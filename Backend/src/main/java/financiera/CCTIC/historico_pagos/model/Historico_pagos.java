package financiera.CCTIC.historico_pagos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import financiera.CCTIC.client.model.Cliente;
import financiera.CCTIC.cortepagos_detalles.model.Cortepagos_detalles;
import financiera.CCTIC.credito.model.Credito;

import javax.persistence.*;
import java.util.List;

@Entity
public class Historico_pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fecha;
    private int monto;
    private String folio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credito")
    private Credito credito;
    @OneToMany(mappedBy = "id_hisotrico")
    @JsonIgnore
    private List<Cortepagos_detalles>  id_hisotrico;

    public Historico_pagos() {
    }

    public Historico_pagos(long id, String fecha, int monto, String folio, Credito credito) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.folio = folio;
        this.credito = credito;
    }

    public Historico_pagos(long id, String fecha, int monto, String folio, Credito credito, List<Cortepagos_detalles> id_hisotrico) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.folio = folio;
        this.credito = credito;
        this.id_hisotrico = id_hisotrico;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public List<Cortepagos_detalles> getId_hisotrico() {
        return id_hisotrico;
    }

    public void setId_hisotrico(List<Cortepagos_detalles> id_hisotrico) {
        this.id_hisotrico = id_hisotrico;
    }
}
