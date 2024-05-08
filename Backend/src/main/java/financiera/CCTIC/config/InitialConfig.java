package financiera.CCTIC.config;

import financiera.CCTIC.person.model.Person;
import financiera.CCTIC.person.repository.PersonRepository;
import financiera.CCTIC.rol.model.Rol;
import financiera.CCTIC.rol.repository.RolRepository;
import financiera.CCTIC.user.model.User;
import financiera.CCTIC.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Configuration

public class InitialConfig implements CommandLineRunner {
    private final RolRepository rolRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public InitialConfig(RolRepository rolRepository, PersonRepository personRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void run(String... args) throws Exception{
        Rol adminRol = getOrSaveRol(new Rol("ADM", "Administrador"));
        getOrSaveRol(new Rol("PRM", "Promotora"));

        Person person = getOrSavePerson(new Person(1, "Lizbeth", "Santibañez", "Temixco", "7776206888", "admin@admin.com",19 ));
        User user = getOrSaveUser(new User( passwordEncoder.encode("finamor"), "admin@admin.com", person, 1, Set.of(adminRol)));
    }

    @Transactional
    public Rol getOrSaveRol(Rol rol){
        Optional<Rol> foundRole = rolRepository.findByDescription(rol.getDescription());
        return foundRole.orElseGet(() -> {
            rolRepository.saveAndFlush(rol);
            return rolRepository.findByDescription(rol.getDescription()).orElse(null);
        });
    }

    @Transactional
    public Person getOrSavePerson(Person person){
        Optional<Person> foundPerson = personRepository.findByEmail(person.getEmail());
        return foundPerson.orElseGet(() -> personRepository.saveAndFlush(person));
    }

    @Transactional
    public User getOrSaveUser(User user){
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
       Person userPerson = user.getPerson();
       if (userPerson != null){
           userPerson = getOrSavePerson(userPerson);
       }
       user.setPerson(userPerson);
       return userRepository.saveAndFlush(user);
    }

}
