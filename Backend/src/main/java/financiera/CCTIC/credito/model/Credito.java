package financiera.CCTIC.credito.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import financiera.CCTIC.client.model.Cliente;
import financiera.CCTIC.contacto.model.Contacto;
import financiera.CCTIC.cortepagos_detalles.model.Cortepagos_detalles;
import financiera.CCTIC.historico_pagos.model.Historico_pagos;
import financiera.CCTIC.user.model.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contrato;
    private String dia_pago;
    private String folio;
    private int monto;
    private int pago_inicial;
    private int pago_semanal;
    private int semanas;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente")
    private Cliente cliente;
    private String fecha;
    @OneToMany(mappedBy = "credito")
    @JsonIgnore
    private List<Historico_pagos> historicos_pagos;

    @OneToMany(mappedBy = "idcredito")
    @JsonIgnore
    private List<Cortepagos_detalles>  idcredito;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotora")
    private User promotora;

    public Credito() {
    }

    public Credito(long id, String contrato, String dia_pago, String folio, int monto, int pago_inicial, int pago_semanal, Cliente cliente, String fecha, List<Historico_pagos> historicos_pagos) {
        this.id = id;
        this.contrato = contrato;
        this.dia_pago = dia_pago;
        this.folio = folio;
        this.monto = monto;
        this.pago_inicial = pago_inicial;
        this.pago_semanal = pago_semanal;
        this.cliente = cliente;
        this.fecha = fecha;
        this.historicos_pagos = historicos_pagos;
    }

    public Credito(long id, String contrato, String dia_pago, String folio, int monto, int pago_inicial, int pago_semanal, int semanas, Cliente cliente, String fecha, User promotora) {
        this.id = id;
        this.contrato = contrato;
        this.dia_pago = dia_pago;
        this.folio = folio;
        this.monto = monto;
        this.pago_inicial = pago_inicial;
        this.pago_semanal = pago_semanal;
        this.semanas = semanas;
        this.cliente = cliente;
        this.fecha = fecha;
        this.promotora = promotora;
    }

    public Credito(long id, String contrato, String dia_pago, String folio, int monto, int pago_inicial, int pago_semanal, int semanas, Cliente cliente, String fecha) {
        this.id = id;
        this.contrato = contrato;
        this.dia_pago = dia_pago;
        this.folio = folio;
        this.monto = monto;
        this.pago_inicial = pago_inicial;
        this.pago_semanal = pago_semanal;
        this.semanas = semanas;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getDia_pago() {
        return dia_pago;
    }

    public void setDia_pago(String dia_pago) {
        this.dia_pago = dia_pago;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getPago_inicial() {
        return pago_inicial;
    }

    public void setPago_inicial(int pago_inicial) {
        this.pago_inicial = pago_inicial;
    }

    public int getPago_semanal() {
        return pago_semanal;
    }

    public void setPago_semanal(int pago_semanal) {
        this.pago_semanal = pago_semanal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Historico_pagos> getHistoricos_pagos() {
        return historicos_pagos;
    }

    public void setHistoricos_pagos(List<Historico_pagos> historicos_pagos) {
        this.historicos_pagos = historicos_pagos;
    }

    public List<Cortepagos_detalles> getIdcredito() {
        return idcredito;
    }

    public void setIdcredito(List<Cortepagos_detalles> idcredito) {
        this.idcredito = idcredito;
    }

    public User getPromotora() {
        return promotora;
    }

    public void setPromotora(User promotora) {
        this.promotora = promotora;
    }
}
