package io.github.marcoant07.msclientes.application.representation;

import io.github.marcoant07.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSavaRequest {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }
}
