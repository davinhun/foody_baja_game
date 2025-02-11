package com.foody.promo.config;

import com.foody.promo.converter.bet.BetEntityToBetModelConverter;
import com.foody.promo.converter.bet.BetModelToBetEntityConverter;
import com.foody.promo.converter.code_upload_date.CodeUploadEntityToCodeUploadModelConverter;
import com.foody.promo.converter.code_upload_date.CodeUploadModelToCodeUploadEntityConverter;
import com.foody.promo.converter.invoice.InvoiceEntityToInvoiceModelConverter;
import com.foody.promo.converter.invoice.InvoiceModelToInvoiceEntityConverter;
import com.foody.promo.converter.match.MatchEntityToMatchModelConverter;
import com.foody.promo.converter.match.MatchModelToMatchEntityConverter;
import com.foody.promo.converter.match.StringToMatchModelConverter;
import com.foody.promo.converter.team.StringToTeamModelConverter;
import com.foody.promo.converter.team.TeamEntityToTeamModelConverter;
import com.foody.promo.converter.team.TeamModelToTeamEntityConverter;
import com.foody.promo.converter.user.UserEntityToUserModelConverter;
import com.foody.promo.converter.user.UserModelToUserEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());

        return bean;
    }

    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<>();
        TeamEntityToTeamModelConverter teamEntityToTeamModelConverter = new TeamEntityToTeamModelConverter();
        TeamModelToTeamEntityConverter teamModelToTeamEntityConverter = new TeamModelToTeamEntityConverter();
        converters.add(new StringToTeamModelConverter());
        converters.add(teamEntityToTeamModelConverter);
        converters.add(teamModelToTeamEntityConverter);

        MatchEntityToMatchModelConverter matchEntityToMatchModelConverter =
                new MatchEntityToMatchModelConverter(teamEntityToTeamModelConverter);
        converters.add(new StringToMatchModelConverter());
        converters.add(matchEntityToMatchModelConverter);
        MatchModelToMatchEntityConverter matchModelToMatchEntityConverter = new MatchModelToMatchEntityConverter(teamModelToTeamEntityConverter);
        converters.add(matchModelToMatchEntityConverter);


        converters.add(new InvoiceEntityToInvoiceModelConverter());
        converters.add(new InvoiceModelToInvoiceEntityConverter());

        UserEntityToUserModelConverter userEntityToUserModelConverter = new UserEntityToUserModelConverter();
        UserModelToUserEntityConverter userModelToUserEntityConverter = new UserModelToUserEntityConverter();

        converters.add(userEntityToUserModelConverter);
        converters.add(userModelToUserEntityConverter);

        converters.add(new BetEntityToBetModelConverter(matchEntityToMatchModelConverter, userEntityToUserModelConverter));
        converters.add(new BetModelToBetEntityConverter(matchModelToMatchEntityConverter, userModelToUserEntityConverter));

        converters.add(new CodeUploadEntityToCodeUploadModelConverter());
        converters.add(new CodeUploadModelToCodeUploadEntityConverter());

        return converters;
    }
}
