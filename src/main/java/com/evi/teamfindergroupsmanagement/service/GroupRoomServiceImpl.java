package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.domain.Category;
import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.domain.InGameRole;
import com.evi.teamfindergroupsmanagement.domain.TakenInGameRole;
import com.evi.teamfindergroupsmanagement.exception.*;
import com.evi.teamfindergroupsmanagement.mapper.GroupRoomMapper;
import com.evi.teamfindergroupsmanagement.mapper.TakenInGameRoleMapper;
import com.evi.teamfindergroupsmanagement.messaging.NotificationMessagingService;
import com.evi.teamfindergroupsmanagement.messaging.model.Notification;
import com.evi.teamfindergroupsmanagement.model.GroupRoomDTO;
import com.evi.teamfindergroupsmanagement.model.GroupRoomUpdateDTO;
import com.evi.teamfindergroupsmanagement.model.JoinCodeDTO;
import com.evi.teamfindergroupsmanagement.model.SearchCriteria;
import com.evi.teamfindergroupsmanagement.repository.CategoryRepository;
import com.evi.teamfindergroupsmanagement.repository.GroupRepository;
import com.evi.teamfindergroupsmanagement.repository.TakenInGameRoleRepository;
import com.evi.teamfindergroupsmanagement.repository.UserRepository;
import com.evi.teamfindergroupsmanagement.security.model.User;
import com.evi.teamfindergroupsmanagement.service.feign.ChatServiceFeignClient;
import com.evi.teamfindergroupsmanagement.utils.GroupRoomSpecification;
import com.evi.teamfindergroupsmanagement.utils.RandomStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.evi.teamfindergroupsmanagement.utils.UserDetailsHelper.checkPrivilages;
import static com.evi.teamfindergroupsmanagement.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class GroupRoomServiceImpl implements GroupRoomService {

    private final GroupRepository groupRepository;
    private final GroupRoomMapper groupRoomMapper;
    private final UserRepository userRepository;
    private final TakenInGameRoleRepository takenInGameRoleRepository;
    private final CategoryRepository categoryRepository;
    private final TakenInGameRoleMapper takenInGameRoleMapper;
    private final NotificationMessagingService notificationMessagingService;
    private final ChatServiceFeignClient chatServiceFeignClient;


    @Override
    public List<GroupRoomDTO> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(groupRoomMapper::mapGroupRoomToGroupRoomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<GroupRoomDTO> getGroupsByCriteria(SearchCriteria criteria, Pageable pageable) {
        GroupRoomSpecification spec = new GroupRoomSpecification(criteria);
        return groupRepository.findAll(spec, pageable)
                .map(groupRoomMapper::mapGroupRoomToGroupRoomDTO);
    }

    @Override
    public Page<GroupRoomDTO> getGroupsByGame(String game, Pageable pageable) {

        return groupRepository.findAllByGameNameAndOpenIsTrueAndDeletedFalse(game, pageable)
                .map(groupRoomMapper::mapGroupRoomToGroupRoomDTO);
    }

    @Override
    public GroupRoomDTO createGroupRoom(GroupRoomDTO groupRoomDTO) {
        User user = getUserById(getCurrentUser().getId());
        GroupRoom groupRoom = prepareGroupRoom(groupRoomDTO, user);
        return groupRoomMapper.mapGroupRoomToGroupRoomDTO(groupRepository.save(groupRoom));
    }

    @Override
    public List<GroupRoomDTO> getDeletedGroups() {
        return groupRepository.findAllByDeletedTrue().stream()
                .map(groupRoomMapper::mapGroupRoomToGroupRoomDTO)
                .collect(Collectors.toList());
    }


    @Override
    public GroupRoomDTO getGroupById(Long groupId) {
        return groupRepository.findByIdAndDeletedFalse(groupId)
                .map(groupRoomMapper::mapGroupRoomToGroupRoomDTO)
                .orElseThrow(() -> new GroupNotFoundException("Group room not found, ID:" + groupId));
    }

    @Override
    public GroupRoomDTO updateGroupRoom(Long id, GroupRoomUpdateDTO groupRoomUpdateDTO) {

        GroupRoom groupRoom = getGroupRoomById(id);
        return groupRoomMapper
                .mapGroupRoomToGroupRoomDTO(
                        groupRepository.save(groupRoomMapper.updateGroupRoomFromGroupRoomUpdateDTO(groupRoomUpdateDTO, groupRoom)));
    }

    @Override
    public void deleteGroupRoomById(Long id) {
        GroupRoom groupRoom = getGroupRoomById(id);
        if (checkPrivilages(groupRoom)) {
            for (User user : groupRoom.getUsers()) {
                user.getGroupRooms().remove(groupRoom);
            }
            groupRoom.setDeleted(true);
            groupRepository.save(groupRoom);
        } else {
            throw new NotGroupLeaderException("You are not a leader of this group or admin");

        }
    }

    @Override
    public void updateVisibility(Long groupId, boolean value) {
        GroupRoom groupRoom = getGroupRoomById(groupId);
        if (checkPrivilages(groupRoom)) {
            groupRoom.setOpen(value);
            groupRepository.save(groupRoom);
        } else {
            throw new NotGroupLeaderException("You are not group leader or admin to change visibility");
        }
    }

    @Override
    public JoinCodeDTO generateJoinCode(Long groupId) {
        GroupRoom groupRoom = getGroupRoomById(groupId);
        User currentUser = getCurrentUser();

        if (groupRoom.getJoinCode() == null && groupRoom.getGroupLeader().getId().equals(currentUser.getId())) {
            String generatedCode = getUniqueCode();
            groupRoom.setJoinCode(generatedCode);
            groupRepository.save(groupRoom);
            return JoinCodeDTO.builder().code(generatedCode).build();
        } else {
            throw new NotGroupLeaderException("Cant generate join code for group with id:" + groupId);
        }
    }

    @Override
    public GroupRoomDTO joinGroupByCode(String code) {
        User user = getUserById(getCurrentUser().getId());

        GroupRoom groupRoom = groupRepository.findGroupRoomByJoinCodeAndDeletedFalse(code);
        if (groupRoom == null) {
            throw new CodeDoesntExistException("Code doesnt fit any group");
        }
        if (user.getGroupRooms().contains(groupRoom)) {
            throw new AlreadyInGroupException("You already joined group " + groupRoom.getName());
        } else {
            user.getGroupRooms().add(groupRoom);
            if (groupRoom.isInGameRolesActive()) {
                groupRoom.getTakenInGameRoles().stream().filter((takenInGameRole -> takenInGameRole.getUser() == null)).findFirst().orElseThrow(null).setUser(user);
            }
            userRepository.save(user);

            notificationMessagingService.sendNotification(Notification.builder().notificationType(Notification.NotificationType.INFO).userId(null).groupId(groupRoom.getId()).msg(user.getUsername() + " joined ").build());
            return groupRoomMapper.mapGroupRoomToGroupRoomDTO(groupRoom);
        }
    }

    @Override
    public GroupRoomDTO makePartyLeader(Long groupId, Long userId) {

        GroupRoom groupRoom = getGroupRoomById(groupId);
        User userToBeLeader = getUserById(userId);

        if (checkPrivilages(groupRoom) && groupRoom.getUsers().contains(userToBeLeader)) {
            groupRoom.setGroupLeader(userToBeLeader);
            notificationMessagingService.sendNotification(Notification.builder().notificationType(Notification.NotificationType.INFO).userId(null).groupId(groupRoom.getId()).msg(userToBeLeader.getUsername() + " is now group leader").build());

            return groupRoomMapper.mapGroupRoomToGroupRoomDTO(groupRepository.save(groupRoom));
        }
        throw new NotGroupLeaderException("Not a group leader");
    }

    @Override
    public GroupRoomDTO removeUserFromGroup(Long groupId, Long userId) {
        GroupRoom groupRoom = getGroupRoomById(groupId);
        User userToRemove = getUserById(userId);

        if (checkPrivilages(groupRoom)) {
            groupRoom.getUsers().remove(userToRemove);
            if (groupRoom.isInGameRolesActive()) {
                groupRoom.getTakenInGameRoles().stream().filter((takenInGameRole) -> userToRemove.equals(takenInGameRole.getUser())).findAny().orElse(new TakenInGameRole()).setUser(null);
            }
            userToRemove.getGroupRooms().remove(groupRoom);
            userRepository.save(userToRemove);

            notificationMessagingService.sendNotification(Notification.builder().notificationType(Notification.NotificationType.REMOVED).groupId(groupRoom.getId()).userId(userToRemove.getId()).msg(userToRemove.getUsername() + " has been removed").build());

            return groupRoomMapper.mapGroupRoomToGroupRoomDTO(groupRepository.save(groupRoom));
        }
        throw new NotGroupLeaderException("Not a group leader");
    }

    private String getUniqueCode() {
        String generatedCode = RandomStringUtils.getRandomString();
        if (groupRepository.existsByJoinCodeAndDeletedFalse(generatedCode)) {
            return getUniqueCode();
        }
        return generatedCode;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }

    private GroupRoom getGroupRoomById(Long id) {
        return groupRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GroupNotFoundException("Group room not found id:" + id));
    }

    private GroupRoom prepareGroupRoom(GroupRoomDTO groupRoomDTO, User user) {
        GroupRoom groupRoom = createBaseGroup(groupRoomDTO, user);

        Long chatId = chatServiceFeignClient.createChat(groupRoom.getId()).getBody();
        if(Objects.equals(chatId,null)){
            throw new RuntimeException("There is problem creating group chat, try again later");
        }
        groupRoom.setChatId(chatId);
        Category category = categoryRepository.findByName(groupRoom.getCategory().getName());
        groupRoom.setCategory(category);
        groupRoom.setGame(category.getGame());
        groupRoom.setGroupLeader(user);
        groupRoom.setTakenInGameRoles(createTakenInGameRoles(groupRoomDTO, groupRoom, category));
        return groupRoom;
    }

    private GroupRoom createBaseGroup(GroupRoomDTO groupRoomDTO, User user) {
        GroupRoom groupRoom = groupRoomMapper.mapGroupRoomDTOToGroupRoom(groupRoomDTO);
        user.getGroupRooms().add(groupRoom);
        return groupRepository.save(groupRoom);
    }

    private List<TakenInGameRole> createTakenInGameRoles(GroupRoomDTO groupRoomDTO, GroupRoom groupRoom, Category category) {
        if (groupRoom.isInGameRolesActive()) {
            List<TakenInGameRole> list = new ArrayList<>();
            groupRoom.setMaxUsers(category.getBasicMaxUsers());
            if (!Objects.equals(groupRoom.getCategory().getName(), "SoloQ")) {
                for (InGameRole inGameRole : category.getGame().getInGameRoles()) {
                    TakenInGameRole takenInGameRole = new TakenInGameRole();
                    takenInGameRole.setInGameRole(inGameRole);
                    if (inGameRole.getId().equals(groupRoomDTO.getTakenInGameRoles().get(0).getInGameRole().getId())) {
                        takenInGameRole.setUser(getCurrentUser());
                    }
                    list.add(takenInGameRole);
                    takenInGameRoleRepository.save(takenInGameRole);
                }
            } else {
                for (int i = 0; i < groupRoomDTO.getTakenInGameRoles().size(); i++) {
                    TakenInGameRole takenInGameRole = takenInGameRoleMapper.mapTakenInGameRoleDTOToTakenInGameRole(groupRoomDTO.getTakenInGameRoles().get(i));
                    if (i == 0) {
                        takenInGameRole.setUser(getCurrentUser());
                    }
                    list.add(takenInGameRole);
                    takenInGameRoleRepository.save(takenInGameRole);
                }
            }
            return list;

        } else {
            return null;
        }
    }

}
