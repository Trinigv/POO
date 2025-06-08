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
import Clases.Persistencia.PersistenciaEventos;
import Clases.Grafica.ButtonRenderer;
import Clases.Grafica.CrearEventoGrafica;
import Clases.Grafica.DetalleEvento;
import Clases.Grafica.GestionEventos;

public class ControladorEventos {

    private List<Evento> eventos;

    public ControladorEventos() {
        eventos = PersistenciaEventos.cargarEventosDesdeArchivo("eventos.txt");
    }

    public void abrirCrearNuevoEvento(GestionEventos vista) {
        JFrame frame = new JFrame("Crear Nuevo Evento");
        CrearEventoGrafica crearEventoPanel = new CrearEventoGrafica(this, vista);
        frame.setContentPane(crearEventoPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void agregarInvitadoAEvento(Evento evento, String nombre, String email, DefaultListModel<String> listaModel) {
    if (!nombre.isEmpty() && !email.isEmpty()) {
        Invitado nuevo = new Invitado(nombre, email);
        evento.addInvitado(nuevo);
        listaModel.addElement(nombre + " - " + email);
    } else {
        JOptionPane.showMessageDialog(null, "Nombre y email no pueden estar vacíos.");
    }
}

    public void crearYAgregarEvento(String nombre, String ubi, String desc, String fechaStr,
                                    GestionEventos vistaPrincipal, JPanel panelFormulario) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Evento nuevoEvento = new Evento(nombre, ubi, desc, fecha);
            eventos.add(nuevoEvento);
            actualizarTabla(vistaPrincipal);

            // Cierra la ventana que contiene el formulario
            SwingUtilities.getWindowAncestor(panelFormulario).dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelFormulario, "Error: " + ex.getMessage());
        }
    }

    public void agregarEvento(Evento evento, GestionEventos vista) {
        eventos.add(evento);
        actualizarTabla(vista);
    }

    public void actualizarTabla(GestionEventos vista) {
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"Nombre", "Ubicación", "Descripción", "Fecha", "Acción"}, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {
            // Solo la última columna (el botón) es editable
            return column == 4;
        }
    };

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    for (Evento e : eventos) {
        model.addRow(new Object[]{
            e.getNombre(), e.getUbi(), e.getDesc(), e.getFecha().format(formatter), "Ver detalle"
        });
    }

    JTable table = vista.getTable();
    table.setModel(model);

    // Asigna el renderizador y editor del botón a la última columna
    table.getColumn("Acción").setCellRenderer(new ButtonRenderer());
    table.getColumn("Acción").setCellEditor(new ButtonController(vista, this));
}


    public void guardar() {
        PersistenciaEventos.guardarEventosEnArchivo("eventos.txt", eventos);
        JOptionPane.showMessageDialog(null, "Eventos guardados en eventos.txt");
    }

   public void mostrarDetalleEvento(int fila, GestionEventos vista) {
    Evento evento = eventos.get(fila);
    DetalleEvento detalle = new DetalleEvento(evento, this);  // <-- pasa el controlador aquí
    detalle.setLocationRelativeTo(null);
    detalle.setVisible(true);
}

    
}
