package financiera.CCTIC.paymentCutInfo.controller;

import financiera.CCTIC.paymentCutInfo.service.PaymentCutInfoPDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/cortePago-info")
@CrossOrigin(origins = {"*"})
public class PaymentCutInfoPDFController {
    @Autowired
    PaymentCutInfoPDFService paymentCutInfoPDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getInfoPaymentCutPdf(@PathVariable int id) throws IOException {
        return paymentCutInfoPDFService.paymentCutInfo(id);
    }
}
