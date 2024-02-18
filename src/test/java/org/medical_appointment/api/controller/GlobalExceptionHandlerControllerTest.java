package org.medical_appointment.api.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

class GlobalExceptionHandlerControllerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleExceptionWorksCorrectly() {
        //given
        String message = "Other exception occurred";
        Exception exception = new Exception();

        //when, then
        ModelAndView modelAndView = globalExceptionHandler.handleException(exception);

        //then
        Assertions.assertEquals("error", modelAndView.getViewName());
        Assertions.assertTrue(modelAndView.getModel().get("errorMessage").toString().contains(message));

    }

    @Test
    void handleNoFoundExceptionWorksCorrectly() {
        //given
        String message = "Could not find a resource";
        EntityNotFoundException entityNotFoundException = new EntityNotFoundException();

        //when, then
        ModelAndView modelAndView = globalExceptionHandler.handleNoFoundException(entityNotFoundException);

        //then
        Assertions.assertEquals("error", modelAndView.getViewName());
        Assertions.assertTrue(modelAndView.getModel().get("errorMessage").toString().contains(message));
    }

    @Test
    void handleUniqueKeyExceptionWorksCorrectly() {
        //given
        String message = "This medical licence number already exists";
        DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException(message);

        //when, then
        ModelAndView modelAndView = globalExceptionHandler.handleUniqueKeyException(dataIntegrityViolationException);

        //then
        Assertions.assertEquals("error", modelAndView.getViewName());
        Assertions.assertEquals(message, modelAndView.getModel().get("errorMessage"));
    }


}