package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.model.CategoryDTO;
import com.evi.teamfindergroupsmanagement.model.GameDTO;

import java.util.List;

public interface SearchService {


    List<GameDTO> getGames();

    List<CategoryDTO> getCategoriesByGame(String game);
}
