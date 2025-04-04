package com.carlosramirez.livechat.controller;

import com.carlosramirez.livechat.config.FeatureConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class ChatRestController {

    private final FeatureConfig featureConfig;

    public ChatRestController(FeatureConfig featureConfig) {
        this.featureConfig = featureConfig;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<String> getAvailableRooms() {
        return featureConfig.getRooms();
    }
}
