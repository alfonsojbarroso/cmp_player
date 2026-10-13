package com.cmp_player.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerUserProfileDto {

    private PlayerDto playerDto;
    private List<UserProfileDto> userProfileDtos;
}
