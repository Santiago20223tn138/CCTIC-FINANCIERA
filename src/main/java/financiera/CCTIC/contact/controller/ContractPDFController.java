package financiera.CCTIC.contact.controller;

import financiera.CCTIC.contact.service.ContractPDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/contrato")
@CrossOrigin(origins = {"*"})
public class ContractPDFController {

    @Autowired
    ContractPDFService contractPDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getPdf(@PathVariable int id) throws IOException {
        return contractPDFService.contractPDF(id);
    }
}
