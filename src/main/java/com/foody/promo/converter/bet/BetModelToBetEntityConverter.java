package com.foody.promo.converter.bet;

import com.foody.promo.converter.match.MatchModelToMatchEntityConverter;
import com.foody.promo.converter.user.UserModelToUserEntityConverter;
import com.foody.promo.domain.BetModel;
import com.foody.promo.entity.BetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class BetModelToBetEntityConverter implements Converter<BetModel, BetEntity> {


    private final MatchModelToMatchEntityConverter matchConverter;
    private final UserModelToUserEntityConverter userConverter;

    @Override
    public BetEntity convert(BetModel model) {
        return BetEntity.builder()
                .id(model.getId())
                .match(matchConverter.convert(model.getMatch()))
                .appUser(userConverter.convert(model.getUser()))
                .guess(model.getGuess())
                .point(model.getPoint())
                .winning(model.getWinning())
                .possibleWinning(model.getPossibleWinning())
                .build();
    }
}
