package com.bs.gamecenter.repository;

import com.bs.gamecenter.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByNameIn(List<String> names);
}
