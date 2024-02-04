package com.bs.gamecenter.controller;

import com.bs.gamecenter.model.GamerCreditDTO;
import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.GamerInput;
import com.bs.gamecenter.service.GamerService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GameCenterController {

    private final GamerService gamerService;

    @MutationMapping
    GamerDTO enrollGamer(@Argument("input") GamerInput gamerInput) {
        log.info("enrollGamer is called");
        return gamerService.enrollGamer(gamerInput);
    }

    @MutationMapping
    GamerDTO grantCreditToGamer(@Argument @Positive Long gamerId, @Argument @Positive Long gameId,
                                @Argument @Positive Integer creditAmount) {
        log.info("grantCreditToGamer is called with gamerId: {}, gameId: {}, credit: {}",
                gamerId, gameId, creditAmount);
        return gamerService.grantCreditToGamer(gamerId, gameId, creditAmount);
    }

    @QueryMapping
    List<GamerCreditDTO> findGamersWithMaxCreditsForEachGameBaseOnLevel() {
        log.info("findGamersWithMaxCreditsForEachGameBaseOnLevel is called");
        return gamerService.findGamersWithMaxCreditsForEachGame();
    }
}
