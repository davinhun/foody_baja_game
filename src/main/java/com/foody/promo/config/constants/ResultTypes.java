package com.foody.promo.config.constants;

import java.util.List;

public class ResultTypes {
    public static final String TEAM1 = "team1";
    public static final String DRAW = "draw";
    public static final String TEAM2 = "team2";


    public static List<String> getAllResultTypes() {
        return List.of(TEAM1, DRAW, TEAM2);
    }

}
