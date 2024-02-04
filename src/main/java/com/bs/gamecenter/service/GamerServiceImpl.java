package com.bs.gamecenter.service;

import com.bs.gamecenter.exception.DataNotFoundException;
import com.bs.gamecenter.exception.InvalidDataException;
import com.bs.gamecenter.model.GameInput;
import com.bs.gamecenter.model.GamerCreditDTO;
import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.GamerInput;
import com.bs.gamecenter.model.entity.Game;
import com.bs.gamecenter.model.entity.Gamer;
import com.bs.gamecenter.model.entity.GamerGame;
import com.bs.gamecenter.model.entity.GamerGameId;
import com.bs.gamecenter.model.mapper.EntityMapper;
import com.bs.gamecenter.model.mapper.GameDataMapper;
import com.bs.gamecenter.repository.GameRepository;
import com.bs.gamecenter.repository.GamerGameRepository;
import com.bs.gamecenter.repository.GamerRepository;
import com.bs.gamecenter.utility.enums.GameLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamerServiceImpl implements GamerService {

    private final GamerRepository gamerRepository;

    private final GameRepository gameRepository;

    private final GamerGameRepository gamerGameRepository;

    private final EntityMapper entityMapper;

    private final GameDataMapper gameDataMapper;

    @Override
    public GamerDTO enrollGamer(GamerInput gamerInput) {
        Gamer gamer = gamerRepository.save(entityMapper.toGamerEntity(gamerInput));
        validateGameInput(gamerInput.games());
        Map<String, GameLevel> nameLevelMap = gamerInput.games().stream()
                .collect(Collectors.toMap(
                        GameInput::name,
                        gameInput -> {
                            GameLevel gameLevel = GameLevel.isValidGameLevel(gameInput.level());
                            if (gameLevel == null) {
                                throw new InvalidDataException("Invalid game level");
                            }
                            return gameLevel;
                        }
                ));


        List<Game> anyFiveGames = findAnyFiveGames(gamerInput.games());
        anyFiveGames.forEach( game -> {
                    final GamerGame gamerGame = entityMapper.toGamerGameEntity(gamer, game, nameLevelMap.get(game.getName()), 0);
                    final GamerGameId gamerGameId = new GamerGameId(gamer.getGamerId(), game.getGameId());
                    gamerGame.setId(gamerGameId);
                    gamerGameRepository.save(gamerGame);
                }
        );
        List<GamerGame> gamerGameList = gamerGameRepository.findByIdGamerId(gamer.getGamerId());
        return gameDataMapper.toGamerDTO(gamer, gamerGameList);
    }

    @Override
    public GamerDTO grantCreditToGamer(Long gamerId, Long gameId, Integer credit) {

        Gamer gamer = gamerRepository.findById(gamerId)
                .orElseThrow(() -> new DataNotFoundException("Gamer not found for Id" + gamerId));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new DataNotFoundException("Game is not found for Id" + gamerId));

        final GamerGameId gamerGameIdKey = new GamerGameId(gamer.getGamerId(), game.getGameId());

        final GamerGame gamerGame = gamerGameRepository.findById(gamerGameIdKey)
                .orElseThrow(() -> new DataNotFoundException(String.format("Game %s is not played by the Gamer: %s.",
                        game.getName(), gamer.getName())));
        gamerGame.setCredits(credit);
        gamerGameRepository.save(gamerGame);

        List<GamerGame> gamerGameList = gamerGameRepository.findByIdGamerId(gamer.getGamerId());
        return gameDataMapper.toGamerDTO(gamer, gamerGameList);
    }

    @Override
    public List<GamerCreditDTO> findGamersWithMaxCreditsForEachGame() {
        return gamerGameRepository.findGamersWithMaxCreditsForEachGame();
    }
    private List<Game> findAnyFiveGames(List<GameInput> gameInput) {
        List<String> namesList = gameInput.stream()
                .map(GameInput::name)
                .filter(Objects::nonNull)
                .toList();

        validateNamesListSize(namesList);

        List<Game> allGames = gameRepository.findByNameIn(namesList);
        List<String> invalidGames = validateGameNames(namesList, allGames.stream()
                .map(Game::getName)
                .toList());

        validateInvalidGameNames(invalidGames);


        Collections.shuffle(allGames);
        return allGames.subList(0, Math.min(5, allGames.size()));
    }

    private void validateGameInput(List<GameInput> gameInput) {
        if (gameInput == null || gameInput.isEmpty()) {
            String error = "Game data can not be empty";
            log.warn(error);
            throw new InvalidDataException(error);
        }
    }

    private void validateNamesListSize(List<String> namesList) {
        if (namesList.size() != 5) {
            String error = "Only 5 Games allowed per gamer";
            log.warn(error);
            throw new InvalidDataException(error);
        }
    }

    private void validateInvalidGameNames(List<String> invalidGames) {
        if (!invalidGames.isEmpty()) {
            String error = "Invalid Game names in the request.  " + String.join(", ", invalidGames);
            log.warn(error);
            throw new InvalidDataException(error);
        }
    }

    public static List<String> validateGameNames(List<String> listA, List<String> listB) {
        Set<String> setB = new HashSet<>(listB);
        return listA.stream()
                .filter(element -> !setB.contains(element))
                .toList();
    }

}
