package com.evi.teamfindergroupsmanagement.controller;

import com.evi.teamfindergroupsmanagement.model.CategoryDTO;
import com.evi.teamfindergroupsmanagement.model.GameDTO;
import com.evi.teamfindergroupsmanagement.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<GameDTO>> getGames() {
        return ResponseEntity.ok(searchService.getGames());
    }

    @GetMapping("/categories/{game}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByGame(@PathVariable String game){
        return ResponseEntity.ok(searchService.getCategoriesByGame(game));
    }
}
