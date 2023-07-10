package com.evi.teamfindergroupsmanagement.repository;

import com.evi.teamfindergroupsmanagement.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

}
