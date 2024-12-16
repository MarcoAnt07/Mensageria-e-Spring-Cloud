package io.gtihub.marcoant07.application;

import feign.FeignException;
import io.gtihub.marcoant07.application.ex.DadosClienteNotFoundException;
import io.gtihub.marcoant07.application.ex.ErroComunicacaoMicroservicesException;
import io.gtihub.marcoant07.domain.model.CartaoCliente;
import io.gtihub.marcoant07.domain.model.DadosCliente;
import io.gtihub.marcoant07.domain.model.SituacaoCliente;
import io.gtihub.marcoant07.infra.clients.CartoesResouceClient;
import io.gtihub.marcoant07.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    @Autowired
    private ClienteResourceClient clienteResourceClient;

    @Autowired
    private CartoesResouceClient cartoesResouceClient;


    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);

            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResouceClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }
}
