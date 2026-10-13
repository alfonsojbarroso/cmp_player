package com.cmp_player.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.cmp_player.dto.UserProfileDto;
import reactor.core.publisher.Flux;

@Service
public class UserProfileService {

    @Value("${base.url.user.service}")
    private String baseUserProfileUrl;

    private final WebClient webClient;

    public UserProfileService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<UserProfileDto> fetchUserProfileByPlayerId(Integer playerId) {
        return webClient.get()
                .uri(baseUserProfileUrl + "/" + playerId)
                .retrieve()
                .bodyToFlux(UserProfileDto.class).onErrorResume(error -> Flux.just());
    }

}
