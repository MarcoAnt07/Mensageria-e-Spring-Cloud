package io.gtihub.marcoant07.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;

    public List<CartaoAprovado> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoAprovado> cartoes) {
        this.cartoes = cartoes;
    }
}
