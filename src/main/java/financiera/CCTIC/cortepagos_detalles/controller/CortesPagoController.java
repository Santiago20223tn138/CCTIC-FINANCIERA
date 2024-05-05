package financiera.CCTIC.cortepagos_detalles.controller;

import financiera.CCTIC.cortepagos_detalles.service.CortePagosService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/Corte")
@CrossOrigin(origins = {"*"})
public class CortesPagoController {

    @Autowired
    CortePagosService cortePagosService;

    @GetMapping("/CortePago/")
    public ResponseEntity<Message> getAllCortePago() throws IOException {
        return cortePagosService.findAllCorteDePagos();
    }

    @GetMapping("/promotora/{id}")
    public ResponseEntity<Message> getCorteByPromotora(@PathVariable int id) throws IOException {
        return cortePagosService.findByPromotora(id);
    }

    @GetMapping("/ByIdCortePago/{id}")
    public ResponseEntity<Message> getCortePagoById(@PathVariable int id) throws IOException {
        return cortePagosService.findByIdCorteDePagos(id);
    }

    @GetMapping("/getAllCorteDePagosDetalles/")
    public ResponseEntity<Message> getAllCorteDePagosDetalles() throws IOException {
        return cortePagosService.findAllCorteDePagosDetalles();
    }

    @GetMapping("/getByIdCorteDePagosDetalles/{id}")
    public ResponseEntity<Message> getCorteDePagosDetallesById(@PathVariable int id) throws IOException {
        return cortePagosService.findByIdCorteDePagosDetalles(id);
    }

    @GetMapping("/getByIdHistoricoCorteDePagosDetalles/{id}")
    public ResponseEntity<Message> getByIdHistoricoCorteDePagosDetallesById(@PathVariable int id) throws IOException {
        return cortePagosService.getByIdHistoricoCorteDePagosDetallesById(id);
    }
}
