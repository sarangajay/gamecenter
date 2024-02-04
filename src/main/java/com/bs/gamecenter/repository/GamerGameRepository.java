package com.bs.gamecenter.repository;


import com.bs.gamecenter.model.GamerCreditDTO;
import com.bs.gamecenter.model.GamerDataDTO;
import com.bs.gamecenter.model.entity.GamerGame;
import com.bs.gamecenter.model.entity.GamerGameId;
import com.bs.gamecenter.utility.enums.GameLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GamerGameRepository extends JpaRepository<GamerGame, GamerGameId> {

    List<GamerGame> findByIdGamerId(Long gamerId);

    @Query("SELECT new com.bs.gamecenter.model.GamerCreditDTO(" +
            "gamer.gamerId, gamer.name, " +
            "game.gameId, game.name, " +
            "gg.level, gg.credits) " +
            "FROM GamerGame gg " +
            "JOIN gg.gamer gamer " +
            "JOIN gg.game game " +
            "WHERE gg.credits = (" +
            "   SELECT MAX(gg2.credits) FROM GamerGame gg2 " +
            "   WHERE gg2.game = gg.game " +
            "   AND gg2.level = gg.level) ")
    List<GamerCreditDTO> findGamersWithMaxCreditsForEachGame();

    @Query("SELECT NEW com.bs.gamecenter.model.GamerDataDTO(" +
            "g.name, g.gender, g.nickname, g.geography, gm.name, gg.level) " +
            "FROM GamerGame gg " +
            "JOIN gg.gamer g " +
            "JOIN gg.game gm " +
            "WHERE (:gamerName IS NULL OR g.name = :gamerName) " +
            "AND (:level IS NULL OR gg.level = :level) " +
            "AND (:gameName IS NULL OR gm.name = :gameName) " +
            "AND (:geography IS NULL OR g.geography = :geography)")
    List<GamerDataDTO> gameDataSearch(
            @Param("gamerName") String gamerName,
            @Param("gameName") String gameName,
            @Param("level") GameLevel level,
            @Param("geography") String geography);
}
