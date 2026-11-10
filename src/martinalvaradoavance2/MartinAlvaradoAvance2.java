
package martinalvaradoavance2;

import javax.swing.SwingUtilities;

public class MartinAlvaradoAvance2 {

    public static void main(String[] args) {

        // Ejecutar la interfaz gráfica en el hilo de Swing
        SwingUtilities.invokeLater(() -> {

            // Crear el gestor compartido de usuarios
            GestorUsuarios gestorUsuarios = new GestorUsuarios();

            // Abrir la ventana de inicio de sesión
            VentanaLogin ventanaLogin =
                    new VentanaLogin(gestorUsuarios);

            ventanaLogin.setVisible(true);
        });
    }
}
