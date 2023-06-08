package com.evi.teamfindergroupsmanagement.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private int basicMaxUsers;
    private boolean canAssignRoles;
}
