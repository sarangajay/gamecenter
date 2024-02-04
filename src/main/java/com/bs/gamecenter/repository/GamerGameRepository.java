package com.bs.gamecenter.repository;


import com.bs.gamecenter.model.GamerCreditDTO;
import com.bs.gamecenter.model.entity.GamerGame;
import com.bs.gamecenter.model.entity.GamerGameId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
