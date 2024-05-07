package financiera.CCTIC.cortepagos_detalles.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import financiera.CCTIC.cortepagos.model.Cortepagos;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.historico_pagos.model.Historico_pagos;

import javax.persistence.*;

@Entity
public class Cortepagos_detalles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcortepago")
    @JsonBackReference
    private Cortepagos idcortepago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hisotrico")
    private Historico_pagos id_hisotrico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcredito")
    private Credito idcredito;

    private int pagado;
    public Cortepagos_detalles() {
    }

    public Cortepagos_detalles(long id, Cortepagos idcorte_pago, Historico_pagos id_hisotrico) {
        this.id = id;
        this.idcortepago = idcorte_pago;
        this.id_hisotrico = id_hisotrico;
    }

    public Cortepagos_detalles(long id, Cortepagos idcortepago, Historico_pagos id_hisotrico, Credito idcredito, int pagado) {
        this.id = id;
        this.idcortepago = idcortepago;
        this.id_hisotrico = id_hisotrico;
        this.idcredito = idcredito;
        this.pagado = pagado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cortepagos getIdcortepago() {
        return idcortepago;
    }

    public void setIdcortepago(Cortepagos idcorte_pago) {
        this.idcortepago = idcorte_pago;
    }

    public Historico_pagos getId_hisotrico() {
        return id_hisotrico;
    }

    public void setId_hisotrico(Historico_pagos id_hisotrico) {
        this.id_hisotrico = id_hisotrico;
    }

    public Credito getIdcredito() {
        return idcredito;
    }

    public void setIdcredito(Credito idcredito) {
        this.idcredito = idcredito;
    }

    public int getPagado() {
        return pagado;
    }

    public void setPagado(int pagado) {
        this.pagado = pagado;
    }
}
