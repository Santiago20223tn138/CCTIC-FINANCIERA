package financiera.CCTIC.paymetReceipt.controller;

import financiera.CCTIC.paymetReceipt.service.PaymentReceiptPDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/recibo-pago")
@CrossOrigin(origins = {"*"})
public class PaymentReceiptPDFController {

    @Autowired
    PaymentReceiptPDFService paymentReceiptPDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getPdf(@PathVariable int id) throws IOException {
        return paymentReceiptPDFService.paymentReceiptPDF((long) id);
    }




}
