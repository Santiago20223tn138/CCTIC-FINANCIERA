package financiera.CCTIC.pagare.controller;

import financiera.CCTIC.pagare.service.PagarePDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/pagare")
@CrossOrigin(origins = {"*"})
public class PagarePDFController {

    @Autowired
    PagarePDFService pagarePDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getPdf(@PathVariable int id) throws IOException {
        return pagarePDFService.pagarePDF(id);
    }
}
