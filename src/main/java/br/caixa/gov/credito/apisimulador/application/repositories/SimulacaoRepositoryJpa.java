package br.caixa.gov.credito.apisimulador.application.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.caixa.gov.credito.apisimulador.domain.Produto;
import br.caixa.gov.credito.apisimulador.domain.Simulacao;


public interface SimulacaoRepositoryJpa extends JpaRepository<Simulacao, Integer> {
    
    Optional<Collection<Simulacao>> findByIdSimulacaoAndProduto(Integer idSimulacao, Produto produto);

    

}
