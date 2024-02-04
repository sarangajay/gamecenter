package com.bs.gamecenter.model;

import com.bs.gamecenter.utility.enums.GameLevel;
import lombok.Builder;

@Builder
public record GamerCreditDTO(Long gamerId,
                             String gamerName,
                             Long gameId,
                             String gameName,
                             GameLevel level,
                             int credits) {
}
