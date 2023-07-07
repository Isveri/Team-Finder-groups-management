package com.evi.teamfindergroupsmanagement.controller;

import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;
import com.evi.teamfindergroupsmanagement.repository.GroupRepository;
import com.evi.teamfindergroupsmanagement.repository.UserRepository;
import com.evi.teamfindergroupsmanagement.service.UserGroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/userGroups/")
public class UserGroupsController {

    private final UserGroupsService userGroupsService;


    @GetMapping("/all")
    public ResponseEntity<UserGroupsListDTO> getUserGroups() {

        return ResponseEntity.ok(userGroupsService.getUserGroups());
    }

    @PatchMapping("/join/{groupId}")
    public ResponseEntity<Void> joinGroupRoom(@PathVariable Long groupId, @RequestBody InGameRolesDTO inGameRoles) {
        userGroupsService.joinGroupRoom(groupId,inGameRoles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/exit/{groupId}")
    public ResponseEntity<Void> exitGroupRoom(@PathVariable Long groupId) {
        userGroupsService.getOutOfGroup(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/exitAllGroups/{userId}")
    public ResponseEntity<Void> exitAllGroups(@PathVariable Long userId) {
        userGroupsService.getOutOffAllGroups(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
