package com.bs.gamecenter.controller;

import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.GamerInput;
import com.bs.gamecenter.service.GamerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

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
}
