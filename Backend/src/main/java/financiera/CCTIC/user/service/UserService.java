package financiera.CCTIC.user.service;

import financiera.CCTIC.person.model.Person;
import financiera.CCTIC.person.repository.PersonRepository;
import financiera.CCTIC.rol.model.Rol;
import financiera.CCTIC.rol.repository.RolRepository;
import financiera.CCTIC.user.DTO.UserDTO;
import financiera.CCTIC.user.model.User;
import financiera.CCTIC.user.repository.UserRepository;
import financiera.CCTIC.utils.Message;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RolRepository rolRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(){
        return  new ResponseEntity<>(new Message("OK",false,userRepository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(long id){
        if( userRepository.existsById(id)){
            return new ResponseEntity<>(new Message("OK", false, userRepository.findById(id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("El usuario ya existe", true, userRepository.findById(id)), HttpStatus.BAD_REQUEST);
    }

    @Transactional(readOnly = true)
    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> setStatus(long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (user.getStatus() == 1) {
                user.setStatus(0);
            } else {
                user.setStatus(1);
            }
            return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(user)), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Message("El usuario no existe", true, null), HttpStatus.BAD_REQUEST);

        }
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> save(User user){

        System.out.println(user.getUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(new Message("El usuario ya existe", true, null), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByPersonEmail(user.getPerson().getEmail())) {
            return new ResponseEntity<>(new Message("La persona ya cuenta con un usuario", true, null), HttpStatus.BAD_REQUEST);
        }
        Person personTemp = personRepository.getById(user.getPerson().getId());
        user.setPerson(personTemp);
        Hibernate.initialize(user.getPerson());
        user.setUsername(personTemp.getEmail());
        User  e =  userRepository.saveAndFlush(user);
        UserDTO use = new UserDTO(user.getId(),user.getUsername(), user.getPassword(),user.getAuthorities(),user.getStatus());
        return new ResponseEntity<>(new Message("OK", false, use), HttpStatus.OK);

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> updatePassword(UserDTO user){
        User user1 = userRepository.getById(user.getId());
        user1.setPassword(user.getPassword());
        User  e =  userRepository.saveAndFlush(user1);
        UserDTO use = new UserDTO(user.getId(),user.getUsername(), user.getPassword(),user.getAuthorities(),user.getStatus());
        return new ResponseEntity<>(new Message("OK", false, use), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public Optional<User> getById(long id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllRepatidor(Rol role){
        List<User> users = userRepository.findUsersByAuthoritiesAndStatus(role, 1);

        return  new ResponseEntity<>(new Message("OK",false,users), HttpStatus.OK);
    }
}
