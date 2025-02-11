package com.foody.promo.schedule;

import com.foody.promo.domain.UserModel;
import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class RankingJob {
    public static final int FIFTEEN_MIN = 15 * 60_000;

    public static final Map<Long, Long> USER_RANKING = new HashMap<>();

    @Autowired
    private UserService userService;

    @Scheduled(fixedDelay = FIFTEEN_MIN)
    public void updateRankings() {
        try {
            List<UserModel> allOrderByPoint = userService.getAllNotBannedOrderByPoint(false);
            USER_RANKING.clear();
            for (int i = 0; i < allOrderByPoint.size(); i++) {
                UserModel model = allOrderByPoint.get(i);
                USER_RANKING.put(model.getId(), (long) (i + 1));
            }
        } catch (Exception ignored) {
            
        }
    }
}
