package financiera.CCTIC.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import financiera.CCTIC.contacto.model.Contacto;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.user.model.User;

import javax.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "someOtherProperty"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String numero_cliente;
    private String paterno;
    private String materno;
    private int edad;
    private int ingreso_semanal;
    private String telefono_1;
    private String telefono_2;
    private String correo;
    private String domicilio;
    private String domicilio_detalle;

    @OneToMany(mappedBy = "cliente2")
    @JsonIgnore
    private List<Contacto> contacto;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Credito> Credito;
    public Cliente() {
    }

    public Cliente(long id, String nombre, String numero_cliente, String paterno, String materno, int edad, int ingreso_semanal, String telefono_1, String telefono_2, String correo, String domicilio, String domicilio_detalle) {
        this.id = id;
        this.nombre = nombre;
        this.numero_cliente = numero_cliente;
        this.paterno = paterno;
        this.materno = materno;
        this.edad = edad;
        this.ingreso_semanal = ingreso_semanal;
        this.telefono_1 = telefono_1;
        this.telefono_2 = telefono_2;
        this.correo = correo;
        this.domicilio = domicilio;
        this.domicilio_detalle = domicilio_detalle;
    }

    public Cliente(long id, String nombre, String numero_cliente, String paterno, String materno, int edad, int ingreso_semanal, String telefono_1, String telefono_2, String correo, String domicilio, String domicilio_detalle, List<Contacto> contacto) {
        this.id = id;
        this.nombre = nombre;
        this.numero_cliente = numero_cliente;
        this.paterno = paterno;
        this.materno = materno;
        this.edad = edad;
        this.ingreso_semanal = ingreso_semanal;
        this.telefono_1 = telefono_1;
        this.telefono_2 = telefono_2;
        this.correo = correo;
        this.domicilio = domicilio;
        this.domicilio_detalle = domicilio_detalle;
        this.contacto = contacto;
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

    public String getNumero_cliente() {
        return numero_cliente;
    }

    public void setNumero_cliente(String numero_cliente) {
        this.numero_cliente = numero_cliente;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIngreso_semanal() {
        return ingreso_semanal;
    }

    public void setIngreso_semanal(int ingreso_semanal) {
        this.ingreso_semanal = ingreso_semanal;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getDomicilio_detalle() {
        return domicilio_detalle;
    }

    public void setDomicilio_detalle(String domicilio_detalle) {
        this.domicilio_detalle = domicilio_detalle;
    }

    public List<Contacto> getContacto() {
        return contacto;
    }

    public void setContacto(List<Contacto> contacto) {
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numero_cliente='" + numero_cliente + '\'' +
                ", paterno='" + paterno + '\'' +
                ", materno='" + materno + '\'' +
                ", edad=" + edad +
                ", ingreso_semanal=" + ingreso_semanal +
                ", telefono_1='" + telefono_1 + '\'' +
                ", telefono_2='" + telefono_2 + '\'' +
                ", correo='" + correo + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", domicilio_detalle='" + domicilio_detalle + '\'' +
                ", contacto=" + contacto +
                ", Credito=" + Credito +
                '}';
    }
}
