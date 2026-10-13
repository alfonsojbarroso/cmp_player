package com.cmp_player.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileDto {

    @JsonProperty("user_headquerter")
    private String userHeadQuarter;
    @JsonProperty("user_rol")
    private String userRol;
    @JsonProperty("player_id")
    private Integer playerId;
    @JsonIgnore
    private String id;
}
