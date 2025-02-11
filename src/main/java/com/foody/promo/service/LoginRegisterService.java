package com.foody.promo.service;

import com.foody.promo.domain.UserModel;
import com.foody.promo.entity.UserEntity;
import com.foody.promo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.foody.promo.config.auth.RoleTypes.ROLE_USER;
import static com.foody.promo.config.constants.EventTypes.REGISTERED_FROM_WEBSITE;
import static com.foody.promo.config.constants.Parameters.REGISTER_START_POINT;
import static com.foody.promo.config.path.PathConfig.*;
import static com.nimbusds.oauth2.sdk.util.StringUtils.isBlank;

@Service
public class LoginRegisterService {

    public static final String USER_ID = "userId";
    public static final String HASH = "hash";

    @Value("${app.url}")
    private String appUrl;
    @Value("${app.schema}")
    private String appSchema;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private BasicEmailService emailService;

    private Executor executor = Executors.newFixedThreadPool(3);

    @Transactional
    public void registerUser(UserModel userModel) {
        var entity =
                UserEntity.builder()
                        .name(userModel.getName())
                        .email(userModel.getEmail())
                        .availablePoints(REGISTER_START_POINT)
                        .lastEvent(REGISTERED_FROM_WEBSITE)
                        .lastChange(REGISTER_START_POINT)
                        .password(passwordEncoder.encode(userModel.getPassword()))
                        .roles(Set.of(ROLE_USER))
                        .emailConfirmed(false)
                        .build();

        var registeredUser = userRepository.save(entity);

        UriComponents uriComponents = buildUrl(registeredUser, CONFIRMATION);

        String subject = "Kedves " + userModel.getName() + "!\n" +
                "\n" +
                " \n" +
                "\n" +
                "Köszönjük, hogy regisztráltál a Foody - Tippelj és utazz! VB játékára.\n" +
                "\n" +
                "A regisztrációd megerősítéséhez, kérlek kattints az alábbi linkre:\n" +
                "\n" +
                uriComponents + "\n" +
                "\n" +
                " \n" +
                "\n" +
                "Az induláshoz jóváírunk neked 5.000 pontot. :)\n" +
                "\n" +
                "Felhívjuk a figyelmedet, hogy a mérkőzésekre csak 2022. november 10-től lesz lehetőséged fogadni.\n" +
                "\n" +
                "Addig viszont gyarapítsd tovább szorgosan a pontjaidat kódfeltöltéssel, hogy igazán felkészülten várd a kezdőrúgást! Erre már 2022. november 1-től lehetőséged adódik.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Tippelj, nyerj és tölts el egy hétvégét a zalakarosi MenDan Magic Spa &amp; Wellness Hotelben!" +
                "\n" +
                "\n" +
                "Sok sikert kívánunk!\n" +
                "\n" +
                "\n" +
                "A FOODY csapata.";

        executor.execute(() ->
                emailService.sendEmail(registeredUser.getEmail(),
                        "Regisztráció megerősítés", subject));
    }

    public boolean resetRequest(String email) {
        Optional<UserEntity> userByEmail = userRepository.findByEmailIgnoreCase(email);
        if (userByEmail.isPresent() && userByEmail.get().getOauthId() == null && !userByEmail.get().isBanned()) {
            UserEntity userEntity = userByEmail.get();
            UriComponents uriComponents = buildUrl(userEntity, RECEIVE_PASSWORD_RESET);

            executor.execute(() -> emailService.sendEmail(userEntity.getEmail(), "Jelszó visszaállítás",
                    "Új jelszó beállításához kattintson az alábbi linkre\n" + uriComponents));
            return true;
        }
        return false;
    }

    public boolean changePassword(Map<String, String> params) {
        Optional<UserEntity> userById = userRepository.findById(Long.parseLong(params.get(USER_ID)));
        if (userById.isPresent() && userById.get().getOauthId() == null) {
            UserEntity userEntity = userById.get();
            String password = params.get("password");
            if (password != null && !isBlank(password) && 5 < password.length() && password.length() < 500
                    && userEntity.hashCode() == Long.parseLong(params.get(HASH))) {
                userEntity.setPassword(passwordEncoder.encode(password));
                userEntity.setEmailConfirmed(true);
                userRepository.save(userEntity);
                return true;
            }
        }
        return false;
    }

    public boolean successFullEmailConfirm(Map<String, String> params) {
        Long userId = Long.parseLong(params.get(USER_ID));
        Optional<UserEntity> userById = userRepository.findById(userId);
        if (userById.isPresent() && userById.get().getOauthId() == null) {
            UserEntity userEntity = userById.get();
            if (!userEntity.getEmailConfirmed() && userEntity.hashCode() == Long.parseLong(params.get(HASH))) {
                userEntity.setEmailConfirmed(true);
                userRepository.save(userEntity);
                return true;
            }
        }
        return false;
    }

    public UserModel registerOauthUser(UserEntity entity) {
        return conversionService.convert(userRepository.save(entity), UserModel.class);
    }


    private UriComponents buildUrl(UserEntity userEntity, String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(appSchema)
                .host(appUrl)
                .path(REGISTER + "/" + path)
                .queryParam(USER_ID, userEntity.getId())
                .queryParam(HASH, userEntity.hashCode())
                .build();
    }

}
