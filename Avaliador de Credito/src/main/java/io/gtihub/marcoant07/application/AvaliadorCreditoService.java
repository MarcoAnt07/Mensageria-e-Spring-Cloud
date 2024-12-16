package io.gtihub.marcoant07.application;

import io.gtihub.marcoant07.domain.model.DadosCliente;
import io.gtihub.marcoant07.domain.model.SituacaoCliente;
import io.gtihub.marcoant07.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    @Autowired
    private ClienteResourceClient client;

    public SituacaoCliente obterSituacaoCliente(String cpf){

        ResponseEntity<DadosCliente> dadosClienteResponse = client.dadosCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }
}
