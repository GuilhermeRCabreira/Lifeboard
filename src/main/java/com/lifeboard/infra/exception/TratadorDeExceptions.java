package com.lifeboard.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice(annotations = Controller.class)
public class TratadorDeExceptions {

    private static final Logger logger = LoggerFactory.getLogger(TratadorDeExceptions.class);

    @ExceptionHandler({NoSuchElementException.class, EntityNotFoundException.class})
    public String tratarErro404() {
        return "erro/404";
    }

    @ExceptionHandler(Exception.class)
    public String tratarErro500(Exception e) {
        logger.error("Erro inesperado na aplicação", e);
        return "erro/500";
    }
}