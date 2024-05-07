package financiera.CCTIC.person.controller;

import financiera.CCTIC.person.model.PersonDTO;
import financiera.CCTIC.person.service.PersonService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/financiera/person")
@CrossOrigin(origins = {"*"})
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public ResponseEntity<Message> getAll(){
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(@PathVariable Long id){
        return personService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Message> save(@RequestBody PersonDTO personDTO){
        return personService.save(personDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody PersonDTO personDTO){
        return personService.update(id, personDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable Long id){
        return personService.delete(id);
    }
}
