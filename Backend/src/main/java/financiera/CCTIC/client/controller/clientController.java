package financiera.CCTIC.client.controller;

import financiera.CCTIC.client.model.ClienteDTO;
import financiera.CCTIC.client.model.CreditoDTO;
import financiera.CCTIC.client.service.ClientService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/client")
@CrossOrigin(origins = {"*"})
public class clientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<Message> getAll() throws IOException {
        return clientService.findAll();
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<Message> getClienteById(@PathVariable int id) throws IOException {
        return clientService.getClienteById(id);
    }

    @GetMapping("/byCredito/{id}")
    public ResponseEntity<Message> getCreditoById(@PathVariable int id) throws IOException {
        return clientService.getCreditoById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getCreditoByIdByCliente(@PathVariable int id) throws IOException {
        return clientService.getCreditoByIdByCliente(id);
    }

    @PostMapping("/cliente/")
    public ResponseEntity<Message> postCLient(@RequestBody ClienteDTO clienteDTO) throws IOException {
        return clientService.postCLient(clienteDTO);
    }

    @PostMapping("/credito/")
    public ResponseEntity<Message> postCredito(@RequestBody CreditoDTO creditoDTO) throws IOException {
        return clientService.postCredito(creditoDTO);
    }

    @PutMapping("/credito/")
    public ResponseEntity<Message> pustCredito(@RequestBody CreditoDTO creditoDTO) throws IOException {
        return clientService.putCredito(creditoDTO);
    }
}
