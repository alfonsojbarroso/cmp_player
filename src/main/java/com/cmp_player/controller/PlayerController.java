package com.cmp_player.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cmp_player.dto.PlayerDto;
import com.cmp_player.dto.UserProfileDto;
import com.cmp_player.service.PlayerService;
import com.cmp_player.service.UserProfileService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("${player.url}")
public class PlayerController {

    private final PlayerService playerService;
    private final UserProfileService profileService;

    public PlayerController(PlayerService playerService, UserProfileService profileService) {
        this.playerService = playerService;
        this.profileService = profileService;
    }

    @GetMapping
    public Flux<PlayerDto> findAll() {
        return playerService.findAll();
    }

    @GetMapping("/{player-id}")
    public Flux<UserProfileDto> fetchUserProfileByPlayerId(@PathVariable("player-id") Integer playerId) {
        return profileService.fetchUserProfileByPlayerId(playerId);
    }

}
