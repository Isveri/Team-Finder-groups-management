package com.evi.teamfindergroupsmanagement.domain;

import com.evi.teamfindergroupsmanagement.security.model.User;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Where(clause = "deleted=false")
public class GroupRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @JoinColumn(name = "category_id")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Category category;

    @JoinColumn(name = "game_id")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Game game;

    @NotNull
    private int maxUsers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TakenInGameRole> takenInGameRoles = new ArrayList<>();

    @Builder.Default
    private boolean inGameRolesActive = false;

    @Builder.Default
    private boolean open = true;

    @ManyToMany(mappedBy = "groupRooms", cascade = CascadeType.MERGE)
    private List<User> users = new ArrayList<>();

    private Long chatId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "groupLeader_id")
    private User groupLeader;

    private String joinCode;

    private String city;

    private boolean deleted = false;
}
