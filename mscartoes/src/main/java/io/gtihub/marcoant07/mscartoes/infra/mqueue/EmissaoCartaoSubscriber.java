package io.gtihub.marcoant07.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gtihub.marcoant07.mscartoes.domain.Cartao;
import io.gtihub.marcoant07.mscartoes.domain.ClienteCartao;
import io.gtihub.marcoant07.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.gtihub.marcoant07.mscartoes.infra.repository.CartaoRepository;
import io.gtihub.marcoant07.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload){

        try{
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);

            Cartao cartao = repository.findById(dadosSolicitacaoEmissaoCartao.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dadosSolicitacaoEmissaoCartao.getCpf());
            clienteCartao.setLimite(dadosSolicitacaoEmissaoCartao.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
