package com.evi.teamfindergroupsmanagement.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true)
@Setter
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private int basicMaxUsers;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="game_id")
    private Game game;

    @Builder.Default
    private boolean canAssignRoles = true;

}
