package com.bs.gamecenter.utility;

import com.bs.gamecenter.model.entity.Game;
import com.bs.gamecenter.repository.GameRepository;
import com.bs.gamecenter.utility.enums.GameName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final GameRepository gameRepository;

    @Override
    public void run(String... args) {
        insertGames();
    }

    private void insertGames() {
        Game game1 = Game.builder().name(GameName.FORTNITE.getName()).build();
        Game game2 = Game.builder().name(GameName.CALL_OF_DUTY.getName()).build();
        Game game3 = Game.builder().name(GameName.DOTA.getName()).build();
        Game game4 = Game.builder().name(GameName.VALHALLA.getName()).build();
        Game game5 = Game.builder().name(GameName.AMONGUS.getName()).build();

        gameRepository.saveAll(List.of(game1, game2, game3, game4, game5));
        log.info("Loaded  the Game names.....");
    }
}
