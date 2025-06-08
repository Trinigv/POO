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
import Clases.Grafica.CrearEvento;
import Clases.Grafica.DetalleEvento;
import Clases.Grafica.GestionEvento;

public class ControladorEvento {

    private List<Evento> eventos;

    // Cargo datos del archivo desde el constructor
    public ControladorEvento() {
        eventos = PersistenciaEventos.cargarEventosDesdeArchivo("eventos.txt");
    }


// ------------------------------------CREAR NUEVO EVENTO--------------------------------------------------

 // Abro nuevo frame
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
   
    // Agrego evento a la lista
    public void agregarEvento(Evento evento, GestionEvento vista) {
        eventos.add(evento);
        actualizarTabla(vista);
    }

    // Actualizo tabla
    public void actualizarTabla(GestionEvento vista) {
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"Nombre", "Ubicación", "Descripción", "Fecha", "Acción"}, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {
            // Solo la última columna es clickeable
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
    //  -------------------------------------FINALIZA CREAR EVENTO------------------------------------------------------------


    // Guardar al archivo 
    public void guardar() {
        PersistenciaEventos.guardarEventosEnArchivo("eventos.txt", eventos);
        JOptionPane.showMessageDialog(null, "Eventos guardados en eventos.txt");
    }


    // Muestro los detalles del evento
   public void mostrarDetalleEvento(int fila, GestionEvento vista) {
        Evento evento = eventos.get(fila);
        DetalleEvento detalle = new DetalleEvento(evento, this);  // <-- pasa el controlador aquí
        detalle.setLocationRelativeTo(null);
        detalle.setVisible(true);
    }

    // Logica para agregar invitado al evento
    public void agregarInvitadoAEvento(Evento evento, String nombre, String email, DefaultListModel<String> listaModel) {
        if (!nombre.isEmpty() && !email.isEmpty()) {
            Invitado nuevo = new Invitado(nombre, email);
            evento.addInvitado(nuevo);
            listaModel.addElement(nombre + " - " + email);
        } else {
            JOptionPane.showMessageDialog(null, "Nombre y email no pueden estar vacíos.");
        }
    }

    
}
