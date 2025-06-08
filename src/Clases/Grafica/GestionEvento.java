package Clases.Grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Clases.Controladores.ButtonController;
import Clases.Controladores.ControladorEvento;

import java.awt.*;

public class GestionEvento extends JPanel {

    private JTable table;
    private ControladorEvento controlador;

    public GestionEvento(ControladorEvento controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        // Tabla
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JButton botonNuevoEvento = new JButton("Nuevo Evento");
        botonNuevoEvento.addActionListener(e -> controlador.abrirCrearNuevoEvento(this));

        JButton botonGuardar = new JButton("Guardar en archivo");
        botonGuardar.addActionListener(e -> controlador.guardar());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonNuevoEvento);
        panelBotones.add(botonGuardar);
        add(panelBotones, BorderLayout.SOUTH);

        // Inicializo tabla
        controlador.actualizarTabla(this);
    }

    public JTable getTable() {
        return table;
    }
}
