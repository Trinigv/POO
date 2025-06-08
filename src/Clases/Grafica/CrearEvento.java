package Clases.Grafica;

import javax.swing.*;

import Clases.Controladores.ControladorEvento;
import Clases.Modelo.Evento;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CrearEvento extends JPanel {

    private JTextField txtNombre, txtUbi, txtFecha;
    private JTextArea txtDesc;
    private ControladorEvento controlador;
    private GestionEvento vistaPrincipal;
public CrearEvento(ControladorEvento controlador, GestionEvento vistaPrincipal) {
    this.controlador = controlador;
    this.vistaPrincipal = vistaPrincipal;

    setLayout(new BorderLayout(10,10));
    setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    // Panel para los 4 campos pequeños: nombre, ubi, fecha
    JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
    txtNombre = new JTextField();
    txtUbi = new JTextField();
    txtFecha = new JTextField();

    panelCampos.add(new JLabel("Nombre:"));
    panelCampos.add(txtNombre);
    panelCampos.add(new JLabel("Ubicación:"));
    panelCampos.add(txtUbi);
    panelCampos.add(new JLabel("Fecha (dd/MM/yyyy):"));
    panelCampos.add(txtFecha);
    panelCampos.add(new JLabel()); // espacio vacío para que el botón esté en la siguiente fila
    JButton btnGuardar = new JButton("Guardar");
    panelCampos.add(btnGuardar);

    // Panel para la descripción, que ocupará más espacio
    txtDesc = new JTextArea(6, 20);
    txtDesc.setLineWrap(true);
    txtDesc.setWrapStyleWord(true);
    JScrollPane scrollDesc = new JScrollPane(txtDesc);
    JPanel panelDesc = new JPanel(new BorderLayout());
    panelDesc.add(new JLabel("Descripción:"), BorderLayout.NORTH);
    panelDesc.add(scrollDesc, BorderLayout.CENTER);

    add(panelCampos, BorderLayout.NORTH);
    add(panelDesc, BorderLayout.CENTER);

    btnGuardar.addActionListener(e -> {
        String nombre = txtNombre.getText();
        String ubi = txtUbi.getText();
        String desc = txtDesc.getText();
        String fechaStr = txtFecha.getText();

        controlador.crearYAgregarEvento(nombre, ubi, desc, fechaStr, vistaPrincipal, this);
    });
    }

}
