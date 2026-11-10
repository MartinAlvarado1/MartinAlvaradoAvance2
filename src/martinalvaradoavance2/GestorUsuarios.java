
package martinalvaradoavance2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestorUsuarios {

    // Lista de usuarios registrados
    private ArrayList<Usuario> listaUsuarios;

    // Historial independiente para cada usuario
    private HashMap<Integer, HistorialSalud> historiales;

    // Constructor
    public GestorUsuarios() {
        listaUsuarios = new ArrayList<>();
        historiales = new HashMap<>();
    }

    // Registrar un nuevo usuario
    public void registrarUsuario(String nombre, String correo,
            String contrasena, LocalDate fechaNacimiento) {

        if (nombre == null || nombre.trim().isEmpty()
                || correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {

            throw new IllegalArgumentException(
                    "Todos los campos obligatorios deben completarse.");
        }

        correo = correo.trim();

        // Validar formato del correo
        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException(
                    "Debe ingresar un correo electrónico válido.");
        }

        // Validar correo duplicado
        if (buscarUsuario(correo) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una cuenta con este correo electrónico.");
        }

        // Validar contraseña
        if (contrasena.length() < 8) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres.");
        }

        // Generar identificador
        int nuevoId = 1;

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdUsuario() >= nuevoId) {
                nuevoId = usuario.getIdUsuario() + 1;
            }
        }

        // Crear usuario
        Usuario nuevoUsuario = new Usuario(
                nuevoId,
                nombre.trim(),
                correo,
                contrasena,
                fechaNacimiento
        );

        listaUsuarios.add(nuevoUsuario);

        // Crear un historial exclusivo para el usuario
        historiales.put(nuevoId, new HistorialSalud());
    }

    // Buscar usuario por correo
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

    // Validar las credenciales
    public Usuario iniciarSesion(String correo, String contrasena) {

        Usuario usuario = buscarUsuario(correo);

        if (usuario != null
                && usuario.iniciarSesion(correo, contrasena)) {
            return usuario;
        }

        return null;
    }

    // Obtener el historial exclusivo de un usuario
    public HistorialSalud obtenerHistorial(Usuario usuario) {

        if (usuario == null
                || !historiales.containsKey(usuario.getIdUsuario())) {

            throw new IllegalArgumentException(
                    "El usuario no tiene un historial registrado.");
        }

        return historiales.get(usuario.getIdUsuario());
    }

    // Cantidad total de usuarios
    public int cantidadUsuarios() {
        return listaUsuarios.size();
    }
}
