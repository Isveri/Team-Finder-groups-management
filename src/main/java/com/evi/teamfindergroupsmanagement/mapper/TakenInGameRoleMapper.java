package com.evi.teamfindergroupsmanagement.mapper;


import com.evi.teamfindergroupsmanagement.domain.TakenInGameRole;
import com.evi.teamfindergroupsmanagement.model.TakenInGameRoleDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(builder = @Builder(disableBuilder = true),
        uses = {UserMapper.class, InGameRolesMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TakenInGameRoleMapper {

    public abstract TakenInGameRoleDTO mapTakenInGameRoleToTakenInGameRoleDTO(TakenInGameRole takenInGameRole);

    public abstract TakenInGameRole mapTakenInGameRoleDTOToTakenInGameRole(TakenInGameRoleDTO takenInGameRoleDTO);
}
