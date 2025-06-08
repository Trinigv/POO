package Clases.Grafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Clases.Controladores.ControladorEventos;
import Clases.Modelo.Evento;

import java.awt.*;

public class DetalleEvento extends JFrame {

    private Evento evento;
    private ControladorEventos controlador;

    public DetalleEvento(Evento evento, ControladorEventos controlador) {

        this.evento = evento;
        this.controlador = controlador;

        setTitle("Detalle del Evento");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel nombreEv = new JLabel("Nombre: " + evento.getNombre());
        JLabel fechaEv = new JLabel("Fecha: " + evento.getFecha());

        JTextArea descripEv = new JTextArea(evento.getDesc());
        descripEv.setLineWrap(true);
        descripEv.setWrapStyleWord(true);
        descripEv.setEditable(false);

        JLabel labelInvitados = new JLabel("Invitados:");
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        JList<String> listaInvitados = new JList<>(listaModel);
        JScrollPane scrollLista = new JScrollPane(listaInvitados);

        // Cargar los invitados existentes en la lista
        for (var inv : evento.getInvitados()) {
            listaModel.addElement(inv.getNombre() + " - " + inv.getEmail());
        }

        JTextField campoNombre = new JTextField();
        JTextField campoEmail = new JTextField();
        campoNombre.setMaximumSize(new Dimension(400, 25));
        campoEmail.setMaximumSize(new Dimension(400, 25));

        JButton botonAgregar = new JButton("Agregar Invitado");

        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            String email = campoEmail.getText().trim();
            controlador.agregarInvitadoAEvento(evento, nombre, email, listaModel);
            campoNombre.setText("");
            campoEmail.setText("");
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(nombreEv);
        panel.add(fechaEv);
        panel.add(new JLabel("Descripción:"));
        panel.add(new JScrollPane(descripEv));
        panel.add(labelInvitados);
        panel.add(scrollLista);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Nombre completo:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(campoNombre);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Correo electrónico:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(campoEmail);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(botonAgregar);

        add(panel);
    }
}
