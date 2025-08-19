package br.caixa.gov.credito.apisimulador.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.caixa.gov.credito.apisimulador.domain.Produto;

public interface ProdutoRepositoryJPA extends JpaRepository<Produto, Integer> {

    Produto findByNome(String nome);


}
