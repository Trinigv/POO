import javax.swing.*;
import java.awt.*;

import Clases.Controladores.ControladorEventos;
import Clases.Grafica.GestionEventos;


public class App extends JFrame {

    public App() {
        setTitle("Gestión de Eventos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear instancia del gestor que maneja la lógica
        ControladorEventos gestor = new ControladorEventos();

        // Pasar el gestor a la interfaz
        add(new GestionEventos(gestor), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
