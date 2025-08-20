package br.caixa.gov.credito.apisimulador.domain;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Entity
@Data
@Table(name = "SIMULACAO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Simulacao {

    @Id
    @Column(name = "CO_SIMULACAO")
    private Integer idSimulacao;
    
    @ManyToOne
    private Produto produto;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SIMULACAO_CO_SIMULACAO", referencedColumnName = "CO_SIMULACAO")
    @Singular(value = "resultadoSimulacao")
    private Collection<SistemaAmortizacao> resultadoSimulacao;

}
