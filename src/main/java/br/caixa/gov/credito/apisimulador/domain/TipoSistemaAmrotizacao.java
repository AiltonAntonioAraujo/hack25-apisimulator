package br.caixa.gov.credito.apisimulador.domain;

public enum TipoSistemaAmrotizacao {

    SAC("Sistema de Amortização Constante"),
    PRICE("Sistema Francês de Amortização");

    private final String descricao;

    TipoSistemaAmrotizacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
