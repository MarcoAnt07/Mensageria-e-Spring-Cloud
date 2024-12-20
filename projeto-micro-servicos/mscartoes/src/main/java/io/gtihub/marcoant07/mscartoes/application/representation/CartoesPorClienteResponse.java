package io.gtihub.marcoant07.mscartoes.application.representation;

import io.gtihub.marcoant07.mscartoes.domain.BandeiraCartao;
import io.gtihub.marcoant07.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartoesPorClienteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public CartoesPorClienteResponse(String nome, String bandeira, BigDecimal limiteLiberado) {
        this.nome = nome;
        this.bandeira = bandeira;
        this.limiteLiberado = limiteLiberado;
    }

    public static CartoesPorClienteResponse fromModel(ClienteCartao model){
        return new CartoesPorClienteResponse(
                model.getCartao().getNome(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }
}
