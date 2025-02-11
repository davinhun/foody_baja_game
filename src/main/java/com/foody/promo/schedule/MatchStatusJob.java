package com.foody.promo.schedule;

import com.foody.promo.config.constants.MatchStatus;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class MatchStatusJob {
    public static final int ONE_MINUTE = 60_000;

    @Autowired
    private AdminService adminService;

    @Scheduled(fixedDelay = ONE_MINUTE)
    public void setMatchInProgressJob() {
        boolean needsUpdate = false;
        try {
            List<MatchModel> matches = adminService.getAllByStatus(MatchStatus.AVAILABLE);

            for (MatchModel match : matches) {
                long difference = match.getStartDate() - System.currentTimeMillis();
                if (difference < ONE_MINUTE) {
                    needsUpdate = true;
                    match.setStatus(MatchStatus.IN_PROGRESS);
                }
            }
            if (needsUpdate) {
                adminService.saveAll(matches);
            }

        } catch (Exception ignored) {

        }
    }
}
