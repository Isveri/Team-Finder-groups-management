package com.evi.teamfindergroupsmanagement.domain;

import com.evi.teamfindergroupsmanagement.security.model.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TakenInGameRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Builder.Default
    private User user = null;

    @OneToOne
    private InGameRole inGameRole;

}
