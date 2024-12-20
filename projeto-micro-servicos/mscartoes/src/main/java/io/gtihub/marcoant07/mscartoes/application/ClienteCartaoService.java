package io.gtihub.marcoant07.mscartoes.application;

import io.gtihub.marcoant07.mscartoes.domain.ClienteCartao;
import io.gtihub.marcoant07.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    @Autowired
    private ClienteCartaoRepository repository;

    public List<ClienteCartao> listarCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
