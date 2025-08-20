package br.caixa.gov.credito.apisimulador.application.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.caixa.gov.credito.apisimulador.domain.Produto;
import br.caixa.gov.credito.apisimulador.domain.Simulacao;

public interface SimulacaoRepositoryJpa extends JpaRepository<Simulacao, Integer> {

    Optional<Collection<Simulacao>> findByIdSimulacaoAndProduto(Integer idSimulacao, Produto produto);

    Page<Simulacao> findByIdSimulacaoAndProduto(Integer idSimulacao, Produto produto, Pageable pageable);

    /// Método com filtros idSimulacao opcional, ordenacao e paginação
    @Query("SELECT s FROM Simulacao s WHERE " + "(?1 IS NULL OR s.idSimulacao = ?1) AND "
            + "(?2 IS NULL OR s.produto.id = ?2) ")
    Page<Simulacao> buscarSimulacoesComFiltros(@Param("idSimulacao") Integer idSimulacao,
            @Param("idProduto") Integer idProduto, Pageable pageable);

}
