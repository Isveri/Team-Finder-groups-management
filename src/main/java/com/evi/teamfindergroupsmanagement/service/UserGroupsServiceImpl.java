package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.domain.TakenInGameRole;
import com.evi.teamfindergroupsmanagement.exception.AlreadyInGroupException;
import com.evi.teamfindergroupsmanagement.exception.GroupNotFoundException;
import com.evi.teamfindergroupsmanagement.exception.UserNotFoundException;
import com.evi.teamfindergroupsmanagement.mapper.UserGroupListMapper;
import com.evi.teamfindergroupsmanagement.messaging.JmsMessagingService;
import com.evi.teamfindergroupsmanagement.messaging.NotificationMessagingService;
import com.evi.teamfindergroupsmanagement.messaging.model.Notification;
import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;
import com.evi.teamfindergroupsmanagement.repository.GroupRepository;
import com.evi.teamfindergroupsmanagement.repository.UserRepository;
import com.evi.teamfindergroupsmanagement.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.evi.teamfindergroupsmanagement.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class UserGroupsServiceImpl implements UserGroupsService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
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

    @Override
    public void getOutOfGroup(Long groupId) {

        int numberOfMinimumMembers = 1;

        User user = getUserById(getCurrentUser().getId());
        GroupRoom groupRoom = getGroupById(groupId);

        user.getGroupRooms().remove(groupRoom);

        if (groupRoom.isInGameRolesActive()) {
            groupRoom.getTakenInGameRoles().stream().filter((takenInGameRole) -> user.equals(takenInGameRole.getUser())).findAny().orElse(new TakenInGameRole()).setUser(null);
        }
        if (Objects.equals(groupRoom.getGroupLeader(), user) && groupRoom.getUsers().size() != numberOfMinimumMembers) {
            groupRoom.setGroupLeader(groupRoom.getUsers().stream().filter(usr -> !user.equals(usr)).findFirst().orElseThrow(null));
        }
        if (groupRoom.getUsers().size() == numberOfMinimumMembers) {
            groupRepository.softDeleteById(groupRoom.getId());
        } else {
            groupRepository.save(groupRoom);
        }
        userRepository.save(user);

        notificationMessagingService.sendNotification(Notification.builder().notificationType(Notification.NotificationType.REMOVED).userId(user.getId()).groupId(groupRoom.getId()).msg(user.getUsername() + " left group").build());

    }

    @Override
    public UserGroupsListDTO getUserGroups() {
        return userGroupListMapper.mapUserToUserGroupsListDTO(getUserById(getCurrentUser().getId()));
    }

    @Override
    public void getOutOffAllGroups() {
        List<GroupRoom> UserGroupRooms = groupRepository.findAllByUsersContaining(getCurrentUser());
        for (GroupRoom groupRoom : UserGroupRooms) {
            this.getOutOfGroup(groupRoom.getId());
        }

    }


    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }

    private GroupRoom getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group room not found id: " + groupId));
    }
}
