package financiera.CCTIC.cortepagos_detalles.repository;

import financiera.CCTIC.cortepagos.model.Cortepagos;
import financiera.CCTIC.cortepagos_detalles.model.Cortepagos_detalles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CortePagosRepository extends JpaRepository<Cortepagos,Long>{

    List<Cortepagos> findAllByPromotoraId(Long id);
}
