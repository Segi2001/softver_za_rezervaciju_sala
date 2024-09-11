/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.exception.SalaNotFoundException;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.exception.response.SalaErrorResponse;

/**
 *
 * @author Stefan
 */
@ControllerAdvice
public class SalaExceptionController {

    @ExceptionHandler
    public ResponseEntity<SalaErrorResponse> handleException(SalaNotFoundException e) {

        SalaErrorResponse response = new SalaErrorResponse();

        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<SalaErrorResponse> handleException(MethodArgumentTypeMismatchException e) {

        SalaErrorResponse response = new SalaErrorResponse();

        response.setMessage("Vrednost prosledjenih argumenata nije dobrog tipa");
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler
    public ResponseEntity<SalaErrorResponse> handleException(HttpMessageNotReadableException e) {

        SalaErrorResponse response = new SalaErrorResponse();

        response.setMessage("Vrednosti atributa nisu dobrog tipa!!!");
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler
    public ResponseEntity<SalaErrorResponse> handleException(IllegalArgumentException e) {

        SalaErrorResponse response = new SalaErrorResponse();

        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

}
