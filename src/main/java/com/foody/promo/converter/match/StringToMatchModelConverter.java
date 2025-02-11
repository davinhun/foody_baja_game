package com.foody.promo.converter.match;

import com.foody.promo.domain.MatchModel;
import com.foody.promo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;
@Component
public class StringToMatchModelConverter implements Converter<String, MatchModel> {

    @Autowired
    private MatchService matchService;

    @Override
    public MatchModel convert(String match) {
        Optional<MatchModel> byId = matchService.getById(Long.valueOf(match));
        if (byId.isEmpty()) {
            throw new ValidationException("Not existing id");
        }
        return byId.get();
    }
}
