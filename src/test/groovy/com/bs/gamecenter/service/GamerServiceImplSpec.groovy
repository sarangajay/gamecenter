package com.bs.gamecenter.service

import com.bs.gamecenter.exception.DataNotFoundException
import com.bs.gamecenter.exception.InvalidDataException
import com.bs.gamecenter.model.GameDTO
import com.bs.gamecenter.model.GameInput
import com.bs.gamecenter.model.GamerCreditDTO
import com.bs.gamecenter.model.GamerDTO
import com.bs.gamecenter.model.GamerDataDTO
import com.bs.gamecenter.model.GamerInput
import com.bs.gamecenter.model.entity.Game
import com.bs.gamecenter.model.entity.Gamer
import com.bs.gamecenter.model.entity.GamerGame
import com.bs.gamecenter.model.entity.GamerGameId
import com.bs.gamecenter.model.mapper.EntityMapper
import com.bs.gamecenter.model.mapper.GameDataMapper
import com.bs.gamecenter.repository.GameRepository
import com.bs.gamecenter.repository.GamerGameRepository
import com.bs.gamecenter.repository.GamerRepository
import com.bs.gamecenter.utility.enums.GameLevel
import com.bs.gamecenter.utility.enums.GameName
import spock.lang.Specification

class GamerServiceImplSpec extends Specification {

    private GamerServiceImpl gamerService
    private GamerRepository gamerRepository
    private GameRepository gameRepository
    private GamerGameRepository gamerGameRepository
    private EntityMapper entityMapper
    private GameDataMapper gameDataMapper

    def setup() {
        gamerRepository = Mock()
        gameRepository = Mock()
        gameRepository = Mock()
        gamerGameRepository = Mock()
        entityMapper = Mock()
        gameDataMapper = Mock()
        gamerService = new GamerServiceImpl(
                gamerRepository,
                gameRepository,
                gamerGameRepository,
                entityMapper,
                gameDataMapper
        )
    }


    def "enrollGamer should create a new GamerDTO"() {
        given:
        def gamerInput = new GamerInput("John Doe", "Male", "JD", "Earth",
                buildGameInputList(
                        new GameInput(GameName.FORTNITE.name(), GameLevel.NOOB.name()),
                        new GameInput(GameName.CALL_OF_DUTY.name(), GameLevel.PRO.name()),
                        new GameInput(GameName.DOTA.name(), GameLevel.INVINCIBLE.name()),
                        new GameInput(GameName.VALHALLA.name(), GameLevel.INVINCIBLE.name()),
                        new GameInput(GameName.AMONGUS.name(), GameLevel.NOOB.name())
                )
        )
        def gamer = buildGamer()

        when:
        1 * gamerRepository.save(_) >> gamer

        1 * gameRepository.findByNameIn(_) >> buildGameList()

        5 * gamerGameRepository.save(_)

        1 * gamerGameRepository.findByIdGamerId(_) >> buildGamerGameList(gamer, buildGame1(), buildGame2(),
                buildGame3(), buildGame4(), buildGame5())

        5 * entityMapper.toGamerGameEntity(_, _, _, _) >> new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 1L),
                gamer: gamer,
                game: new Game(gameId: 1L, name: GameName.FORTNITE.name()),
                level: GameLevel.NOOB,
                credits: 0)

        1 * gameDataMapper.toGamerDTO(gamer, buildGamerGameList(gamer, buildGame1(), buildGame2(),
                buildGame3(), buildGame4(), buildGame5())) >> guildGamerDTO()
        def gamerDTO = gamerService.enrollGamer(gamerInput)

        then:
        noExceptionThrown()
        assert gamerDTO != null
        assert gamerDTO.gamerId() == 1L
        assert gamerDTO.name() == "Saranga"
        assert gamerDTO.gender() == "Male"
        assert gamerDTO.nickname() == "JD"
        assert gamerDTO.geography() == "Kokkadal"
    }

    def "enrollGamer failed with exception when a game level is not valid"() {
        given:
        def gamerInput = new GamerInput("John Doe", "Male", "JD", "Earth",
                buildGameInputList(
                        new GameInput(GameName.FORTNITE.name(), "NO"),
                        new GameInput(GameName.CALL_OF_DUTY.name(), GameLevel.PRO.name()),
                        new GameInput(GameName.DOTA.name(), GameLevel.INVINCIBLE.name()),
                        new GameInput(GameName.VALHALLA.name(), GameLevel.INVINCIBLE.name()),
                        new GameInput(GameName.AMONGUS.name(), GameLevel.NOOB.name())
                ))
        def gamer = buildGamer()

        when:
        1 * gamerRepository.save(_) >> gamer

        gamerService.enrollGamer(gamerInput)

        then:
        thrown(InvalidDataException)
    }

    def "enrollGamer failed with exception when a game name is not valid"() {
        given:
        def gamerInput = new GamerInput("John Doe", "Male", "JD", "Earth",
                buildGameInputList(
                        new GameInput(GameName.FORTNITE.name(), GameLevel.NOOB.name()),
                        new GameInput(GameName.CALL_OF_DUTY.name(), GameLevel.PRO.name()),
                        new GameInput(GameName.DOTA.name(), GameLevel.INVINCIBLE.name()),
                        new GameInput(GameName.VALHALLA.name(), GameLevel.INVINCIBLE.name()),
                        new GameInput("INVALID", GameLevel.NOOB.name())
                ))
        def gamer = buildGamer()

        when:
        1 * gamerRepository.save(_) >> gamer

        1 * gameRepository.findByNameIn(_) >> buildGameList()

        gamerService.enrollGamer(gamerInput)

        then:
        thrown(InvalidDataException)
    }

    def "enrollGamer failed with exception when a no games are added to the request"() {
        given:
        def gamerInput = new GamerInput("John Doe", "Male", "JD", "Earth", buildGameInputList(
                new GameInput(GameName.AMONGUS.name(), GameLevel.NOOB.name())))

        def gamer = buildGamer()

        when:
        1 * gamerRepository.save(_) >> gamer

        gamerService.enrollGamer(gamerInput)

        then:
        thrown(InvalidDataException)
    }

    def "enrollGamer failed with exception when a games count added is not five to the request"() {
        given:
        def gamerInput = new GamerInput("John Doe", "Male", "JD", "Earth", null)

        def gamer = buildGamer()

        when:
        1 * gamerRepository.save(_) >> gamer

        gamerService.enrollGamer(gamerInput)

        then:
        thrown(InvalidDataException)
    }

    def "grantCreditToGamer should create a new GamerDTO with credit"() {
        given:
        def gamer = buildGamer()
        def game = buildGame1()

        when:
        1 * gamerRepository.findById(_) >> Optional.of(gamer)

        1 * gameRepository.findById(_) >> Optional.of(game)

        1 * gamerGameRepository.findById(_) >> Optional.of(new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 1L), gamer: gamer, game: game, level: GameLevel.NOOB, credits: 0))

        1 * gamerGameRepository.save(_)

        1 * gamerGameRepository.findByIdGamerId(_) >> buildGamerGameList(gamer, buildGame1(), buildGame2(),
                buildGame3(), buildGame4(), buildGame5())

        1 * gameDataMapper.toGamerDTO(gamer, buildGamerGameList(gamer, buildGame1(), buildGame2(),
                buildGame3(), buildGame4(), buildGame5())) >> guildGamerDTO()

        def gamerDTO = gamerService.grantCreditToGamer(1L, 1L, 100)

        then:
        noExceptionThrown()
        assert gamerDTO != null
        assert gamerDTO.gamerId() == 1L
        assert gamerDTO.name() == "Saranga"
        assert gamerDTO.gender() == "Male"
        assert gamerDTO.nickname() == "JD"
        assert gamerDTO.geography() == "Kokkadal"
    }

    def "grantCreditToGamer failed with exception when gamer is not found"() {
        given:
        1 * gamerRepository.findById(_ as Long) >> Optional.ofNullable(null)

        when:
        gamerService.grantCreditToGamer(gamerId, gameId, 100)

        then:
        thrown(DataNotFoundException)

        where:
        gamerId | gameId | credit
        1L      | 1L     | 100
    }

    def "grantCreditToGamer failed with exception when game is not found"() {
        given:
        1 * gamerRepository.findById(_ as Long) >> Optional.of(buildGamer())
        1 * gameRepository.findById(_) >> Optional.ofNullable(null)

        when:
        gamerService.grantCreditToGamer(gamerId, gameId, 100)

        then:
        thrown(DataNotFoundException)

        where:
        gamerId | gameId | credit
        1L      | 1L     | 100
    }

    def "findGamersWithMaxCreditsForEachGameBaseOnLevel successfully"() {
        given:
        List<GamerCreditDTO> creditDTOList = Arrays.asList(
                new GamerCreditDTO(1L, "John Doe", 5L, GameName.AMONGUS.name(), GameLevel.NOOB, 100)
        )
        1 * gamerGameRepository.findGamersWithMaxCreditsForEachGame() >> creditDTOList

        when:
        List<GamerCreditDTO> creditDTOS = gamerGameRepository.findGamersWithMaxCreditsForEachGame()

        then:
        noExceptionThrown()
        assert creditDTOS.size() == 1
    }

    Gamer buildGamer() {
        return new Gamer(gamerId: 1L, name: "John Doe", gender: "Male", nickname: "JD", geography: "Kokkadal")
    }

    List<GameInput> buildGameInputList(GameInput... inputs) {
        List<GameInput> list = new ArrayList<>()
        for (GameInput element : inputs) {
            list.add(element)
        }
        return list
    }

    List<Game> buildGameList() {
        return Arrays.asList(
                buildGame1(),
                buildGame2(),
                buildGame3(),
                buildGame4(),
                buildGame5()
        )
    }

    Game buildGame1() {
        return new Game(gameId: 1L, name: GameName.FORTNITE.name())
    }

    Game buildGame2() {
        return new Game(gameId: 2L, name: GameName.CALL_OF_DUTY.name())
    }

    Game buildGame3() {
        return new Game(gameId: 3L, name: GameName.DOTA.name())
    }

    Game buildGame4() {
        return new Game(gameId: 4L, name: GameName.VALHALLA.name())
    }

    Game buildGame5() {
        return new Game(gameId: 5L, name: GameName.AMONGUS.name())
    }

    List<GamerGame> buildGamerGameList(Gamer gamer, Game game1, Game game2, Game game3, Game game4, Game game5) {
        return Arrays.asList(
                new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 1L), gamer: gamer, game: game1, level: GameLevel.NOOB, credits: 0),
                new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 2L), gamer: gamer, game: game2, level: GameLevel.PRO, credits: 0),
                new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 3L), gamer: gamer, game: game3, level: GameLevel.INVINCIBLE, credits: 0),
                new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 4L), gamer: gamer, game: game4, level: GameLevel.INVINCIBLE, credits: 0),
                new GamerGame(id: new GamerGameId(gamerId: 1L, gameId: 5L), gamer: gamer, game: game5, level: GameLevel.NOOB, credits: 0)
        )
    }

    GamerDTO guildGamerDTO() {
        return new GamerDTO(1L, "Saranga", "Male", "JD", "Kokkadal", buildGameDTOList())
    }

    List<GameDTO> buildGameDTOList() {
        return Arrays.asList(
                new GameDTO(1L, GameName.FORTNITE.name(), GameLevel.NOOB.name(), 0),
                new GameDTO(2L, GameName.CALL_OF_DUTY.name(), GameLevel.PRO.name(), 0),
                new GameDTO(3L, GameName.DOTA.name(), GameLevel.INVINCIBLE.name(), 0),
                new GameDTO(4L, GameName.VALHALLA.name(), GameLevel.INVINCIBLE.name(), 0),
                new GameDTO(5L, GameName.AMONGUS.name(), GameLevel.NOOB.name(), 0)
        )
    }
}


