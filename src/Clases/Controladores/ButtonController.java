package Clases.Controladores;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

import Clases.Grafica.GestionEvento;

public class ButtonController extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private JButton button;
    private int currentRow;
    private GestionEvento parent;
    private ControladorEvento controlador;

    public ButtonController(GestionEvento parent, ControladorEvento gestor) {
        this.parent = parent;
        this.controlador = gestor;
        button = new JButton("Ver detalle");
        button.addActionListener(this);
    }

    @Override
    public Object getCellEditorValue() {
        return "Ver detalle";
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        currentRow = row;
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controlador.mostrarDetalleEvento(currentRow, parent);
        fireEditingStopped();
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Siempre editable para que el botón funcione
    }
}
