package com.bs.gamecenter.model;

public record GameDTO(Long gameId,
                      String name,
                      String level,
                      int credits) {
}
