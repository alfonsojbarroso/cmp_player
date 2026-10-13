package com.cmp_player.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmp_player.dto.PlayerUserProfileDto;
import com.cmp_player.dto.UserProfileDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerUserProfileService {

    private final PlayerService playerService;
    private final UserProfileService userProfileService;

    public PlayerUserProfileService(PlayerService playerService, UserProfileService userProfileService) {
        this.playerService = playerService;
        this.userProfileService = userProfileService;
    }

    public Flux<PlayerUserProfileDto> fetch() {
        return playerService.findAll()
                .flatMap(player -> {
                    Mono<List<UserProfileDto>> mono = userProfileService.fetchUserProfileByPlayerId(player.id())
                            .collectList();
                    return mono.map(playerUserProfile -> new PlayerUserProfileDto(player, playerUserProfile));
                });
    }

}
