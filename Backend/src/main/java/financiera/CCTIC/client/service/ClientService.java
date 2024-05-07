package financiera.CCTIC.client.service;

import financiera.CCTIC.client.model.ClienteDTO;
import financiera.CCTIC.client.model.CreditoDTO;
import financiera.CCTIC.client.repository.ClientRepository;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.credito.repository.CreditoRepository;
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
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CreditoRepository creditoRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(){
        return  new ResponseEntity<>(new Message("OK",false,clientRepository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getCreditoById(int id){
        return  new ResponseEntity<>(new Message("OK",false,creditoRepository.getByIdCliente(id)), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getClienteById(int id){
        return  new ResponseEntity<>(new Message("OK",false,clientRepository.getById((long) id)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getCreditoByIdByCliente(int id){
        List<Object[]> multo = clientRepository.getCreditoByIdCliente(id);
        List<Credito> listaDeCreditos = new ArrayList<>();

        for (Object[] arrayDatos : multo) {
            Credito credito = new Credito();
            credito.setId((int) arrayDatos[0]);
            credito.setContrato((String) arrayDatos[1]);
            credito.setFolio((String) arrayDatos[3]);
            credito.setDia_pago((String) arrayDatos[2]);
            credito.setMonto((int) arrayDatos[4]);
            credito.setPago_inicial((Integer) arrayDatos[5]);
            credito.setPago_semanal((Integer) arrayDatos[6]);
            long isd = ((Integer) arrayDatos[7]).longValue();
            credito.setCliente(clientRepository.getById(isd));
            java.sql.Date fechaSQL = (java.sql.Date) arrayDatos[8];
            SimpleDateFormat sdf = new SimpleDateFormat("yyddMM"); // ajusta el formato según tus necesidades
            String fechaString = sdf.format(fechaSQL);
            credito.setFecha(fechaString);
            if(arrayDatos[9] == null){
                credito.setSemanas(0);
            }else{
                credito.setSemanas((int) arrayDatos[9]);
            }
            listaDeCreditos.add(credito);
        }

        return  new ResponseEntity<>(new Message("OK",false,listaDeCreditos), HttpStatus.OK);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<Message> postCLient(ClienteDTO clienteDTO){
        try {
            System.out.println( clientRepository.getClienNumber());
            clientRepository.postClient(clienteDTO.getNombre(), procesarNumeroCliente(clienteDTO.getNombre(),clienteDTO.getMaterno()), clienteDTO.getPaterno(), clienteDTO.getMaterno(), clienteDTO.getEdad(), clienteDTO.getIngreso_semanal(), clienteDTO.getTelefono_1(), clienteDTO.getTelefono_2(), clienteDTO.getCorreo(), clienteDTO.getDomicilio(), clienteDTO.getDomicilio_detalle(), clienteDTO.getCn_nombre(), clienteDTO.getCn_telefono_1(), clienteDTO.getCn_telefono_2(), clienteDTO.getParentezco());
            return new ResponseEntity<>(new Message("OK", false, "Se ha completado correctamente"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Puedes manejar la excepción de una manera más adecuada según tus necesidades
            return new ResponseEntity<>(new Message("Error", true, "Ocurrió un error al procesar la solicitud "+e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public ResponseEntity<Message> putCredito(CreditoDTO creditoDTO){
        try {
            Credito credito = creditoRepository.getByIdCliente((int) creditoDTO.getId());
            credito.setDia_pago(creditoDTO.getDiaPago());
            creditoRepository.save(credito);
            return new ResponseEntity<>(new Message("OK", false, "Se ha completado correctamente"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Puedes manejar la excepción de una manera más adecuada según tus necesidades
            return new ResponseEntity<>(new Message("Error", true, "Ocurrió un error al procesar la solicitud "+e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public ResponseEntity<Message> postCredito(CreditoDTO creditoDTO){
        try {
            var semanas = 33;
            var intereses = 1;

            var total = Math.floor((creditoDTO.getMonto() * intereses));

            var semanal = Math.floor((creditoDTO.getMonto()  * intereses) / semanas);

            var totalSemanal =  Math.floor(semanal * semanas);

            var sobranteSemanal =    Math.floor((total - totalSemanal));

            var inicial = Math.floor((sobranteSemanal + semanal));

            clientRepository.postCredito(procesarContrato(clientRepository.getContratoCreditoNumber()),creditoDTO.getDiaPago(),procesarFolio(clientRepository.getContratoFolioNumber()), (int) total, (int) inicial,(int) semanal,creditoDTO.getCliente(),creditoDTO.getFecha(),semanas, creditoDTO.getPromotora());
            return new ResponseEntity<>(new Message("OK", false, "Se ha completado correctamente"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Puedes manejar la excepción de una manera más adecuada según tus necesidades
            return new ResponseEntity<>(new Message("Error", true, "Ocurrió un error al procesar la solicitud "+e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public String procesarContrato(String inputString) {
        String ultimosSeisCaracteres = inputString.substring(Math.max(0, inputString.length() - 6));
        try {
            int numeroEntero = Integer.parseInt(ultimosSeisCaracteres);
            int numeroResultante = numeroEntero + 1;
            String numero= "NA2317"+numeroResultante;
            return numero;
        } catch (NumberFormatException e) {
            // En caso de que los últimos 6 caracteres no sean convertibles a entero
            return "Error: No se pudieron convertir los últimos 6 caracteres a un número entero";
        }
    }

    public String procesarFolio(String inputString) {
        String ultimosSeisCaracteres = inputString.substring(Math.max(0, inputString.length() - 6));
        try {
            int numeroEntero = Integer.parseInt(ultimosSeisCaracteres);
            int numeroResultante = numeroEntero + 1;
            String numero= "MOR2317"+numeroResultante;
            return numero;
        } catch (NumberFormatException e) {
            // En caso de que los últimos 6 caracteres no sean convertibles a entero
            return "Error: No se pudieron convertir los últimos 6 caracteres a un número entero";
        }
    }

    public String procesarNumeroCliente(String nombre, String apellidoPaterno) {
        apellidoPaterno.toUpperCase();
        nombre.toUpperCase();
        try {
            String count2 = "";
            String clasificar = ""+nombre.charAt(0)+apellidoPaterno.charAt(apellidoPaterno.length() - 1);
            int count = clientRepository.getCountClientNumber(clasificar);
            if(count<9) {
                count2 = "0" + (count + 1);
            }else{
                count2 = ""+(count+1);
            }

            String numero= "FI17"+obtenerFechaActualEnFormatoAAmmdd()+clasificar+count2;
            System.out.println(numero);
            return numero;
        } catch (NumberFormatException e) {
            return "Error: No se pudieron convertir los últimos 6 caracteres a un número entero";
        }
    }


    public static String obtenerFechaActualEnFormatoAAmmdd() {
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyddMM");
        return sdf.format(fechaActual);
    }


}
