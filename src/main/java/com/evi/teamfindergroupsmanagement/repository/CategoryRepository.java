package com.evi.teamfindergroupsmanagement.repository;

import com.evi.teamfindergroupsmanagement.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    List<Category> findAllByGameName(String name);

}
