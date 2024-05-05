package financiera.CCTIC.credito.model;

import java.math.BigInteger;
import java.util.Date;

public class CorteDePago {
    private int contrato_id;
    private int historico;
    private String Cliente;
    private String telefono_1;
    private int pago_semanal;
    private String contrato;
    private String folio;
    private Date fecha_maxima;
    private BigInteger monto;
    private int es_fecha_valida;

    public CorteDePago() {
    }

    public CorteDePago(int contrato_id, String cliente, String telefono_1, int pago_semanal, String contrato, String folio, Date fecha_maxima, BigInteger monto, int es_fecha_valida) {
        this.contrato_id = contrato_id;
        Cliente = cliente;
        this.telefono_1 = telefono_1;
        this.pago_semanal = pago_semanal;
        this.contrato = contrato;
        this.folio = folio;
        this.fecha_maxima = fecha_maxima;
        this.monto = monto;
        this.es_fecha_valida = es_fecha_valida;
    }

    public int getHistorico() {
        return historico;
    }

    public void setHistorico(int historico) {
        this.historico = historico;
    }

    public int getContrato_id() {
        return contrato_id;
    }

    public void setContrato_id(int contrato_id) {
        this.contrato_id = contrato_id;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getTelefono_1() {
        return telefono_1;
    }

    public void setTelefono_1(String telefono_1) {
        this.telefono_1 = telefono_1;
    }

    public int getPago_semanal() {
        return pago_semanal;
    }

    public void setPago_semanal(int pago_semanal) {
        this.pago_semanal = pago_semanal;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFecha_maxima() {
        return fecha_maxima;
    }

    public void setFecha_maxima(Date fecha_maxima) {
        this.fecha_maxima = fecha_maxima;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public int getEs_fecha_valida() {
        return es_fecha_valida;
    }

    public void setEs_fecha_valida(int es_fecha_valida) {
        this.es_fecha_valida = es_fecha_valida;
    }
}
