package com.evi.teamfindergroupsmanagement.mapper;

import com.evi.teamfindergroupsmanagement.domain.Game;
import com.evi.teamfindergroupsmanagement.model.GameDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(builder = @Builder(disableBuilder = true),
        uses = {InGameRolesMapper.class, CategoryMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class GameMapper {

    public abstract GameDTO mapGameToGameDTO(Game game);
}
