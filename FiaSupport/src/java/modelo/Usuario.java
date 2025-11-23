package modelo;

public class Usuario {
    private String carnet;     // id_usuario en BD
    private String nombres;    // nombres en BD
    private String apellidos;  // apellidos en BD
    private String rol;        // "ADMIN" o "USUARIO"

    public Usuario() {}

    public Usuario(String carnet, String nombres, String apellidos, String rol) {
        this.carnet = carnet;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rol = rol;
    }

    public String getCarnet() { return carnet; }
    public void setCarnet(String carnet) { this.carnet = carnet; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // ✅ helper para JSP
    public String getNombreCompleto() {
        String n = (nombres == null) ? "" : nombres;
        String a = (apellidos == null) ? "" : apellidos;
        return (n + " " + a).trim();
    }
}
