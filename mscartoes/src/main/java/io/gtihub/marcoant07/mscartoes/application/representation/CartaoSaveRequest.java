package io.gtihub.marcoant07.mscartoes.application.representation;

import io.gtihub.marcoant07.mscartoes.domain.BandeiraCartao;
import io.gtihub.marcoant07.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite);
    }
}
