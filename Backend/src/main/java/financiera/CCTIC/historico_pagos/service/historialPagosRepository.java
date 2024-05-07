package financiera.CCTIC.historico_pagos.service;

import financiera.CCTIC.historico_pagos.model.Historico_pagos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface historialPagosRepository extends JpaRepository<Historico_pagos,Long> {
   List<Historico_pagos> findAllByCreditoId(long id);
}
