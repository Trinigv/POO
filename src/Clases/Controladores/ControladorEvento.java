package Clases.Controladores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Clases.Modelo.Evento;
import Clases.Modelo.Invitado;
import Clases.Persistencia.PersistenciaEvento;
import Clases.Grafica.ButtonRenderer;
import Clases.Grafica.CrearEvento;
import Clases.Grafica.DetalleEvento;
import Clases.Grafica.GestionEvento;

public class ControladorEvento {

    private List<Evento> eventos;
    private Evento eventoSeleccionado;

    public ControladorEvento() {
        eventos = PersistenciaEvento.cargarEventosDesdeArchivo("eventos.txt");
    }

    // ------------------------------------ CREAR NUEVO EVENTO ------------------------------------
    public void abrirCrearNuevoEvento(GestionEvento vista) {
        JFrame frame = new JFrame("Crear Nuevo Evento");
        CrearEvento crearEventoPanel = new CrearEvento(this, vista);
        frame.setContentPane(crearEventoPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void crearYAgregarEvento(String nombre, String ubi, String desc, String fechaStr,
                                    GestionEvento vistaPrincipal, JPanel panelFormulario) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Evento nuevoEvento = new Evento(nombre, ubi, desc, fecha);
            eventos.add(nuevoEvento);
            actualizarTabla(vistaPrincipal);

            SwingUtilities.getWindowAncestor(panelFormulario).dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelFormulario, "Error: " + ex.getMessage());
        }
    }

    public void agregarEvento(Evento evento, GestionEvento vista) {
        eventos.add(evento);
        actualizarTabla(vista);
    }

    public void actualizarTabla(GestionEvento vista) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Nombre", "Ubicación", "Descripción", "Fecha", "Acción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Evento e : eventos) {
            model.addRow(new Object[]{
                    e.getNombre(),
                    e.getUbi(),
                    e.getDesc(),
                    e.getFecha().format(formatter),
                    "Ver detalle"
            });
        }

        JTable table = vista.getTable();
        table.setModel(model);

        table.getColumn("Acción").setCellRenderer(new ButtonRenderer());
        table.getColumn("Acción").setCellEditor(new ButtonController(vista, this));

    }

    // ------------------------------------ DETALLE EVENTO ------------------------------------

    public void mostrarDetalleEvento(int fila, GestionEvento vista) {
        eventoSeleccionado = eventos.get(fila);
        DetalleEvento detalle = new DetalleEvento(this, vista);
        detalle.setLocationRelativeTo(null);
        detalle.setVisible(true);
    }

    public String getNombreEvento() {
        return eventoSeleccionado != null ? eventoSeleccionado.getNombre() : "";
    }

    public String getUbicacionEvento() {
        return eventoSeleccionado != null ? eventoSeleccionado.getUbi() : "";
    }

    public LocalDate getFechaEvento() {
        return eventoSeleccionado != null ? eventoSeleccionado.getFecha() : LocalDate.now();
    }

    public String getDescripcionEvento() {
        return eventoSeleccionado != null ? eventoSeleccionado.getDesc() : "";
    }

    public List<Invitado> getInvitadosEvento() {
        return eventoSeleccionado != null ? eventoSeleccionado.getInvitados() : List.of();
    }

    public void actualizarDatosEvento(String nuevoNombre, String nuevaUbicacion, String nuevaFechaStr,
                                      String nuevaDescripcion, JFrame ventana, GestionEvento vista) {
        if (eventoSeleccionado == null) {
            JOptionPane.showMessageDialog(ventana, "No hay evento seleccionado.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr, formatter);

            eventoSeleccionado.setNombre(nuevoNombre);
            eventoSeleccionado.setUbi(nuevaUbicacion);
            eventoSeleccionado.setFecha(nuevaFecha);
            eventoSeleccionado.setDesc(nuevaDescripcion);

            actualizarTabla(vista);

            JOptionPane.showMessageDialog(ventana, "Evento actualizado correctamente.");
            ventana.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ventana, "Error al actualizar evento: " + ex.getMessage());
        }
    }

    public void agregarInvitado(String nombre, String email) {
        if (eventoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "No hay evento seleccionado.");
            return;
        }
        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y email no pueden estar vacíos.");
            return;
        }
        Invitado nuevo = new Invitado(nombre, email);
        eventoSeleccionado.addInvitado(nuevo);
    }

    // ------------------------------------ GUARDAR ------------------------------------

    public void guardar() {
        PersistenciaEvento.guardarEventosEnArchivo("eventos.txt", eventos);
        JOptionPane.showMessageDialog(null, "Eventos guardados en eventos.txt");
    }
}