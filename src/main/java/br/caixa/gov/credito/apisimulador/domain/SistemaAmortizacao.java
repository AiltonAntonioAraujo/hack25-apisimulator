package br.caixa.gov.credito.apisimulador.domain;

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

    public static SistemaAmortizacao calcularPrice(int prazo, double valorDesejado, double taxaJurosMensal) {
        Collection<Parcela> parcelas = new ArrayList<>();
        double juros = taxaJurosMensal / 100.0;
        double prestacao = (valorDesejado * juros) / (1 - Math.pow(1 + juros, -prazo));
        double saldoDevedor = valorDesejado;

        for (int i = 1; i <= prazo; i++) {
            double valorJuros = saldoDevedor * juros;
            double valorAmortizacao = prestacao - valorJuros;
            saldoDevedor -= valorAmortizacao;

            Parcela parcela = new Parcela();
            parcela.setNumero(i);
            parcela.setValorAmortizacao(valorAmortizacao);
            parcela.setValorJuros(valorJuros);
            parcela.setValorPrestacao(prestacao);

            parcelas.add(parcela);
        }

        return SistemaAmortizacao.builder().tipo(TipoSistemaAmrotizacao.PRICE).parcelas(parcelas).build();
    }

    public static SistemaAmortizacao calcularSAC(int prazo, double valorDesejado, double taxaJurosMensal) {
        Collection<Parcela> parcelas = new ArrayList<>();
        double juros = taxaJurosMensal / 100.0;
        double amortizacao = valorDesejado / prazo;
        double saldoDevedor = valorDesejado;

        for (int i = 1; i <= prazo; i++) {
            double valorJuros = saldoDevedor * juros;
            double valorPrestacao = amortizacao + valorJuros;

            Parcela parcela = new Parcela();
            parcela.setNumero(i);
            parcela.setValorAmortizacao(amortizacao);
            parcela.setValorJuros(valorJuros);
            parcela.setValorPrestacao(valorPrestacao);

            parcelas.add(parcela);

            saldoDevedor -= amortizacao;
        }

        return SistemaAmortizacao.builder().tipo(TipoSistemaAmrotizacao.SAC).parcelas(parcelas).build();
    }
}
