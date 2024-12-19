package io.github.marcoant07.msclientes.application;

import io.github.marcoant07.msclientes.application.representation.ClienteSavaRequest;
import io.github.marcoant07.msclientes.domain.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@Slf4j
public class ClientesResource {

    private static final Logger log = LoggerFactory.getLogger(ClientesResource.class);

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSavaRequest request){
        Cliente cliente = request.toModel();

        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = service.getByCpf(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public String status(){
        return "ok";
    }
}
