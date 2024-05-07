package financiera.CCTIC.user.controller;

import financiera.CCTIC.person.repository.PersonRepository;
import financiera.CCTIC.rol.model.Rol;
import financiera.CCTIC.rol.model.RolDTO;
import financiera.CCTIC.rol.repository.RolRepository;
import financiera.CCTIC.user.DTO.UserDTO;
import financiera.CCTIC.user.model.User;
import financiera.CCTIC.user.service.UserService;
import financiera.CCTIC.utils.Message;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financiera/user")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolRepository rolRepository;

    @GetMapping("/")
    public ResponseEntity<Message> getAll(){
        return  userService.findAll();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Message> getById(@PathVariable("id") long id){
        return  userService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Message> createUser(@RequestBody UserDTO createUserDTO) {
        Hibernate.initialize(createUserDTO.getPerson());
        try {
            // Crear un nuevo objeto User usando los datos del DTO y guardar en la base de datos
            User newUser = new User(
                    passwordEncoder.encode(createUserDTO.getPassword()),
                    createUserDTO.getUsername(),
                    createUserDTO.getPerson(),
                    createUserDTO.getStatus(),
                    createUserDTO.getAuthorities()
            );

            // Puedes devolver directamente el nuevo usuario como parte de la respuesta
            return new ResponseEntity<>(new Message("Usuario creado con éxito", false,  userService.save(newUser)), HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la creación del usuario
            return new ResponseEntity<>(new Message("Error al crear el usuario", true, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Repatidor/")
    public ResponseEntity<Message> getAllRepatidor(@RequestBody RolDTO rolDTO){
        return  userService.findAllRepatidor(new Rol(rolDTO.getId(), rolDTO.getDescription(), rolDTO.getAcronym()));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Message> setStatus(@PathVariable("id") long id){
        return userService.setStatus(id);
    }

    @PutMapping("/password/")
    public ResponseEntity<Message> setpassword(@RequestBody UserDTO UserDTO){
        System.out.println(UserDTO.toString());
        String password = passwordEncoder.encode(UserDTO.getPassword());
        UserDTO.setPassword(password);
        return userService.updatePassword(UserDTO);
    }
}
