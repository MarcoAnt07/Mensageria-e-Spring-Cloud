package io.gtihub.marcoant07.domain.model;

import java.math.BigDecimal;

public class DadosSolicitacaoEmissaoCartao {

    private Long idCartao;
    private String cpf;
    private String endereco;
    private BigDecimal limiteLiberado;

    public Long getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Long idCartao) {
        this.idCartao = idCartao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getLimiteLiberado() {
        return limiteLiberado;
    }

    public void setLimiteLiberado(BigDecimal limiteLiberado) {
        this.limiteLiberado = limiteLiberado;
    }

    public DadosSolicitacaoEmissaoCartao() {
    }

    public DadosSolicitacaoEmissaoCartao(String cpf, String endereco, BigDecimal limiteLiberado) {
        this.cpf = cpf;
        this.endereco = endereco;
        this.limiteLiberado = limiteLiberado;
    }

    public DadosSolicitacaoEmissaoCartao(Long idCartao, String cpf, String endereco, BigDecimal limiteLiberado) {
        this.idCartao = idCartao;
        this.cpf = cpf;
        this.endereco = endereco;
        this.limiteLiberado = limiteLiberado;
    }
}
