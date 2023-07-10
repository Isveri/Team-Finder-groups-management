package com.evi.teamfindergroupsmanagement.mapper;

import com.evi.teamfindergroupsmanagement.model.UserGroupsListDTO;
import com.evi.teamfindergroupsmanagement.security.model.User;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(builder = @Builder(disableBuilder = true),
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserGroupListMapper {

    public abstract UserGroupsListDTO mapUserToUserGroupsListDTO(User user);
}