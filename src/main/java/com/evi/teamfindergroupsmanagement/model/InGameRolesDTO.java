package com.evi.teamfindergroupsmanagement.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode
@Getter
@Builder
public class InGameRolesDTO {
    @NotBlank
    private String name;
    private Long id;
}
