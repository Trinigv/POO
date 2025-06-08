import javax.swing.*;
import java.awt.*;

import Clases.Controladores.ControladorEvento;
import Clases.Grafica.GestionEvento;


public class App extends JFrame {

    public App() {
        setTitle("Gestión de Eventos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear instancia del gestor que maneja la lógica
        ControladorEvento gestor = new ControladorEvento();

        // Pasar el gestor a la interfaz
        add(new GestionEvento(gestor), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
