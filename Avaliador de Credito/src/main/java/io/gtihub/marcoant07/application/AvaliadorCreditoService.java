package io.gtihub.marcoant07.application;

import feign.FeignException;
import io.gtihub.marcoant07.application.ex.DadosClienteNotFoundException;
import io.gtihub.marcoant07.application.ex.ErroComunicacaoMicroservicesException;
import io.gtihub.marcoant07.application.ex.ErroSolicitacaoCartaoException;
import io.gtihub.marcoant07.domain.model.*;
import io.gtihub.marcoant07.infra.clients.CartoesResouceClient;
import io.gtihub.marcoant07.infra.clients.ClienteResourceClient;
import io.gtihub.marcoant07.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    @Autowired
    private ClienteResourceClient clienteResourceClient;

    @Autowired
    private CartoesResouceClient cartoesResouceClient;

    @Autowired
    private SolicitacaoEmissaoCartaoPublisher publisher;


    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try{
            // Obtenção de dados do cliente
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);

            // Obtenção da lista de cartões do cliente
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResouceClient.getCartoesByCliente(cpf);

            // Instanciação direta de SituacaoCliente sem o builder
            SituacaoCliente situacaoCliente = new SituacaoCliente();
            situacaoCliente.setCliente(dadosClienteResponse.getBody());
            situacaoCliente.setCartoes(cartoesResponse.getBody());

            return situacaoCliente;
        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);

            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResouceClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();

            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitacaoEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try{
            publisher.solicitarCartao(dados);

            var protocolo = UUID.randomUUID().toString();

            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}