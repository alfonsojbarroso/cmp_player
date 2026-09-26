package com.cmp_player.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cmp_player.dto.PlayerDto;

import reactor.core.publisher.Flux;

@Service
public class PlayerService {

    @Value("${base.url.player}")
    private String baseUrlPlayer;
    private final WebClient webClient;

    public PlayerService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<PlayerDto> findAll() {
        return webClient.get()
                .uri(baseUrlPlayer)
                .retrieve()
                .bodyToFlux(PlayerDto.class);
    }

}
