package io.gtihub.marcoant07.domain.model;

import java.util.List;

public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;

    public RetornoAvaliacaoCliente(List<CartaoAprovado> listaCartoesAprovados) {
    }

    public List<CartaoAprovado> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoAprovado> cartoes) {
        this.cartoes = cartoes;
    }
}
