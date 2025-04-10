package com.carlosramirez.livechat.mapper;

import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import com.carlosramirez.livechat.model.entity.BaseUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToDTO(BaseUser user);
}
