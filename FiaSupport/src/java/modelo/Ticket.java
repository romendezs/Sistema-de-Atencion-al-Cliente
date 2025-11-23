package modelo;

import java.sql.Date;
import java.sql.Timestamp;

public class Ticket {

    private int idTicket;
    private String solicitante;   // carnet del usuario
    private int idEmpleado;
    private int idCategoria;
    private String titulo;
    private String descripcion;
    private Timestamp fechaCreacion;
    private Timestamp fechaCierre;   // NULL = pendiente
    private Date fechaSuceso;        // fecha elegida por usuario
    private String estado;           // calculado

    public Ticket() {}

    // ===== getters / setters =====

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaSuceso() {
        return fechaSuceso;
    }

    public void setFechaSuceso(Date fechaSuceso) {
        this.fechaSuceso = fechaSuceso;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
