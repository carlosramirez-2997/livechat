package com.carlosramirez.livechat.services.user;

import com.carlosramirez.livechat.model.dto.rest.PaginatedResponse;
import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    PaginatedResponse<UserDTO> getUsersRegistered(int page, int size);
}
