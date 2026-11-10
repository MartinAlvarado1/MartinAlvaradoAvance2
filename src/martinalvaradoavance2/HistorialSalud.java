
package martinalvaradoavance2;

import java.io.Serializable;
import java.util.ArrayList;

public class HistorialSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    // Colección que almacena las mediciones de salud
    private ArrayList<RegistroSalud> listaRegistros;

    // Constructor
    public HistorialSalud() {
        listaRegistros = new ArrayList<>();
    }

    // Agrega una nueva medición al historial
    public void agregarRegistro(RegistroSalud registro) {

        if (registro == null) {
            throw new IllegalArgumentException(
                    "No se puede agregar un registro vacío.");
        }

        if (buscarRegistro(registro.getIdRegistro()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un registro con ese identificador.");
        }

        listaRegistros.add(registro);
    }

    // Busca una medición según su identificador
    public RegistroSalud buscarRegistro(int idRegistro) {

        for (RegistroSalud registro : listaRegistros) {

            if (registro.getIdRegistro() == idRegistro) {
                return registro;
            }
        }

        return null;
    }

    // Muestra todas las mediciones registradas
    public String mostrarHistorial() {

        if (listaRegistros.isEmpty()) {
            return "No hay mediciones registradas.";
        }

        StringBuilder historial = new StringBuilder();

        for (RegistroSalud registro : listaRegistros) {

            historial.append(registro.mostrarRegistro());
            historial.append("\n-------------------------\n");
        }

        return historial.toString();
    }

    // Elimina una medición según su identificador
    public boolean eliminarRegistro(int idRegistro) {

        RegistroSalud registro = buscarRegistro(idRegistro);

        if (registro != null) {
            listaRegistros.remove(registro);
            return true;
        }

        return false;
    }

    // Devuelve la cantidad de mediciones guardadas
    public int cantidadRegistros() {
        return listaRegistros.size();
    }

    // Devuelve una copia de los registros del historial
    public ArrayList<RegistroSalud> getListaRegistros() {
        return new ArrayList<>(listaRegistros);
    }
}
