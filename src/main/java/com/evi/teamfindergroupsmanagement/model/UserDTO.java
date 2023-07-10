package com.evi.teamfindergroupsmanagement.model;

import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import com.evi.teamfindergroupsmanagement.model.RoleDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String info;
    private int age;
    private int phone;
    private String city;
    private RoleDTO role;
    private List<InGameRolesDTO> inGameRoles;
}
