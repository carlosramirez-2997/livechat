package com.carlosramirez.livechat.controller;

import com.carlosramirez.livechat.model.dto.rest.PaginatedResponse;
import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import com.carlosramirez.livechat.services.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PaginatedResponse<UserDTO> getUsers(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be 0 or greater") int page,
                                               @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be at least 1")
                                               @Max(value = 100, message = "Size must be at most 100") int size) {
        return userService.getUsersRegistered(page, size);
    }
}
