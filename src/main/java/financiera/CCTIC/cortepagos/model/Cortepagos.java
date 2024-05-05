package financiera.CCTIC.cortepagos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import financiera.CCTIC.cortepagos_detalles.model.Cortepagos_detalles;
import financiera.CCTIC.user.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Cortepagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int idultimo;
    private Date fecha;
    private int monto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotora")
    private User promotora;

    @OneToMany(mappedBy = "idcortepago")
    @JsonIgnore
    @JsonManagedReference
    private List<Cortepagos_detalles> idcortepago;



    public Cortepagos() {
    }

    public Cortepagos(long id, int idultimo, Date fecha, int monto) {
        this.id = id;
        this.idultimo = idultimo;
        this.fecha = fecha;
        this.monto = monto;
    }

    public Cortepagos(long id, int idultimo, Date fecha, int monto, List<Cortepagos_detalles> idcorte_pago) {
        this.id = id;
        this.idultimo = idultimo;
        this.fecha = fecha;
        this.monto = monto;
        this.idcortepago = idcorte_pago;
    }

    public Cortepagos(long id, int idultimo, Date fecha, int monto, User promotora, List<Cortepagos_detalles> idcortepago) {
        this.id = id;
        this.idultimo = idultimo;
        this.fecha = fecha;
        this.monto = monto;
        this.promotora = promotora;
        this.idcortepago = idcortepago;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdultimo() {
        return idultimo;
    }

    public void setIdultimo(int idultimo) {
        this.idultimo = idultimo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public List<Cortepagos_detalles> getIdcortepago() {
        return idcortepago;
    }

    public void setIdcortepago(List<Cortepagos_detalles> idcortepago) {
        this.idcortepago = idcortepago;
    }

    public User getPromotora() {
        return promotora;
    }

    public void setPromotora(User promotora) {
        this.promotora = promotora;
    }
}
