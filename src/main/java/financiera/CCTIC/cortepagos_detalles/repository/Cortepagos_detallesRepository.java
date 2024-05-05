package financiera.CCTIC.cortepagos_detalles.repository;

import financiera.CCTIC.cortepagos.model.Cortepagos;
import financiera.CCTIC.cortepagos_detalles.model.Cortepagos_detalles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Cortepagos_detallesRepository  extends JpaRepository<Cortepagos_detalles,Long>{

    List<Cortepagos_detalles> findCortepagos_detallesByidcortepago(Cortepagos cortepagos);
}
