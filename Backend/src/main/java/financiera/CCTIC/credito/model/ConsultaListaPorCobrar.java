package financiera.CCTIC.credito.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ConsultaListaPorCobrar {
    private int contrato_id;
    private String Cliente;
    private BigDecimal pendiente;
    private String telefono_1;
    private String dia_pago;
    private int pago_semanal;
    private String domicilio;
    private String contrato;
    private String folio;
    private BigInteger total;
    private BigDecimal pagado;
    private BigInteger pagos_realizados;
    private int fecha_maxima;
    private int fechaValida;

    public ConsultaListaPorCobrar() {
    }


    public ConsultaListaPorCobrar(int contrato_id, String cliente, BigDecimal pendiente, String telefono_1, String dia_pago, int pago_semanal, String domicilio, String contrato, String folio, BigInteger total, BigDecimal pagado, BigInteger pagos_realizados, int fecha_maxima, int fechaValida) {
        this.contrato_id = contrato_id;
        Cliente = cliente;
        this.pendiente = pendiente;
        this.telefono_1 = telefono_1;
        this.dia_pago = dia_pago;
        this.pago_semanal = pago_semanal;
        this.domicilio = domicilio;
        this.contrato = contrato;
        this.folio = folio;
        this.total = total;
        this.pagado = pagado;
        this.pagos_realizados = pagos_realizados;
        this.fecha_maxima = fecha_maxima;
        this.fechaValida = fechaValida;
    }

    public int getFecha_maxima() {
        return fecha_maxima;
    }

    public void setFecha_maxima(int fecha_maxima) {
        this.fecha_maxima = fecha_maxima;
    }

    public int getisEs_fecha_valida() {
        return fechaValida;
    }

    public void setEs_fecha_valida(int es_fecha_valida) {
        this.fechaValida = es_fecha_valida;
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

    public BigDecimal getPendiente() {
        return pendiente;
    }

    public void setPendiente(BigDecimal pendiente) {
        this.pendiente = pendiente;
    }

    public String getTelefono_1() {
        return telefono_1;
    }

    public void setTelefono_1(String telefono_1) {
        this.telefono_1 = telefono_1;
    }

    public String getDia_pago() {
        return dia_pago;
    }

    public void setDia_pago(String dia_pago) {
        this.dia_pago = dia_pago;
    }

    public int getPago_semanal() {
        return pago_semanal;
    }

    public void setPago_semanal(int pago_semanal) {
        this.pago_semanal = pago_semanal;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public BigDecimal getPagado() {
        return pagado;
    }

    public void setPagado(BigDecimal pagado) {
        this.pagado = pagado;
    }

    public BigInteger getPagos_realizados() {
        return pagos_realizados;
    }

    public void setPagos_realizados(BigInteger pagos_realizados) {
        this.pagos_realizados = pagos_realizados;
    }
}
