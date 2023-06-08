package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.model.GroupRoomDTO;
import com.evi.teamfindergroupsmanagement.model.GroupRoomUpdateDTO;
import com.evi.teamfindergroupsmanagement.model.JoinCodeDTO;
import com.evi.teamfindergroupsmanagement.model.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupRoomService {
    GroupRoomDTO getGroupByName(String name);

    List<GroupRoomDTO> getAllGroups();

    Page<GroupRoomDTO> getGroupsByCriteria(SearchCriteria criteria, Pageable pageable);

    Page<GroupRoomDTO> getGroupsByGame(String game, Pageable pageable);

    GroupRoomDTO createGroupRoom(GroupRoomDTO groupRoomDTO);

    List<GroupRoomDTO> getDeletedGroups();


    GroupRoomDTO getGroupById(Long id);

    GroupRoomDTO updateGroupRoom(Long id, GroupRoomUpdateDTO groupRoomUpdateDTO);

    void deleteGroupRoomById(Long id);

    void updateVisibility(Long groupId, boolean value);

    JoinCodeDTO generateJoinCode(Long groupId);

    GroupRoomDTO joinGroupByCode(String code);

    GroupRoomDTO makePartyLeader(Long groupId, Long userId);

    GroupRoomDTO removeUserFromGroup(Long groupId, Long userId);
}
