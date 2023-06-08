package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;

public interface UserGroupsService {
    void joinGroupRoom(Long groupId, InGameRolesDTO inGameRoles);

    void getOutOfGroup(Long groupId);

    UserGroupsListDTO getUserGroups();
}
