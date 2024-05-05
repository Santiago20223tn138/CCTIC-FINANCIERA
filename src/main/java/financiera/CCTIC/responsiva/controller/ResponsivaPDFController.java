package financiera.CCTIC.responsiva.controller;

import financiera.CCTIC.responsiva.service.ResponsivaPDFService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/financiera/responsiva")
@CrossOrigin(origins = {"*"})
public class ResponsivaPDFController {

    @Autowired
    ResponsivaPDFService responsivaPDFService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Message> getPdf(@PathVariable int id) throws IOException {
        return responsivaPDFService.responsivaPDF(id);
    }
}
