package com.salesianos.dam.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(org.springframework.validation.BindException.class)
    public String handleBindException(org.springframework.validation.BindException ex, Model model) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        model.addAttribute("message", msg);
        return "error";
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public String handleConstraintViolation(jakarta.validation.ConstraintViolationException ex, Model model) {
        String msg = ex.getConstraintViolations().iterator().next().getMessage();
        model.addAttribute("message", msg);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
