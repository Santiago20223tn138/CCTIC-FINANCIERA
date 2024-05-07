package financiera.CCTIC.person.repository;

import financiera.CCTIC.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    boolean existsById(long id);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByPhone(String phone);
}
