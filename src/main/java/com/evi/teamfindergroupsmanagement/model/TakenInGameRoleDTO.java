package com.evi.teamfindergroupsmanagement.model;


import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class TakenInGameRoleDTO {

    private Long id;
    private UserMsgDTO user;
    @NotNull
    private InGameRolesDTO inGameRole;
}
