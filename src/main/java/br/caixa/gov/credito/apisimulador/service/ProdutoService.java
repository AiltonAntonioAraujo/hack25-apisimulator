package br.caixa.gov.credito.apisimulador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.caixa.gov.credito.apisimulador.application.repositories.ProdutoRepositoryJPA;
import br.caixa.gov.credito.apisimulador.domain.Produto;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepositoryJPA produtoRepository;

    public Produto buscarProdutoPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Integer id) {
        produtoRepository.deleteById(id);
    }

}
