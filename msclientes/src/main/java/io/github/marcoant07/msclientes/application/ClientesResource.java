package io.github.marcoant07.msclientes.application;

import io.github.marcoant07.msclientes.application.representation.ClienteSavaRequest;
import io.github.marcoant07.msclientes.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
public class ClientesResource {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSavaRequest request){
        Cliente cliente = request.toModel();

        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()  // A URL atual de onde a requisição foi feita
                .query("cpf={cpf}")    // Incluindo o CPF como query param
                .buildAndExpand(cliente.getCpf())  // Expande o CPF na URL
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
}
