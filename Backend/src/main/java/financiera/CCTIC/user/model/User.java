package financiera.CCTIC.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import financiera.CCTIC.cortepagos.model.Cortepagos;
import financiera.CCTIC.credito.model.CorteDePago;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.person.model.Person;
import financiera.CCTIC.rol.model.Rol;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column()
    private int status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Rol> authorities;

    @OneToMany(mappedBy = "promotora")
    private List<Credito> creditos;

    @OneToMany(mappedBy = "promotora")
    private List<Cortepagos> cortePagos;

    public User() {
    }

    public User(long id, String password, String username, Person person, int status, Set<Rol> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.person = person;
        this.status = status;
        this.authorities = authorities;
    }

    public User(String password, String username, Person person, int status, Set<Rol> authorities) {
        this.password = password;
        this.username = username;
        this.person = person;
        this.status = status;
        this.authorities = authorities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Rol> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Rol> authorities) {
        this.authorities = authorities;
    }
}
