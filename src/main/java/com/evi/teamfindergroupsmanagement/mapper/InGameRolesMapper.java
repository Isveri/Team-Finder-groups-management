package com.evi.teamfindergroupsmanagement.mapper;

import com.evi.teamfindergroupsmanagement.domain.InGameRole;
import com.evi.teamfindergroupsmanagement.model.InGameRolesDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class InGameRolesMapper {

    public abstract InGameRolesDTO mapInGameRolesToInGameRolesDTO(InGameRole inGameRole);
    public abstract InGameRole mapInGameRolesDTOToInGameRole(InGameRolesDTO inGameRolesDTO);
}
