package com.bs.gamecenter.model;

import com.bs.gamecenter.utility.enums.GameLevel;

public record GamerDataDTO(String gamerName,
                           String gender,
                           String nickname,
                           String geography,
                           String gameName,
                           GameLevel level) {
}
