package Clases.Modelo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private String nombre;
    private LocalDate fecha;
    private String ubi;
    private String desc;
    //private int invitados;
    private List<Invitado> invitados;

    public Evento(String nombre, String ubicacion, String desc, LocalDate fecha){

        this.nombre = nombre;
        this.fecha = fecha;
        this.ubi = ubicacion;
        this.desc = desc;
        //this.invitados = invitados;
        this.invitados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUbi() {
        return ubi;
    }

    public void setUbi(String ubi) {
        this.ubi = ubi;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void addInvitado(Invitado invitado) {
    this.invitados.add(invitado);
    }

    public List<Invitado> getInvitados() {
    return invitados;
}


}
