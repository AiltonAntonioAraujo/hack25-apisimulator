package br.caixa.gov.credito.apisimulador.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SistemaAmortizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_SISTEMA_AMORTIZACAO", nullable = false)
    private TipoSistemaAmrotizacao tipo;

    @ElementCollection
    private Collection<Parcela> parcelas;

    /**
     * @param prazo
     * @param valorDesejado
     * @param taxaJurosMensal
     * @return SistemaAmortizacao de parcelas com a tabela Price
     * 
     *         P = (PV * i) / (1 – (1 + i)^-n), onde P é a parcela, PV é o valor
     *         presente (principal), i é a taxa de juros e n é o número de
     *         prestações
     */
    public static SistemaAmortizacao calcularPrice(Integer prazo, BigDecimal valorDesejado, Double taxaJurosMensal) {
        Collection<Parcela> parcelas = new ArrayList<>();

        BigDecimal prestacao = valorDesejado.multiply(BigDecimal.valueOf(taxaJurosMensal))
                .divide(BigDecimal.ONE.subtract(BigDecimal.valueOf(Math.pow(1 + taxaJurosMensal, -prazo))), 10, RoundingMode.HALF_UP);
        BigDecimal saldoDevedor = valorDesejado;

        for (int i = 1; i <= prazo; i++) {
            BigDecimal valorJuros = saldoDevedor.multiply(BigDecimal.valueOf(taxaJurosMensal));
            BigDecimal valorAmortizacao = prestacao.subtract(valorJuros);
            saldoDevedor.subtract(valorAmortizacao);

            Parcela parcela = new Parcela();
            parcela.setNumero(i);
            parcela.setValorAmortizacao(valorAmortizacao);
            parcela.setValorJuros(valorJuros);
            parcela.setValorPrestacao(prestacao);

            parcelas.add(parcela);
        }

        return SistemaAmortizacao.builder().tipo(TipoSistemaAmrotizacao.PRICE).parcelas(parcelas).build();
    }

    /**
     * Calcula o sistema SAC (Sistema de Amortização Constante).
     * 
     * @param prazo           Quantidade de parcelas
     * @param valorDesejado   Valor principal do empréstimo
     * @param taxaJurosMensal Taxa de juros mensal (ex: 0.01 para 1%)
     * @return SistemaAmortizacao com parcelas calculadas com SAC
     */
    public static SistemaAmortizacao calcularSAC(Integer prazo, BigDecimal valorDesejado,
            Double taxaJurosMensal) {

        Collection<Parcela> parcelas = new ArrayList<>();
        BigDecimal amortizacao = valorDesejado.divide(BigDecimal.valueOf(prazo), 10, RoundingMode.HALF_UP);
        BigDecimal saldoDevedor = valorDesejado;

        for (int i = 1; i <= prazo; i++) {
            BigDecimal valorJuros = saldoDevedor.multiply(BigDecimal.valueOf(taxaJurosMensal));
            BigDecimal valorPrestacao = amortizacao.add(valorJuros);

            Parcela parcela = new Parcela();
            parcela.setNumero(i);
            parcela.setValorAmortizacao(amortizacao);
            parcela.setValorJuros(valorJuros);
            parcela.setValorPrestacao(valorPrestacao);

            parcelas.add(parcela);

            saldoDevedor = saldoDevedor.subtract(amortizacao);
        }

        return SistemaAmortizacao.builder().tipo(TipoSistemaAmrotizacao.SAC).parcelas(parcelas).build();
    }
}
