package com.foody.promo.config;

import com.foody.promo.domain.PointAndRankingModel;
import com.foody.promo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.foody.promo.config.constants.Parameters.POINT_AND_RANK;
import static com.foody.promo.config.path.PathConfig.*;
import static com.foody.promo.utils.Utils.getUserId;


@Controller
@ControllerAdvice
public class ErrorHandler implements ErrorController {

    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_DETAIL = "errorDetail";
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    @Autowired
    private UserService userService;

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping(ERROR)
    public String error(Model model, HttpServletResponse response, HttpServletRequest request) {
        int errorCode = response.getStatus();
        if (errorCode == 404) {
            return ERROR_NOT_FOUND;
        }
        try {
            model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        } catch (Exception e) {
            model.addAttribute(POINT_AND_RANK, new PointAndRankingModel(0L, 0L));
        }
        model.addAttribute(ERROR_CODE, errorCode);
        model.addAttribute(ERROR_DETAIL, response.getHeaders("errorMessage"));

        return ERROR_DETAILED_ERROR;
    }

    @ExceptionHandler({ValidationException.class, AccessDeniedException.class})
    public void handleRuntimeException(RuntimeException exception, ServletWebRequest webRequest) throws IOException {
        logger.error(exception.getMessage(), exception);
        webRequest.getResponse().setHeader("errorMessage", exception.getMessage());
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

    }

    @ExceptionHandler(BindException.class)
    public void handleBindException(BindException exception, ServletWebRequest webRequest, BindingResult bindingResult) throws IOException {
        logger.error(exception.getMessage(), exception);
        String messages = exception.getBindingResult().getAllErrors().stream()
                .map(error -> getBindingException((FieldError) error)).collect(Collectors.joining(","));
        webRequest.getResponse().setHeader("errorMessage", messages);
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

    }

    @ExceptionHandler(MailSendException.class)
    public void handleSendException(Exception exception, ServletWebRequest webRequest) throws IOException {
        logger.error(exception.getMessage(), exception);
        String messages = "E-mail küldés sikertelen! Próbálja meg késöbb vagy másik e-mail címmel";
        webRequest.getResponse().setHeader("errorMessage", messages);
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public void handleAll(Exception exception, ServletWebRequest webRequest) throws IOException {
        logger.error(exception.getMessage(), exception);
        webRequest.getResponse().setHeader("errorMessage", exception.getMessage());
        webRequest.getResponse().sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());

    }

    private String getBindingException(FieldError fieldError) {
        return "Field: " + fieldError.getField() + " Exception: " + fieldError.getDefaultMessage();
    }
}