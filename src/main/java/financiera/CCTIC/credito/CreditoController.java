package financiera.CCTIC.credito;

import financiera.CCTIC.client.model.ClienteDTO;
import financiera.CCTIC.credito.model.HistorialPagosDTO;
import financiera.CCTIC.credito.service.CreditoService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/credito")
@CrossOrigin(origins = {"*"})
public class CreditoController {

    @Autowired
    CreditoService CreditoService;

    @PostMapping("/")
    public ResponseEntity<Message> postCLient(@RequestBody HistorialPagosDTO historialPagosDTO) throws IOException {
        return CreditoService.postCLient(historialPagosDTO);
    }


    @GetMapping("/consultaListaPorCobrar/{id}")
    public ResponseEntity<Message> consultaListaPorCobrar(@PathVariable int id) throws IOException {
        return CreditoService.consultaListaPorCobrar(id);
    }

    @PostMapping("/CorteDePago/{id}")
    public ResponseEntity<Message> CorteDePago(@PathVariable int id) throws IOException {
        return CreditoService.CorteDePago(id);
    }
}
