package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.domain.TakenInGameRole;
import com.evi.teamfindergroupsmanagement.exception.AlreadyInGroupException;
import com.evi.teamfindergroupsmanagement.exception.GroupNotFoundException;
import com.evi.teamfindergroupsmanagement.exception.UserNotFoundException;
import com.evi.teamfindergroupsmanagement.mapper.GroupRoomMapper;
import com.evi.teamfindergroupsmanagement.mapper.UserGroupListMapper;
import com.evi.teamfindergroupsmanagement.messaging.NotificationMessagingService;
import com.evi.teamfindergroupsmanagement.messaging.model.Notification;
import com.evi.teamfindergroupsmanagement.model.GroupRoomDTO;
import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.TakenInGameRoleDTO;
import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;
import com.evi.teamfindergroupsmanagement.repository.GroupRepository;
import com.evi.teamfindergroupsmanagement.repository.UserRepository;
import com.evi.teamfindergroupsmanagement.security.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.evi.teamfindergroupsmanagement.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class UserGroupsServiceImpl implements UserGroupsService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupRoomMapper groupRoomMapper;
    private final UserGroupListMapper userGroupListMapper;
    private final NotificationMessagingService notificationMessagingService;


    @Override
    public void joinGroupRoom(Long groupId, InGameRolesDTO inGameRoles) {

        User user = getUserById(getCurrentUser().getId());

        GroupRoom groupRoom = getGroupById(groupId);

        if (user.getGroupRooms().contains(groupRoom)) {
            throw new AlreadyInGroupException("User id:" + user.getId() + " is already in group");
        } else {
            user.getGroupRooms().add(groupRoom);
            if (groupRoom.isInGameRolesActive()) {
                if (inGameRoles.getId() == null) {
                    groupRoom.getTakenInGameRoles().stream().filter((takenInGameRole -> takenInGameRole.getUser() == null)).findFirst().orElseThrow(null).setUser(user);
                } else {
                    groupRoom.getTakenInGameRoles().stream().filter((takenInGameRole -> takenInGameRole.getInGameRole().getId().equals(inGameRoles.getId()))).findFirst().orElseThrow(null).setUser(user);
                }
            }
            userRepository.save(user);

            notificationMessagingService.sendNotification(Notification.builder().notificationType(Notification.NotificationType.INFO).userId(null).groupId(groupRoom.getId()).msg(user.getUsername() + " joined group").build());

        }
    }

    @Transactional
    @Override
    public void getOutOfGroup(Long groupId) {

        User user = getUserById(getCurrentUser().getId());
        GroupRoom groupRoom = getGroupById(groupId);
        exitGroup(user,groupRoom);
        notificationMessagingService.sendNotification(Notification.builder().notificationType(Notification.NotificationType.REMOVED).userId(user.getId()).groupId(groupRoom.getId()).msg(user.getUsername() + " left group").build());

    }

    public void exitGroup(User user,GroupRoom groupRoom){

        int numberOfMinimumMembers = 1;

        if (groupRoom.isInGameRolesActive()) {
            groupRoom.getTakenInGameRoles().stream().filter((takenInGameRole) -> user.equals(takenInGameRole.getUser())).findAny().orElse(new TakenInGameRole()).setUser(null);
        }
        if (Objects.equals(groupRoom.getGroupLeader(), user) && groupRoom.getUsers().size() != numberOfMinimumMembers) {
            groupRoom.setGroupLeader(groupRoom.getUsers().stream().filter(usr -> !user.equals(usr)).findFirst().orElseThrow(null));
        }
        if (groupRoom.getUsers().size() == numberOfMinimumMembers) {
            groupRoom.setDeleted(true);
        } else {
            groupRoom.getUsers().remove(user);
        }
        groupRepository.save(groupRoom);
    }

    @Override
    public UserGroupsListDTO getUserGroups() {
        return userGroupListMapper.mapUserToUserGroupsListDTO(getUserById(getCurrentUser().getId()));
    }

    @Transactional
    @Override
    public List<GroupRoomDTO> getOutOffAllGroups() {
        User user = getUserById(getUserGroups().getId());
        List<GroupRoom> UserGroupRooms = groupRepository.findAllByUsersContainingAndDeletedFalse(user);
        List<GroupRoomDTO> groupRoomBackup = new ArrayList<>();
        for (GroupRoom groupRoom : UserGroupRooms) {
            groupRoomBackup.add(groupRoomMapper.mapGroupRoomToGroupRoomDTO(groupRoom));
            this.exitGroup(user,groupRoom);
        }
        return groupRoomBackup;

    }

    @Transactional
    @Override
    public void rollbackExit(String groupRoomDTOBackupJSON) {
        List<GroupRoomDTO> groupRoomDTOS;
        ObjectMapper objectMapper = new ObjectMapper();
        User user = getUserById(getCurrentUser().getId());
        try {
            groupRoomDTOS = objectMapper.readValue(groupRoomDTOBackupJSON, new TypeReference<>() {
            });
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        groupRoomDTOS.forEach(groupRoomDTO -> {
            GroupRoom groupRoom = groupRepository.findById(groupRoomDTO.getId()).orElseThrow(()-> new GroupNotFoundException("Group doesnt exist id: "+groupRoomDTO.getId()));
            if(groupRoomDTO.isInGameRolesActive()) {
                Optional<TakenInGameRoleDTO> takenInGameRoleDTO = groupRoomDTO.getTakenInGameRoles().stream().filter(inGameRoleDTO -> user.getId().equals(inGameRoleDTO.getUser().getId())).findFirst();
                groupRoom.getTakenInGameRoles().stream()
                        .filter(takenInGameRole -> takenInGameRoleDTO.get().getId().equals(takenInGameRole.getId()))
                        .findFirst()
                        .ifPresent(takenInGameRole -> takenInGameRole.setUser(user));

            }
            if(groupRoomDTO.getGroupLeader().getId().equals(user.getId())){
                groupRoom.setDeleted(false);
                groupRoom.setGroupLeader(user);
                groupRoom.getUsers().add(user);

            }else {
                groupRoom.getUsers().add(user);
            }
            groupRepository.save(groupRoom);
        });
    }


    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }

    private GroupRoom getGroupById(Long groupId) {
        return groupRepository.findByIdAndDeletedFalse(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group room not found id: " + groupId));
    }
}
