package com.evi.teamfindergroupsmanagement.domain;

import com.evi.teamfindergroupsmanagement.security.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class InGameRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToMany(mappedBy = "inGameRoles", cascade = CascadeType.MERGE)
    private List<User> users;

}
