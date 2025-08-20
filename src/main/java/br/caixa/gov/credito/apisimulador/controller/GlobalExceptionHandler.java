package br.caixa.gov.credito.apisimulador.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import br.caixa.gov.credito.apisimulador.domain.exception.ProdutoNaoEncontradoException;
import br.caixa.gov.credito.apisimulador.domain.exception.Response;
import br.caixa.gov.credito.apisimulador.domain.exception.SimulacaoNaoEncontradaException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Response> handleProdutoNotFound(ProdutoNaoEncontradoException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SimulacaoNaoEncontradaException.class)
    public ResponseEntity<Response> handleSimulacaoNotFound(SimulacaoNaoEncontradaException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ NoResourceFoundException.class })
    public ResponseEntity<Response> handleInvalidRequestResource(NoResourceFoundException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<Response> handleInvalidMessageResponse(HttpMessageNotReadableException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Response> handleInvalidRequestParameter(MethodArgumentTypeMismatchException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Response> handleInvalidaArgumentRequest(IllegalArgumentException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
