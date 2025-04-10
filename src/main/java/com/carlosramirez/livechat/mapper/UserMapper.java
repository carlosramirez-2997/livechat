package com.carlosramirez.livechat.mapper;

import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import com.carlosramirez.livechat.model.entity.BaseUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    UserDTO userToDTO(BaseUser user);
}
