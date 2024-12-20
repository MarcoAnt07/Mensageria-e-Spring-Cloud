package io.gtihub.marcoant07.mscartoes.application;

import io.gtihub.marcoant07.mscartoes.domain.Cartao;
import io.gtihub.marcoant07.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    public Cartao save(Cartao cartao){
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda){
        BigDecimal rendaBigDecimal = BigDecimal.valueOf(renda);

        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
