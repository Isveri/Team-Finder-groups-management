package com.evi.teamfindergroupsmanagement.controller;

import com.evi.teamfindergroupsmanagement.model.GroupRoomDTO;
import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;
import com.evi.teamfindergroupsmanagement.service.UserGroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user-groups")
public class UserGroupsController {

    private final UserGroupsService userGroupsService;


    @GetMapping
    public ResponseEntity<UserGroupsListDTO> getUserGroups() {

        return ResponseEntity.ok(userGroupsService.getUserGroups());
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<Void> joinGroupRoom(@PathVariable Long groupId, @RequestBody InGameRolesDTO inGameRoles) {
        userGroupsService.joinGroupRoom(groupId, inGameRoles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> exitGroupRoom(@PathVariable Long groupId) {
        userGroupsService.getOutOfGroup(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public List<GroupRoomDTO> exitAllGroups() {
        return userGroupsService.getOutOffAllGroups();
    }

    @PutMapping
    public ResponseEntity<Void> rollbackExit(@RequestBody String groupBackupJSON){
        userGroupsService.rollbackExit(groupBackupJSON);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
