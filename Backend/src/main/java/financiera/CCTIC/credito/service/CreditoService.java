package financiera.CCTIC.credito.service;

import financiera.CCTIC.client.model.ClienteDTO;
import financiera.CCTIC.credito.model.ConsultaListaPorCobrar;
import financiera.CCTIC.credito.model.CorteDePago;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.credito.model.HistorialPagosDTO;
import financiera.CCTIC.credito.repository.CreditoRepository;
import financiera.CCTIC.historico_pagos.model.Historico_pagos;
import financiera.CCTIC.historico_pagos.service.historialPagosRepository;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CreditoService {

    @Autowired
    CreditoRepository creditoRepository;

    @Autowired
    historialPagosRepository historialPagosRepository;

    @Transactional(readOnly = false)
    public ResponseEntity<Message> postCLient(HistorialPagosDTO historialPagosDTO){
        try {
            Historico_pagos historialPagos = new Historico_pagos();
            historialPagos.setFecha(historialPagosDTO.getFecha());
            historialPagos.setMonto(historialPagosDTO.getMonto());
            String folio = GenerarIdentificador();
            historialPagos.setFolio(folio);
            historialPagos.setCredito(creditoRepository.getByIdCliente(historialPagosDTO.getCredito()));

            Object response = historialPagosRepository.saveAndFlush(historialPagos);

            return new ResponseEntity<>(new Message("OK", false, response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Error", true, "Ocurrió un error al procesar la solicitud " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public ResponseEntity<Message> consultaListaPorCobrar(int idPromotora){
        List<Object[]> multo = creditoRepository.consultaListaPorCobrar(idPromotora);
        List<ConsultaListaPorCobrar> listaDeConsultaListaPorCobrar = new ArrayList<>();

        for (Object[] arrayDatos : multo) {
            ConsultaListaPorCobrar consultaListaPorCobrar = new ConsultaListaPorCobrar();
            consultaListaPorCobrar.setContrato_id((int) arrayDatos[0]);
            consultaListaPorCobrar.setCliente((String) arrayDatos[1]);
            consultaListaPorCobrar.setPendiente((BigDecimal) arrayDatos[2]);
            consultaListaPorCobrar.setTelefono_1((String) arrayDatos[3]);
            consultaListaPorCobrar.setDia_pago((String) arrayDatos[4]);
            consultaListaPorCobrar.setPago_semanal((int) arrayDatos[5]);
            consultaListaPorCobrar.setDomicilio((String) arrayDatos[6]);
            consultaListaPorCobrar.setContrato((String) arrayDatos[7]);
            consultaListaPorCobrar.setFolio((String) arrayDatos[8]);
            consultaListaPorCobrar.setTotal((BigInteger) arrayDatos[9]);
            consultaListaPorCobrar.setPagado((BigDecimal) arrayDatos[10]);
            consultaListaPorCobrar.setPagos_realizados((BigInteger) arrayDatos[11]);
            consultaListaPorCobrar.setFecha_maxima((int) arrayDatos[12]);
            consultaListaPorCobrar.setEs_fecha_valida((int) arrayDatos[13]);
            listaDeConsultaListaPorCobrar.add(consultaListaPorCobrar);
        }
        return  new ResponseEntity<>(new Message("OK",false,listaDeConsultaListaPorCobrar), HttpStatus.OK);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<Message> CorteDePago(int promotora ){
        List<Object[]> multo = creditoRepository.CorteDePago(promotora);
        List<CorteDePago> listaDeCobro= new ArrayList<>();

        for (Object[] arrayDatos : multo) {
            CorteDePago cortedepago = new CorteDePago();
            cortedepago.setContrato_id((int) arrayDatos[0]);
            cortedepago.setHistorico((int) arrayDatos[1]);
            cortedepago.setCliente((String) arrayDatos[2]);
            cortedepago.setTelefono_1((String) arrayDatos[3]);
            cortedepago.setPago_semanal((int) arrayDatos[4]);
            cortedepago.setContrato((String) arrayDatos[5]);
            cortedepago.setFolio((String) arrayDatos[6]);
            cortedepago.setFecha_maxima((Date) arrayDatos[7]);
            cortedepago.setMonto((BigInteger) arrayDatos[8]);
            cortedepago.setEs_fecha_valida((int) arrayDatos[9]);

            listaDeCobro.add(cortedepago);
        }
        return  new ResponseEntity<>(new Message("OK",false,listaDeCobro), HttpStatus.OK);
    }

    public static String GenerarIdentificador() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formatoFechaHora = ahora.format(formatter);
        String identificador = "NA" + formatoFechaHora;
        System.out.println(identificador);
        return identificador;
    }

}
