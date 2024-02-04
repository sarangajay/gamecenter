package com.bs.gamecenter.model.mapper;
import com.bs.gamecenter.model.GameDTO;
import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.entity.Game;
import com.bs.gamecenter.model.entity.Gamer;
import com.bs.gamecenter.model.entity.GamerGame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameDataMapper {

    public GamerDTO toGamerDTO(Gamer gamer, List<GamerGame> gamerGameList) {
        GamerDTO.GamerDTOBuilder gamerDTOBuilder = buildGamerDTO(gamer);
        gamerDTOBuilder.games(mapGamerGamesToGameDTOs(gamerGameList));
        return gamerDTOBuilder.build();
    }

    public GamerDTO.GamerDTOBuilder buildGamerDTO(Gamer gamer) {
        GamerDTO.GamerDTOBuilder gamerDTOBuilder = GamerDTO.builder();
        gamerDTOBuilder.gamerId(gamer.getGamerId());
        gamerDTOBuilder.name(gamer.getName());
        gamerDTOBuilder.gender(gamer.getGender());
        gamerDTOBuilder.geography(gamer.getGeography());
        gamerDTOBuilder.nickname(gamer.getNickname());
        return gamerDTOBuilder;
    }


    private List<GameDTO> mapGamerGamesToGameDTOs(List<GamerGame> gamerGameList) {
        List<GameDTO> gameDTOs = new ArrayList<>();
        for (GamerGame gamerGame : gamerGameList) {
            Game game = gamerGame.getGame();
            String level = gamerGame.getLevel().name();
            GameDTO gameDTO = new GameDTO(game.getGameId(), game.getName(), level, gamerGame.getCredits());
            gameDTOs.add(gameDTO);
        }
        return gameDTOs;
    }

}
