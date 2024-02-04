package com.bs.gamecenter.repository;


import com.bs.gamecenter.model.entity.GamerGame;
import com.bs.gamecenter.model.entity.GamerGameId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GamerGameRepository extends JpaRepository<GamerGame, GamerGameId> {

    List<GamerGame> findByIdGamerId(Long gamerId);
}
