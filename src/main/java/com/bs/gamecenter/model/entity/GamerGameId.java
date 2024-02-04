package com.bs.gamecenter.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class GamerGameId implements Serializable {

    @Column(name = "gamer_id")
    private Long gamerId;

    @Column(name = "game_id")
    private Long gameId;

}
