package com.bs.gamecenter.model;

import java.util.List;

public record GamerInput(String name,
                         String gender,
                         String nickname,
                         String geography,
                         List<GameInput> games) {
}
