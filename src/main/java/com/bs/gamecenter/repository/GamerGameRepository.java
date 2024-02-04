package com.bs.gamecenter.repository;


import com.bs.gamecenter.model.entity.GamerGame;
import com.bs.gamecenter.model.entity.GamerGameId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GamerGameRepository extends JpaRepository<GamerGame, GamerGameId> {

}
