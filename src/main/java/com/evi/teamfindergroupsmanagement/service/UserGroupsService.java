package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.model.GroupRoomDTO;
import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;

import java.util.List;

public interface UserGroupsService {
    void joinGroupRoom(Long groupId, InGameRolesDTO inGameRoles);

    void getOutOfGroup(Long groupId);

    UserGroupsListDTO getUserGroups();

    List<GroupRoomDTO> getOutOffAllGroups();

    void rollbackExit(String groupRoomDTOBackupJSON);
}
