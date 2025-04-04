package com.carlosramirez.livechat.services.user.impl;

import com.carlosramirez.livechat.data.UserRepository;
import com.carlosramirez.livechat.model.entity.CustomUserDetails;
import com.carlosramirez.livechat.model.entity.RegisteredUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         RegisteredUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

         return new CustomUserDetails(user);
    }
}
