package com.evi.teamfindergroupsmanagement.model;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@Setter
@Getter
public class GroupRoomUpdateDTO {
    private String name;
    private String description;
    private int maxUsers;
}
