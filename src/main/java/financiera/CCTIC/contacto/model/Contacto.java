package financiera.CCTIC.contacto.model;

import financiera.CCTIC.client.model.Cliente;

import javax.persistence.*;

@Entity
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String telefono_1;
    private String telefono_2;
    private String parentezco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente")
    private Cliente cliente2;

    public Contacto() {
    }

    public Contacto(long id, String nombre, String telefono_1, String telefono_2, String parentezco, Cliente cliente) {
        this.id = id;
        this.nombre = nombre;
        this.telefono_1 = telefono_1;
        this.telefono_2 = telefono_2;
        this.parentezco = parentezco;
        this.cliente2 = cliente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono_1() {
        return telefono_1;
    }

    public void setTelefono_1(String telefono_1) {
        this.telefono_1 = telefono_1;
    }

    public String getTelefono_2() {
        return telefono_2;
    }

    public void setTelefono_2(String telefono_2) {
        this.telefono_2 = telefono_2;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public Cliente getCliente() {
        return cliente2;
    }

    public void setCliente(Cliente cliente) {
        this.cliente2 = cliente;
    }
}
