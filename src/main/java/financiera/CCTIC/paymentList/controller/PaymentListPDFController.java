package financiera.CCTIC.paymentList.controller;

import financiera.CCTIC.paymentList.service.PaymentListPDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/lista-cobros")
@CrossOrigin(origins = {"*"})
public class PaymentListPDFController {
    @Autowired
    PaymentListPDFService paymentListPDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getListPdf(@PathVariable int id) throws IOException {
        return paymentListPDFService.paymentList(id);
    }
}
