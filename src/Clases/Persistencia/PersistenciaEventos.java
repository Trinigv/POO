package Clases.Persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Clases.Modelo.Evento;
import Clases.Modelo.Invitado;

public class PersistenciaEventos {

    public static void guardarEventosEnArchivo(String archivo, List<Evento> eventos) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            for (Evento evento : eventos) {
                escritor.write("Evento:");
                escritor.newLine();
                escritor.write("Nombre:" + evento.getNombre());
                escritor.newLine();
                escritor.write("Ubicacion:" + evento.getUbi());
                escritor.newLine();
                escritor.write("Descripcion:" + evento.getDesc());
                escritor.newLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                escritor.write("Fecha:" + evento.getFecha().format(formatter));
                escritor.newLine();

                escritor.write("Invitados:");
                escritor.newLine();
                if (evento.getInvitados() != null) {
                    for (Invitado invitado : evento.getInvitados()) {
                        escritor.write(invitado.getNombre() + "," + invitado.getEmail());
                        escritor.newLine();
                    }
                }

                escritor.write("---"); // separador entre eventos
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar eventos: " + e.getMessage());
        }
    }

    public static List<Evento> cargarEventosDesdeArchivo(String archivo) {
    List<Evento> eventos = new ArrayList<>();
    try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
        String linea;
        Evento eventoActual = null;
        List<Invitado> invitadosActuales = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while ((linea = lector.readLine()) != null) {
            if (linea.equals("Evento:")) {
                eventoActual = new Evento("", "", "", LocalDate.now()); // valores por defecto
                invitadosActuales = new ArrayList<>();
            } else if (linea.startsWith("Nombre:")) {
                eventoActual.setNombre(linea.substring(7));
            } else if (linea.startsWith("Ubicacion:")) {
                eventoActual.setUbi(linea.substring(10));
            } else if (linea.startsWith("Descripcion:")) {
                eventoActual.setDesc(linea.substring(12));
            } else if (linea.startsWith("Fecha:")) {
                eventoActual.setFecha(LocalDate.parse(linea.substring(6), formatter));
            } else if (linea.equals("Invitados:")) {
                // empieza la lista de invitados
            } else if (linea.equals("---")) {
                for(Invitado inv : invitadosActuales) {
                    eventoActual.addInvitado(inv);
                }
                eventos.add(eventoActual);
                eventoActual = null;
            } else if (!linea.isEmpty() && eventoActual != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    invitadosActuales.add(new Invitado(partes[0], partes[1]));
                }
            }
        }

    } catch (Exception e) {
        System.err.println("Error al cargar eventos: " + e.getMessage());
    }

    return eventos;
}
}
