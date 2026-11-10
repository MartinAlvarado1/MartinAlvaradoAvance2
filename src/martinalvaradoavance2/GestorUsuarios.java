
package martinalvaradoavance2;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorUsuarios {

    // Colección que almacena los usuarios registrados
    private ArrayList<Usuario> listaUsuarios;

    // Constructor
    public GestorUsuarios() {
        listaUsuarios = new ArrayList<>();
    }

    // Registra un nuevo usuario
    public void registrarUsuario(String nombre, String correo,
            String contrasena, LocalDate fechaNacimiento) {

        // Verificar que los datos no estén vacíos
        if (nombre == null || nombre.trim().isEmpty()
                || correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {

            throw new IllegalArgumentException(
                    "Todos los campos obligatorios deben completarse.");
        }

        // Validar el formato básico del correo
        correo = correo.trim();

        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException(
                    "Debe ingresar un correo electrónico válido.");
        }

        // Verificar que el correo no esté registrado
        if (buscarUsuario(correo) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una cuenta con este correo electrónico.");
        }

        // Validar longitud mínima de la contraseña
        if (contrasena.length() < 8) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres.");
        }

        // Asignar un identificador al nuevo usuario
        int nuevoId = 1;

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdUsuario() >= nuevoId) {
                nuevoId = usuario.getIdUsuario() + 1;
            }
        }

        // Crear el objeto Usuario
        Usuario nuevoUsuario = new Usuario(
                nuevoId,
                nombre.trim(),
                correo,
                contrasena,
                fechaNacimiento
        );

        // Agregarlo a la colección
        listaUsuarios.add(nuevoUsuario);
    }

    // Busca un usuario por correo electrónico
    public Usuario buscarUsuario(String correo) {

        if (correo == null) {
            return null;
        }

        for (Usuario usuario : listaUsuarios) {

            if (usuario.getCorreo().equalsIgnoreCase(
                    correo.trim())) {

                return usuario;
            }
        }

        return null;
    }

    // Valida el inicio de sesión
    public Usuario iniciarSesion(String correo, String contrasena) {

        Usuario usuario = buscarUsuario(correo);

        if (usuario != null
                && usuario.iniciarSesion(correo, contrasena)) {

            return usuario;
        }

        return null;
    }

    // Devuelve la cantidad de usuarios registrados
    public int cantidadUsuarios() {
        return listaUsuarios.size();
    }
}
