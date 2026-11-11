
package martinalvaradoavance2;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;

public class RepositorioUsuarios {

    // Archivo principal de almacenamiento
    private final File archivo;

    // Constructor
    public RepositorioUsuarios() {

        File carpeta = new File(
                System.getProperty("user.home"),
                "FideHealtTrackDatos"
        );

        archivo = new File(carpeta, "usuarios.dat");
    }

    // Guardar usuarios e historiales de manera más segura
    public synchronized void guardarUsuarios(
            GestorUsuarios gestor) throws IOException {

        if (gestor == null) {
            throw new IllegalArgumentException(
                    "El gestor de usuarios no puede ser nulo.");
        }

        Path carpeta = archivo.toPath().getParent();

        // Crear la carpeta si no existe
        Files.createDirectories(carpeta);

        // Crear archivo temporal en la misma carpeta
        Path temporal = Files.createTempFile(
                carpeta, "usuarios_", ".tmp"
        );

        try {

            // Serializar la información en el temporal
            try (ObjectOutputStream salida =
                    new ObjectOutputStream(
                            Files.newOutputStream(temporal))) {

                salida.writeObject(gestor);
            }

            // Reemplazar el archivo principal
            try {

                Files.move(
                        temporal,
                        archivo.toPath(),
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );

            } catch (AtomicMoveNotSupportedException e) {

                // Alternativa si el sistema no admite
                // reemplazo atómico de archivos
                Files.move(
                        temporal,
                        archivo.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } finally {

            // Eliminar el temporal si todavía existe
            Files.deleteIfExists(temporal);
        }
    }

    // Recuperar usuarios e historiales existentes
    public GestorUsuarios cargarUsuarios()
            throws IOException, ClassNotFoundException {

        if (!archivo.exists()) {
            return new GestorUsuarios();
        }

        // Deserializar el archivo principal
        try (ObjectInputStream entrada =
                new ObjectInputStream(
                        Files.newInputStream(archivo.toPath()))) {

            Object objeto = entrada.readObject();

            if (objeto instanceof GestorUsuarios) {
                return (GestorUsuarios) objeto;
            }

            throw new IOException(
                    "El archivo no contiene información válida.");
        }
    }
}
