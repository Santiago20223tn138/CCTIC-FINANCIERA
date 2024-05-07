package financiera.CCTIC.credito.repository;

import financiera.CCTIC.credito.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CreditoRepository  extends JpaRepository<Credito,Long> {

    @Query(value = "SELECT * FROM credito where id = :identificador ;",nativeQuery = true)
    Credito getByIdCliente( @Param("identificador") int identificador );


    @Query(value = "insert into historico_pagos(fecha,monto,folio,credito) values( :fecha , :monto , :folio , :credito);",nativeQuery = true)
    void postHistorialPago(
            @Param("fecha") String fecha,
            @Param("monto") int monto,
            @Param("folio") String folio,
            @Param("credito") int credito
    );

    @Query(value = "CALL consultaListaPorCobrar(:idPromotora);",nativeQuery = true)
    List<Object[]> consultaListaPorCobrar(@Param("idPromotora") int idPromotora);



    @Query(value = "CALL CorteDePago(:idPromotora);",nativeQuery = true)
    List<Object[]> CorteDePago(@Param("idPromotora") int idPromotora);
}
