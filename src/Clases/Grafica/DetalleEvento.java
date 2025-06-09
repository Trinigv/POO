package Clases.Grafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Clases.Controladores.ControladorEvento;
import Clases.Modelo.Invitado;  // Asumo que tenés esta clase

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DetalleEvento extends JFrame {

    private ControladorEvento controlador;
    private GestionEvento vista;

    private JTextField campoNombre;
    private JTextField campoUbicacion;
    private JTextField campoFecha;
    private JTextArea campoDescripcion;
    private DefaultListModel<String> listaModel;

    public DetalleEvento(ControladorEvento controlador, GestionEvento vista) {
        this.controlador = controlador;
        this.vista = vista;

        setTitle("Detalle del Evento");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Obtener datos del evento a través del controlador
        campoNombre = new JTextField(controlador.getNombreEvento());
        campoUbicacion = new JTextField(controlador.getUbicacionEvento());
        campoFecha = new JTextField(controlador.getFechaEvento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        campoDescripcion = new JTextArea(controlador.getDescripcionEvento());
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        listaModel = new DefaultListModel<>();
        JList<String> listaInvitados = new JList<>(listaModel);
        JScrollPane scrollLista = new JScrollPane(listaInvitados);

        cargarListaInvitados();

        JTextField campoNombreInvitado = new JTextField();
        JTextField campoEmailInvitado = new JTextField();
        campoNombreInvitado.setMaximumSize(new Dimension(400, 25));
        campoEmailInvitado.setMaximumSize(new Dimension(400, 25));

        JButton botonAgregarInvitado = new JButton("Agregar Invitado");
        botonAgregarInvitado.addActionListener(e -> {
            String nombre = campoNombreInvitado.getText().trim();
            String email = campoEmailInvitado.getText().trim();
            controlador.agregarInvitado(nombre, email);
            campoNombreInvitado.setText("");
            campoEmailInvitado.setText("");
            cargarListaInvitados();  // Refrescar la lista después de agregar
        });

        JButton botonGuardarCambios = new JButton("Guardar Cambios");
        botonGuardarCambios.addActionListener(e -> {
            controlador.actualizarDatosEvento(
                campoNombre.getText().trim(),
                campoUbicacion.getText().trim(),
                campoFecha.getText().trim(),
                campoDescripcion.getText().trim(),
                this,
                vista
            );
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Ubicación:"));
        panel.add(campoUbicacion);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Fecha (dd/MM/yyyy):"));
        panel.add(campoFecha);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Descripción:"));
        panel.add(new JScrollPane(campoDescripcion));

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(botonGuardarCambios);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(new JLabel("Invitados:"));
        panel.add(scrollLista);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(new JLabel("Agregar nuevo invitado:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre completo:"));
        panel.add(campoNombreInvitado);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Correo electrónico:"));
        panel.add(campoEmailInvitado);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botonAgregarInvitado);

        add(panel);
    }

    private void cargarListaInvitados() {
        listaModel.clear();
        List<Invitado> invitados = controlador.getInvitadosEvento();
        for (Invitado inv : invitados) {
            listaModel.addElement(inv.getNombre() + " - " + inv.getEmail());
        }
    }
}
