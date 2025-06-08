package Clases.Grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Clases.Controladores.ButtonController;
import Clases.Controladores.ControladorEventos;

import java.awt.*;

public class GestionEventos extends JPanel {

    private JTable table;
    private ControladorEventos controlador;

    public GestionEventos(ControladorEventos controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton botonNuevoEvento = new JButton("Nuevo Evento");
        botonNuevoEvento.addActionListener(e -> controlador.abrirCrearNuevoEvento(this));

        JButton botonGuardar = new JButton("Guardar en archivo");
        botonGuardar.addActionListener(e -> controlador.guardar());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonNuevoEvento);
        panelBotones.add(botonGuardar);
        add(panelBotones, BorderLayout.SOUTH);

        controlador.actualizarTabla(this);

        // Aquí asignamos el botón en la columna "Acción"
        table.getColumn("Acción").setCellRenderer(new ButtonRenderer());
        table.getColumn("Acción").setCellEditor(new ButtonController(this, controlador));
    }

    public JTable getTable() {
        return table;
    }
}
