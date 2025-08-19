package br.caixa.gov.credito.apisimulador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.caixa.gov.credito.apisimulador.domain.Produto;
import br.caixa.gov.credito.apisimulador.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Produto>> getAll() {
        return new ResponseEntity<>(produtoService.listarTodosProdutos(), HttpStatus.OK);
    }
}
