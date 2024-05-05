package financiera.CCTIC.client.model;

public class ClienteDTO {

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
    private String cn_nombre;
    private String cn_telefono_1;
    private String cn_telefono_2;
    private String parentezco;

    public ClienteDTO() {
    }

    public ClienteDTO(String nombre, String numero_cliente, String paterno, String materno, int edad, int ingreso_semanal, String telefono_1, String telefono_2, String correo, String domicilio, String domicilio_detalle, String cn_nombre, String cn_telefono_1, String cn_telefono_2, String parentezco) {
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
        this.cn_nombre = cn_nombre;
        this.cn_telefono_1 = cn_telefono_1;
        this.cn_telefono_2 = cn_telefono_2;
        this.parentezco = parentezco;
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

    public String getCn_nombre() {
        return cn_nombre;
    }

    public void setCn_nombre(String cn_nombre) {
        this.cn_nombre = cn_nombre;
    }

    public String getCn_telefono_1() {
        return cn_telefono_1;
    }

    public void setCn_telefono_1(String cn_telefono_1) {
        this.cn_telefono_1 = cn_telefono_1;
    }

    public String getCn_telefono_2() {
        return cn_telefono_2;
    }

    public void setCn_telefono_2(String cn_telefono_2) {
        this.cn_telefono_2 = cn_telefono_2;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "nombre='" + nombre + '\'' +
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
                ", cn_nombre='" + cn_nombre + '\'' +
                ", cn_telefono_1='" + cn_telefono_1 + '\'' +
                ", cn_telefono_2='" + cn_telefono_2 + '\'' +
                ", parentezco='" + parentezco + '\'' +
                '}';
    }
}
