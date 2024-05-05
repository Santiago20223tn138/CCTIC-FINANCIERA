package financiera.CCTIC.credito.model;

public class HistorialPagosDTO {
    private  long id;
    private String fecha;
    private int monto;
    private String folio;
    private int credito;

    public HistorialPagosDTO() {
    }

    public HistorialPagosDTO(long id, String fecha, int monto, String folio, int credito) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.folio = folio;
        this.credito = credito;
    }

    public HistorialPagosDTO(String fecha, int monto, String folio, int credito) {
        this.fecha = fecha;
        this.monto = monto;
        this.folio = folio;
        this.credito = credito;
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

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }
}
