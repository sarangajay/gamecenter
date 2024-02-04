package com.bs.gamecenter.model;

import lombok.Builder;

import java.util.List;
@Builder
public record GamerDTO(Long gamerId,

                       String name,

                       String gender,

                       String nickname,

                       String geography,
                       List<GameDTO> games) {
}
