package com.bs.gamecenter.model.mapper;

import com.bs.gamecenter.model.GameInput;
import com.bs.gamecenter.model.GamerInput;
import com.bs.gamecenter.model.entity.Game;
import com.bs.gamecenter.model.entity.Gamer;
import com.bs.gamecenter.model.entity.GamerGame;
import com.bs.gamecenter.utility.enums.GameLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface EntityMapper {

    @Mapping(target = "gamerId", ignore = true)
    Gamer toGamerEntity(GamerInput gamerInput);

    Game toEntity(GameInput gameInput);
    @Mapping(target = "gamer.gamerId", source = "gamer.gamerId")
    @Mapping(target = "game.gameId", source = "game.gameId")
    @Mapping(target = "gamer", source = "gamer")
    @Mapping(target = "game", source = "game")
    @Mapping(target = "level", source = "level")
    @Mapping(target = "credits", source = "credits")
    GamerGame toGamerGameEntity(Gamer gamer, Game game, GameLevel level, int credits);

}