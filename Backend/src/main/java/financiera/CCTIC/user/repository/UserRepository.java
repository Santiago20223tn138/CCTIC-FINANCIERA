package financiera.CCTIC.user.repository;

import financiera.CCTIC.rol.model.Rol;
import financiera.CCTIC.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername (String username);
    Optional<User> findById(long id);
    Optional<User> findUserByPersonId(long id);
    List<User> findUsersByAuthoritiesAndStatus(Rol rol, int status);
    boolean existsByPersonEmail(String email);
    boolean existsByUsername(String username);
    boolean existsById(long id);
}
