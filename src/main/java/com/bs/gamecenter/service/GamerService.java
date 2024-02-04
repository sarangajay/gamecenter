package com.bs.gamecenter.service;

import com.bs.gamecenter.model.GamerCreditDTO;
import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.GamerInput;

import java.util.List;


public interface GamerService {
    GamerDTO enrollGamer(GamerInput gamerInput);

    GamerDTO grantCreditToGamer(Long gamerId, Long gameId, Integer credit);
    List<GamerCreditDTO> findGamersWithMaxCreditsForEachGame();
}
