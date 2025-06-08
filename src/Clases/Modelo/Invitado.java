package Clases.Modelo;

public class Invitado {

    private String nombreCompleto;
    private String email;

    public Invitado(String nombreCompleto, String email) {
        this.email = email;
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombre() {
        return nombreCompleto;
    }

    public void setNombre(String nombre) {
        this.nombreCompleto = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
