package financiera.CCTIC.client.repository;

import financiera.CCTIC.client.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository  extends JpaRepository<Cliente,Long> {

    @Query(value = "{CALL registraCliente (:nombre,:numero_cliente,:paterno,:materno,:edad,:ingreso_semanal,:telefono_1,:telefono_2,:correo,:domicilio,:domicilio_detalle,:cn_nombre,:cn_telefono_1,:cn_telefono_2,:parentezco)}",nativeQuery = true)
    void postClient(
            @Param("nombre") String nombre,
            @Param("numero_cliente") String numero_cliente,
            @Param("paterno") String paterno,
            @Param("materno") String materno,
            @Param("edad") int edad,
            @Param("ingreso_semanal") int ingreso_semanal,
            @Param("telefono_1") String telefono_1,
            @Param("telefono_2") String telefono_2,
            @Param("correo") String correo,
            @Param("domicilio") String domicilio,
            @Param("domicilio_detalle") String domicilio_detalle,
            @Param("cn_nombre") String cn_nombre,
            @Param("cn_telefono_1") String cn_telefono_1,
            @Param("cn_telefono_2") String cn_telefono_2,
            @Param("parentezco") String parentezco
    );

    @Query(value = "{CALL registraCredito (:contrato , :diaPago , :folio , :monto , :inicial , :semanal , :cliente , :fecha , :semanas, :promotora)}",nativeQuery = true)
    void postCredito(
            @Param("contrato") String contrato,
            @Param("diaPago") String diaPago,
            @Param("folio") String folio,
            @Param("monto") int monto,
            @Param("inicial") int inicial,
            @Param("semanal") int semanal,
            @Param("cliente") int cliente,
            @Param("fecha") String fecha,
            @Param("semanas") int semanas,
            @Param("promotora") int promotora
            );

    @Query(value = "SELECT numero_cliente FROM cliente order by numero_cliente desc limit 1;",nativeQuery = true)
    String getClienNumber();

    @Query(value = "SELECT contrato FROM credito order by contrato desc limit 1;",nativeQuery = true)
    String getContratoCreditoNumber();

    @Query(value = "SELECT folio FROM credito order by folio desc limit 1;",nativeQuery = true)
    String getContratoFolioNumber();
    @Query(value = "SELECT Count(numero_cliente) FROM  cliente WHERE numero_cliente LIKE %:clasificacion% ;",nativeQuery = true)
    int getCountClientNumber(
            @Param("clasificacion") String clasificacion
    );

    @Query(value = "SELECT * FROM credito WHERE cliente = :id ;", nativeQuery = true)
    List<Object[]> getCreditoByIdCliente(@Param("id") int id);

    @Query(value = "UPDATE `credito` SET `dia_pago` = :Dia WHERE (`id` = :id );",nativeQuery = true)
    String putCredito(
            @Param("Dia") String Dia,
            @Param("id") int id
    );
}
