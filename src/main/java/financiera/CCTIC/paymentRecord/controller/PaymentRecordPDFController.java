package financiera.CCTIC.paymentRecord.controller;

import financiera.CCTIC.paymentRecord.service.PaymentRecordPDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/registro-pago")
@CrossOrigin(origins = {"*"})
public class PaymentRecordPDFController {

    @Autowired
    PaymentRecordPDFService paymentRecordPDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getPdf(@PathVariable int id) throws IOException{
        return paymentRecordPDFService.paymentRecordPDF(id);
    }
}
