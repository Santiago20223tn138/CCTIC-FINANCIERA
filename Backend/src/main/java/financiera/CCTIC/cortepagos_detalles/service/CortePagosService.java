package financiera.CCTIC.cortepagos_detalles.service;

import financiera.CCTIC.cortepagos.model.Cortepagos;
import financiera.CCTIC.cortepagos_detalles.repository.CortePagosRepository;
import financiera.CCTIC.cortepagos_detalles.repository.Cortepagos_detallesRepository;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CortePagosService {

    @Autowired
    CortePagosRepository cortePagosRepository;

    @Autowired
    Cortepagos_detallesRepository cortepagos_detallesRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllCorteDePagos(){
        return  new ResponseEntity<>(new Message("OK",false,cortePagosRepository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByIdCorteDePagos(long id){
        return  new ResponseEntity<>(new Message("OK",false,cortePagosRepository.findById(id)), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllCorteDePagosDetalles(){
        return  new ResponseEntity<>(new Message("OK",false,cortepagos_detallesRepository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByIdCorteDePagosDetalles(long id){
        return  new ResponseEntity<>(new Message("OK",false,cortepagos_detallesRepository.findById(id)), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByPromotora(long id){
        return new ResponseEntity<>(new Message("Ok", false, cortePagosRepository.findAllByPromotoraId(id)), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getByIdHistoricoCorteDePagosDetallesById(long id){
        Optional<Cortepagos> cortepagos = cortePagosRepository.findById(id);
        Cortepagos corte = cortepagos.get();
        return  new ResponseEntity<>(new Message("OK",false,cortepagos_detallesRepository.findCortepagos_detallesByidcortepago(corte) ), HttpStatus.OK);
    }
}
