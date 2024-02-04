package com.bs.gamecenter.model.entity;


import com.bs.gamecenter.utility.enums.GameLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "gamer_games")
public class GamerGame {

    @EmbeddedId
    private GamerGameId id;

    @ManyToOne
    @MapsId("gamerId")
    @JoinColumn(name = "gamer_id")
    private Gamer gamer;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private GameLevel level;

    private int credits;

}
