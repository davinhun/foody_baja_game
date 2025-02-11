package com.foody.promo.config.constants;

import java.util.List;


public class MatchStatus {
    public static final String AVAILABLE = "available";
    public static final String IN_PROGRESS = "in_progress";
    public static final String FINISHED = "finished";


    public static List<String> getAllMatchStatus() {
        return List.of(AVAILABLE, IN_PROGRESS, FINISHED);
    }
}
