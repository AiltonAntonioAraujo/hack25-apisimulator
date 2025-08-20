package br.caixa.gov.credito.apisimulador.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.caixa.gov.credito.apisimulador.service.SimulacaoService;
import br.caixa.gov.credito.apisimulador.service.dto.SimulacaoRequestDTO;
import br.caixa.gov.credito.apisimulador.service.dto.SimulacaoResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/simulacoes")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<SimulacaoResponseDTO> criarSimulacao(
            @Valid @RequestBody SimulacaoRequestDTO simulacaoRequest) {
        try {
            return new ResponseEntity<>(simulacaoService.criarSimulacao(simulacaoRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Collection<SimulacaoResponseDTO>> listarTodasAsSimulacoes() {
        return new ResponseEntity<>(simulacaoService.getAllSimulacoes(), HttpStatus.OK);
    }

    @GetMapping("/{idSimulacao}/{idProduto}")
    @ResponseBody
    public ResponseEntity<Collection<SimulacaoResponseDTO>> listarSimulacoesProduto(
            @Digits(integer = 8, fraction = 0, message = "O identificador deve conter até 8 dígitos") @Positive(message = "O identificador deve ser positivo")
            @PathVariable Integer idSimulacao,

            @Digits(integer = 8, fraction = 0, message = "O codigo do produto deve conter até 8 dígitos") @Positive(message = "O codigo do produto deve ser positivo")
            @PathVariable Integer idProduto) {
        try {
            return new ResponseEntity<>(simulacaoService.findByIdAndProduto(idSimulacao, idProduto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
