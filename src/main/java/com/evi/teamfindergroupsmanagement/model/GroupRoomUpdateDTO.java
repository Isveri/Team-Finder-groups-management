package com.evi.teamfindergroupsmanagement.model;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@Setter
@Getter
public class GroupRoomUpdateDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]{3,30}$")
    private String name;

    @NotBlank
    @Size(min = 3, max = 150)
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]{3,150}$")
    private String description;

    @Min(2)
    @Max(10)
    @NotNull
    private int maxUsers;
}
