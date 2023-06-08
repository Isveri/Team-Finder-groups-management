package com.evi.teamfindergroupsmanagement.mapper;


import com.evi.teamfindergroupsmanagement.model.UserMsgDTO;
import com.evi.teamfindergroupsmanagement.security.model.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public abstract UserDTO mapUserToUserDTO(User user);

    public abstract User updateUserFromUserDTO(UserDTO userDTO, @MappingTarget User user);

    public abstract User mapUserDTOToUser(UserDTO userDTO);

    public abstract UserMsgDTO mapUserToUserMsgDTO(User user);

    public abstract User mapUserMsgDTOToUser(UserMsgDTO userMsgDTO);

}
