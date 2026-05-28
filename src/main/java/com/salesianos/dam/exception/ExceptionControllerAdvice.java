package com.salesianos.dam.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionControllerAdvice {

    // coge los errores que ocurren al rellenar formularios cuando algún dato no está bien
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public String handleBindException(org.springframework.validation.BindException ex, Model model) {
        //obtenemos el primer mensaje de error que hayamos configurado en la entidad
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        model.addAttribute("message", msg);
        // le mostramos al usuario la pantalla de error.html con el mensaje
        return "error";
    }

    // atrapa los errores de validación de base de datos o restricciones de campos (como si el número de teléfono no cumple tener 9 dígitos)
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public String handleConstraintViolation(jakarta.validation.ConstraintViolationException ex, Model model) {
        String msg = ex.getConstraintViolations().iterator().next().getMessage();
        model.addAttribute("message", msg);
        return "error";
    }

    // Si ocurre cualquier otro tipo de error no puesto antes se pone aquí y muestra el mensaje del error
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
