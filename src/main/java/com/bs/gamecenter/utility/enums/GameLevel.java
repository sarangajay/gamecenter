package com.bs.gamecenter.utility.enums;

public enum GameLevel {

    NOOB, PRO, INVINCIBLE;

    public static GameLevel isValidGameLevel(String v) {
        for (GameLevel gameLevel : values()) {
            if (gameLevel.name().equals(v)) {
                return gameLevel;
            }
        }
        return null;
    }
}
