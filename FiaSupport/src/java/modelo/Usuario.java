package modelo;

public class Usuario {
    private String carnet;
    private String nombre;
    private String rol; // "ADMIN", "TECNICO", "USUARIO"

    public Usuario() {}

    public Usuario(String carnet, String nombre, String rol) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getCarnet() { return carnet; }
    public void setCarnet(String carnet) { this.carnet = carnet; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
