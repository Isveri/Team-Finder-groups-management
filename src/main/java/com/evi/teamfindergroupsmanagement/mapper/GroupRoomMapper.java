package com.evi.teamfindergroupsmanagement.mapper;

import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.model.GroupRoomDTO;
import com.evi.teamfindergroupsmanagement.model.GroupRoomUpdateDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true),
        uses = {UserMapper.class,TakenInGameRoleMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class GroupRoomMapper {

    public abstract GroupRoomDTO mapGroupRoomToGroupRoomDTO(GroupRoom groupRoom);

    public abstract GroupRoom mapGroupRoomDTOToGroupRoom(GroupRoomDTO groupRoomDTO);

    public abstract GroupRoom updateGroupRoomFromGroupRoomUpdateDTO(GroupRoomUpdateDTO groupRoomUpdateDTO, @MappingTarget GroupRoom groupRoom);


}
