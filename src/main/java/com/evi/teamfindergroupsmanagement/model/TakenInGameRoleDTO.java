package com.evi.teamfindergroupsmanagement.model;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class TakenInGameRoleDTO {

    private Long id;
    private UserMsgDTO user;
    private InGameRolesDTO inGameRole;
}
