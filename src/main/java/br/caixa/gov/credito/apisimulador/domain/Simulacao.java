package br.caixa.gov.credito.apisimulador.domain;

import java.util.Collection;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "SIMULACAO")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_SIMULACAO")
    private Long id;
    
    @ManyToOne
    private Produto produto;

    @ElementCollection
    @CollectionTable(name = "RESULTADO_SIMULACAO", joinColumns = @JoinColumn(name = "CO_SIMULACAO"))
    private Collection<SistemaArmotizacao> resultadoSimulacao;

}
