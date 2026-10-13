package com.cmp_player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerClassDto {
    Integer id;
    String name;
    String jersey;
    String country;
    String position;
}
