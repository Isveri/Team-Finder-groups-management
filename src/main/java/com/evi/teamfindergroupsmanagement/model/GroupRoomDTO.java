package com.evi.teamfindergroupsmanagement.model;

import com.evi.teamfindergroupsmanagement.mapper.UserDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class GroupRoomDTO {
    private Long id;
    private String name;
    private String description;
    private int maxUsers;
    private boolean open;
    private boolean inGameRolesActive;
    private List<UserDTO> users;
    private CategoryDTO category;
    private Long chatId;
    private List<TakenInGameRoleDTO> takenInGameRoles;
    private UserDTO groupLeader;
    private String city;
    private GameDTO game;
    private String joinCode;
}
