package com.evi.teamfindergroupsmanagement.model;

import com.evi.teamfindergroupsmanagement.mapper.UserDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class GroupRoomDTO {
    private Long id;

    @Size(min=3)
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]{3,30}$")
    @NotBlank
    private String name;

    @NotBlank
    @Size(min=3,max=150)
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]{3,150}$")
    private String description;

    @Min(2)
    @Max(10)
    @NotNull
    private int maxUsers;

    private boolean open;

    @NotNull
    private boolean inGameRolesActive;
    private List<UserDTO> users;
    private CategoryDTO category;
    private Long chatId;

    @NotNull
    private List<@Valid TakenInGameRoleDTO> takenInGameRoles;
    private UserDTO groupLeader;

    @NotBlank
    private String city;
    private GameDTO game;
    private String joinCode;
}
