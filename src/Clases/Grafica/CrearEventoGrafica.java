package Clases.Grafica;

import javax.swing.*;

import Clases.Controladores.ControladorEventos;
import Clases.Modelo.Evento;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CrearEventoGrafica extends JPanel {

    private JTextField txtNombre, txtUbi, txtFecha;
    private JTextArea txtDesc;
    private ControladorEventos controlador;
    private GestionEventos vistaPrincipal;

    public CrearEventoGrafica(ControladorEventos controlador, GestionEventos vistaPrincipal) {
        this.controlador = controlador;
        this.vistaPrincipal = vistaPrincipal;

        setLayout(new GridLayout(5, 2));

        txtNombre = new JTextField();
        txtUbi = new JTextField();
        txtDesc = new JTextArea(3, 20);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        txtFecha = new JTextField();

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Ubicación:"));
        add(txtUbi);
        add(new JLabel("Descripción:"));
        add(scrollDesc);
        add(new JLabel("Fecha (dd/MM/yyyy):"));
        add(txtFecha);

        JButton btnGuardar = new JButton("Guardar");
        add(new JLabel());
        add(btnGuardar);

      btnGuardar.addActionListener(e -> {
            // Solo obtenemos los textos (vista pura)
            String nombre = txtNombre.getText();
            String ubi = txtUbi.getText();
            String desc = txtDesc.getText();
            String fechaStr = txtFecha.getText();

            // Llamamos al controlador para que procese y agregue el evento,
            // actualice la tabla y cierre la ventana
            controlador.crearYAgregarEvento(nombre, ubi, desc, fechaStr, vistaPrincipal, this);
        });

    }
}
