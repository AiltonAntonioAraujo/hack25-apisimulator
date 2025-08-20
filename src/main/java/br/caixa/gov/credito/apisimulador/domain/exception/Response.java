package br.caixa.gov.credito.apisimulador.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private String message;
    private Integer status;
    

}
