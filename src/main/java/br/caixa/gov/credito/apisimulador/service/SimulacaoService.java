package br.caixa.gov.credito.apisimulador.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.caixa.gov.credito.apisimulador.application.mapper.SimulacaoMapper;
import br.caixa.gov.credito.apisimulador.application.repositories.ProdutoRepositoryJPA;
import br.caixa.gov.credito.apisimulador.application.repositories.SimulacaoRepositoryJpa;
import br.caixa.gov.credito.apisimulador.domain.Produto;
import br.caixa.gov.credito.apisimulador.domain.Simulacao;
import br.caixa.gov.credito.apisimulador.domain.SistemaAmortizacao;
import br.caixa.gov.credito.apisimulador.domain.exception.ProdutoNaoEncontradoException;
import br.caixa.gov.credito.apisimulador.domain.exception.SimulacaoNaoEncontradaException;
import br.caixa.gov.credito.apisimulador.service.dto.SimulacaoRequestDTO;
import br.caixa.gov.credito.apisimulador.service.dto.SimulacaoResponseDTO;
import jakarta.annotation.PostConstruct;

@Service
public class SimulacaoService {

    @Autowired
    private SimulacaoRepositoryJpa simulacaoRepository;

    @Autowired
    private ProdutoRepositoryJPA produtoRepository;

    private Collection<Produto> produtos;

    @Autowired
    private SimulacaoMapper simulacaoMapper;

    @PostConstruct
    public void init() {
        this.produtos = produtoRepository.findAll();
    }

    public SimulacaoResponseDTO criarSimulacao(SimulacaoRequestDTO simulacaoRequest) {

        Integer dataFormatada = Integer.parseInt(LocalDate.now().format((DateTimeFormatter.ofPattern("uuuuMMd"))));

        simulacaoRepository.findById(dataFormatada).ifPresent(simulacao -> {
            throw new IllegalArgumentException("Já existe uma simulação com o ID: " + dataFormatada);
        });

        // Seleciona o produto adequado de acordo com os parâmetros da simulação
        Produto produto = getProdutoSimulacao(simulacaoRequest, this.produtos);

        Simulacao simulacao = Simulacao.builder().produto(produto)
                // Define o ID da simulação com base na data atual
                .idSimulacao(dataFormatada)
                // Realiza a simulação na tabela PRICE
                .resultadoSimulacao(SistemaAmortizacao.calcularPrice(simulacaoRequest.prazo(),
                        simulacaoRequest.valorDesejado(), produto.getPercentualTaxaJuros()))
                // Realiza a simulação na tabela SAC
                .resultadoSimulacao(SistemaAmortizacao.calcularSAC(simulacaoRequest.prazo(),
                        simulacaoRequest.valorDesejado(), produto.getPercentualTaxaJuros()))
                .build();

        return simulacaoMapper.toSimulacaoResponseDto(simulacaoRepository.save(simulacao));
    }

    private Produto getProdutoSimulacao(SimulacaoRequestDTO simulacao, Collection<Produto> produtos) {
        if (simulacao.valorDesejado() == null) {
            throw new IllegalArgumentException("O valor desejado não pode ser nulo.");
        }
        for (Produto produto : produtos) {
            boolean prazoOk = simulacao.prazo() >= produto.getNumeroMinimoMeses()
                    && produto.getNumeroMaximoMeses() != null && (simulacao.prazo() <= produto.getNumeroMaximoMeses());
            boolean valorOk = simulacao.valorDesejado().compareTo(produto.getValorMinimo()) >= 0
                    && produto.getValorMaximo() != null
                    && simulacao.valorDesejado().compareTo(produto.getValorMaximo()) <= 0;
            if (prazoOk && valorOk) {
                return produto;
            }
        }
        throw new ProdutoNaoEncontradoException("Nenhum produto atende aos parâmetros da simulação.");
    }

    public Collection<SimulacaoResponseDTO> getAllSimulacoes() {
        return simulacaoMapper.toSimulacaoResponseDtoList(simulacaoRepository.findAll());
    }

    public Map<String, Object> buscarSimulacoesComPaginacao(Integer idSimulacao, Integer idProduto, int pagina,
            int tamanho, String ordenacao, String direcao) {

        // Criar objeto de paginação com ordenação
        Sort.Direction dir = direcao.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(dir, ordenacao);
        Pageable paginacao = PageRequest.of(pagina, tamanho, sort);

        // Buscar página de simulações
        Page<Simulacao> paginaSimulacoes = simulacaoRepository.buscarSimulacoesComFiltros(idSimulacao, idProduto,
                paginacao);

        // Criar mapa com resultados e metadados
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("pagina", paginaSimulacoes.getNumber());
        resultado.put("totalPaginas", paginaSimulacoes.getTotalPages());
        resultado.put("qtdRegistrosPagina", paginaSimulacoes.getSize());
        resultado.put("qtdRegistros", paginaSimulacoes.getTotalElements());
        resultado.put("isPrimeiraPagina", paginaSimulacoes.isFirst());
        resultado.put("isUltimaPagina", paginaSimulacoes.isLast());
        resultado.put("registros", paginaSimulacoes.getContent());

        return resultado;
    }

    public Collection<SimulacaoResponseDTO> findByIdAndProduto(Integer idSimulacao, Integer idProduto) {
        Optional<Collection<Simulacao>> optionalSimulacao = simulacaoRepository.findByIdSimulacaoAndProduto(idSimulacao,
                this.produtos.stream().filter(produto -> produto.getId().equals(idProduto)).findFirst()
                        .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado.")));

        if (!optionalSimulacao.isPresent()) {
            throw new SimulacaoNaoEncontradaException("Simulação não encontrada.");
        }

        return simulacaoMapper.toSimulacaoResponseDtoList(optionalSimulacao.get());
    }
}
