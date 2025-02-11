package com.foody.promo.converter.bet;

import com.foody.promo.converter.match.MatchEntityToMatchModelConverter;
import com.foody.promo.converter.user.UserEntityToUserModelConverter;
import com.foody.promo.domain.BetModel;
import com.foody.promo.entity.BetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class BetEntityToBetModelConverter implements Converter<BetEntity, BetModel> {


    private final MatchEntityToMatchModelConverter matchConverter;
    private final UserEntityToUserModelConverter userConverter;

    @Override
    public BetModel convert(BetEntity entity) {
        return new BetModel(entity.getId(), matchConverter.convert(entity.getMatch()), userConverter.convert(entity.getAppUser()),
                entity.getGuess(), entity.getPoint(), entity.getWinning(), entity.getPossibleWinning());
    }
}
