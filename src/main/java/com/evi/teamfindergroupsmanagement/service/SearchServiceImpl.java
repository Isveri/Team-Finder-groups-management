package com.evi.teamfindergroupsmanagement.service;

import com.evi.teamfindergroupsmanagement.mapper.CategoryMapper;
import com.evi.teamfindergroupsmanagement.mapper.GameMapper;
import com.evi.teamfindergroupsmanagement.model.CategoryDTO;
import com.evi.teamfindergroupsmanagement.model.GameDTO;
import com.evi.teamfindergroupsmanagement.repository.CategoryRepository;
import com.evi.teamfindergroupsmanagement.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public List<GameDTO> getGames() {
        return gameRepository.findAll()
                .stream()
                .map(gameMapper::mapGameToGameDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getCategoriesByGame(String game) {
        return categoryRepository.findAllByGameName(game)
                .stream()
                .map(categoryMapper::mapCategoryToCategoryDTO)
                .collect(Collectors.toList());
    }


}
