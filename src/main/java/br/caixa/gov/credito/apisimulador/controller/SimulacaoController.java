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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/simulacoes")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;


    @Operation(summary = "Registra uma nova Simulação de Crédito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Simulação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    @PostMapping
    @ResponseBody
    public ResponseEntity<SimulacaoResponseDTO> criarSimulacao(
            @Valid @RequestBody SimulacaoRequestDTO simulacaoRequest) {
            return new ResponseEntity<>(simulacaoService.criarSimulacao(simulacaoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todas as Simulação de Crédito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacão realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    @ResponseBody
    public ResponseEntity<Collection<SimulacaoResponseDTO>> listarTodasAsSimulacoes() {
        return new ResponseEntity<>(simulacaoService.getAllSimulacoes(), HttpStatus.OK);
    }

    @Operation(summary = "Lista as Simulação de Crédito pelo ID da Simulação e pelo ID do Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacão realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Simulação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{idSimulacao}/{idProduto}")
    @ResponseBody
    public ResponseEntity<Collection<SimulacaoResponseDTO>> listarSimulacoesProduto(
            @Digits(integer = 8, fraction = 0, message = "O identificador deve conter até 8 dígitos") @Positive(message = "O identificador deve ser positivo")
            @PathVariable Integer idSimulacao,

            @Digits(integer = 8, fraction = 0, message = "O codigo do produto deve conter até 8 dígitos") @Positive(message = "O codigo do produto deve ser positivo")
            @PathVariable Integer idProduto) {

            return new ResponseEntity<>(simulacaoService.findByIdAndProduto(idSimulacao, idProduto), HttpStatus.OK);
  
    }

}
