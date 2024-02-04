package com.bs.gamecenter.utility.enums;

public enum GameName {

    FORTNITE("Fortnite"),
    CALL_OF_DUTY("Call of duty"),
    DOTA("Dota"),
    VALHALLA("Valhalla"),
    AMONGUS("Amongus");

    private final String name;

    GameName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
