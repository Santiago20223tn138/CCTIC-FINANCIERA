package financiera.CCTIC.rol.repository;

import financiera.CCTIC.rol.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByDescription(String description);
    boolean existsById(long id);
}
