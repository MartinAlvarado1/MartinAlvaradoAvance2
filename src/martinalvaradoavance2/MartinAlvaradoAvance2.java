
package martinalvaradoavance2;

import javax.swing.SwingUtilities;

public class MartinAlvaradoAvance2 {

    public static void main(String[] args) {

        // Ejecutar la interfaz gráfica en el hilo de Swing
        SwingUtilities.invokeLater(() -> {

            // Administrador de usuarios y sus historiales
            GestorUsuarios gestorUsuarios = new GestorUsuarios();

            // Abrir la pantalla de inicio de sesión
            VentanaLogin ventanaLogin =
                    new VentanaLogin(gestorUsuarios);

            ventanaLogin.setVisible(true);
        });
    }
}
