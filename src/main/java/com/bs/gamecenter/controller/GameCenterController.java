package com.bs.gamecenter.controller;

import com.bs.gamecenter.model.GamerCreditDTO;
import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.GamerDataDTO;
import com.bs.gamecenter.model.GamerInput;
import com.bs.gamecenter.service.GamerService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

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

    @QueryMapping
    List<GamerDataDTO> gameDataSearch(
            @Argument @Nullable String gamerName,
            @Argument @Nullable String gameName,
            @Argument @Nullable String level,
            @Argument @Nullable String geography
    ) {
        log.info("gameDataSearch is called");
        logIfNotNull("gamerName: {}", gamerName);
        logIfNotNull("gameName: {}", gameName);
        logIfNotNull("{}", level);
        logIfNotNull("geography: {}", geography);

        Optional<String> optionalGamerName = Optional.ofNullable(gamerName);
        Optional<String> optionalGameName = Optional.ofNullable(gameName);
        Optional<String> optionalLevel = Optional.ofNullable(level);
        Optional<String> optionalGeography = Optional.ofNullable(geography);

        return gamerService.gameDataSearch(
                optionalGamerName.orElse(null),
                optionalGameName.orElse(null),
                optionalLevel.orElse(null),
                optionalGeography.orElse(null)
        );
    }

    private void logIfNotNull(String message, @Nullable String value) {
        if (value != null) {
            log.info(message, value);
        }
    }
}
