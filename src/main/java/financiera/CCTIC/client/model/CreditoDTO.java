package financiera.CCTIC.client.model;

public class CreditoDTO {
    private  long id;
    private String contrato;
    private String diaPago;
    private String folio;
    private int monto;
    private int inicial;
    private int semanal;
    private int cliente;
    private String fecha;
    private int semanas;

    private int promotora;

    public CreditoDTO() {
    }

    public CreditoDTO(String contrato, String diaPago, String folio, int monto, int inicial, int semanal, int cliente, String fecha) {
        this.contrato = contrato;
        this.diaPago = diaPago;
        this.folio = folio;
        this.monto = monto;
        this.inicial = inicial;
        this.semanal = semanal;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public CreditoDTO(long id, String contrato, String diaPago, String folio, int monto, int inicial, int semanal, int cliente, String fecha) {
        this.id = id;
        this.contrato = contrato;
        this.diaPago = diaPago;
        this.folio = folio;
        this.monto = monto;
        this.inicial = inicial;
        this.semanal = semanal;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public CreditoDTO(long id, String contrato, String diaPago, String folio, int monto, int inicial, int semanal, int cliente, String fecha, int semanas) {
        this.id = id;
        this.contrato = contrato;
        this.diaPago = diaPago;
        this.folio = folio;
        this.monto = monto;
        this.inicial = inicial;
        this.semanal = semanal;
        this.cliente = cliente;
        this.fecha = fecha;
        this.semanas = semanas;
    }

    public CreditoDTO(long id, String contrato, String diaPago, String folio, int monto, int inicial, int semanal, int cliente, String fecha, int semanas, int promotora) {
        this.id = id;
        this.contrato = contrato;
        this.diaPago = diaPago;
        this.folio = folio;
        this.monto = monto;
        this.inicial = inicial;
        this.semanal = semanal;
        this.cliente = cliente;
        this.fecha = fecha;
        this.semanas = semanas;
        this.promotora = promotora;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(String diaPago) {
        this.diaPago = diaPago;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getInicial() {
        return inicial;
    }

    public void setInicial(int inicial) {
        this.inicial = inicial;
    }

    public int getSemanal() {
        return semanal;
    }

    public void setSemanal(int semanal) {
        this.semanal = semanal;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPromotora() {
        return promotora;
    }

    public void setPromotora(int promotora) {
        this.promotora = promotora;
    }
}
