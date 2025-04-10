package com.carlosramirez.livechat.services.user.impl;

import com.carlosramirez.livechat.data.UserRepository;
import com.carlosramirez.livechat.mapper.UserMapper;
import com.carlosramirez.livechat.model.dto.rest.PaginatedResponse;
import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import com.carlosramirez.livechat.model.entity.CustomUserDetails;
import com.carlosramirez.livechat.model.entity.RegisteredUser;
import com.carlosramirez.livechat.services.user.UserService;
import com.carlosramirez.livechat.utils.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         RegisteredUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

         return new CustomUserDetails(user);
    }


    @Override
    public PaginatedResponse<UserDTO> getUsersRegistered(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserDTO> data = userRepository.findAll(pageable)
                .map(userMapper::userToDTO);

        return PaginationUtils.mapPage(data, data.getContent());
    }
}