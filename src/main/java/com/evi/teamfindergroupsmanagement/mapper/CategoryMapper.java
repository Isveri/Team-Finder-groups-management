package com.evi.teamfindergroupsmanagement.mapper;

import com.evi.teamfindergroupsmanagement.domain.Category;
import com.evi.teamfindergroupsmanagement.model.CategoryDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true),
        uses = GameMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CategoryMapper {

    public abstract CategoryDTO mapCategoryToCategoryDTO(Category category);
}
