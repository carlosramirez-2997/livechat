package com.carlosramirez.livechat.data;

import com.carlosramirez.livechat.model.entity.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<RegisteredUser, Long> {
    Optional<RegisteredUser> findByEmail(String email);
}
