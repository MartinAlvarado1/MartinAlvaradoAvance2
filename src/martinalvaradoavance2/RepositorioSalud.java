
package martinalvaradoavance2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RepositorioSalud {

    // Archivo donde se almacenará el historial de mediciones
    private final String rutaArchivo;

    // Constructor
    public RepositorioSalud(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // Guarda el historial completo en un archivo
    public void guardarHistorial(HistorialSalud historial)
            throws IOException {

        if (historial == null) {
            throw new IllegalArgumentException(
                    "El historial no puede estar vacío.");
        }

        try (ObjectOutputStream salida =
                new ObjectOutputStream(
                        new FileOutputStream(rutaArchivo))) {

            salida.writeObject(historial);
        }
    }

    // Recupera el historial previamente guardado
    public HistorialSalud cargarHistorial()
            throws IOException, ClassNotFoundException {

        File archivo = new File(rutaArchivo);

        // Si el archivo todavía no existe, crea un historial vacío
        if (!archivo.exists()) {
            return new HistorialSalud();
        }

        try (ObjectInputStream entrada =
                new ObjectInputStream(
                        new FileInputStream(archivo))) {

            Object objeto = entrada.readObject();

            if (objeto instanceof HistorialSalud) {
                return (HistorialSalud) objeto;
            }

            throw new IOException(
                    "El archivo no contiene un historial válido.");
        }
    }
}

