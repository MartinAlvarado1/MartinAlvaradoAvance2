
package martinalvaradoavance2;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MartinAlvaradoAvance2 {

    public static void main(String[] args) {

        // Crear el repositorio de almacenamiento
        RepositorioUsuarios repositorio =
                new RepositorioUsuarios();

        GestorUsuarios gestorUsuarios;

        // Recuperar los datos al iniciar la aplicación
        try {

            gestorUsuarios = repositorio.cargarUsuarios();

            System.out.println(
                    "Datos de FideHealtTrack cargados correctamente."
            );

            System.out.println(
                    "Usuarios registrados: "
                    + gestorUsuarios.cantidadUsuarios()
            );

        } catch (IOException | ClassNotFoundException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudieron recuperar los datos guardados.\n"
                    + e.getMessage()
                    + "\nLa aplicación no se iniciará.",
                    "Error al cargar información",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        final GestorUsuarios gestor = gestorUsuarios;

        // Evitar mostrar muchas advertencias por el mismo error
        AtomicBoolean errorMostrado = new AtomicBoolean(false);

        // Guardado final al cerrar normalmente la aplicación
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {

                    try {

                        repositorio.guardarUsuarios(gestor);

                        System.out.println(
                                "Datos guardados al cerrar FideHealtTrack."
                        );

                    } catch (IOException e) {

                        System.err.println(
                                "Error al guardar al cerrar: "
                                + e.getMessage()
                        );
                    }
                })
        );

        // Iniciar la interfaz gráfica
        SwingUtilities.invokeLater(() -> {

            // Guardado automático cada 5 segundos
            Timer temporizador = new Timer(5000, e -> {

                try {

                    repositorio.guardarUsuarios(gestor);

                    // Si se recupera de un error, permitir
                    // mostrar futuras advertencias
                    errorMostrado.set(false);

                } catch (IOException ex) {

                    System.err.println(
                            "Error de guardado automático: "
                            + ex.getMessage()
                    );

                    if (errorMostrado.compareAndSet(false, true)) {

                        JOptionPane.showMessageDialog(
                                null,
                                "No se pudieron guardar los datos.\n"
                                + "Revise los permisos de la carpeta.\n"
                                + ex.getMessage(),
                                "Error de guardado automático",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            });

            temporizador.setRepeats(true);
            temporizador.start();

            // Mostrar la ventana de inicio de sesión
            VentanaLogin ventanaLogin =
                    new VentanaLogin(gestor);

            ventanaLogin.setVisible(true);
        });
    }
}
