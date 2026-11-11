
package martinalvaradoavance2;

import java.io.Serializable;
import java.time.LocalDate;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    // Información del usuario
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasenaHash;
    private LocalDate fechaNacimiento;

    // Constructor
    public Usuario(int idUsuario, String nombre, String correo,
            String contrasena, LocalDate fechaNacimiento) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;

        // Guardar un hash, nunca la contraseña original
        this.contrasenaHash =
                SeguridadContrasenas.generarHash(contrasena);
    }

    // Consultar información del usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Modificar información del usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Actualizar contraseña de forma protegida
    public void setContrasena(String nuevaContrasena) {

        this.contrasenaHash =
                SeguridadContrasenas.generarHash(
                        nuevaContrasena
                );
    }

    // Verificar las credenciales
    public boolean iniciarSesion(
            String correo, String contrasena) {

        return this.correo.equalsIgnoreCase(correo)
                && SeguridadContrasenas.verificarContrasena(
                        contrasena,
                        contrasenaHash
                );
    }

    // Mostrar información general, sin datos sensibles
    @Override
    public String toString() {

        return "Usuario: " + nombre
                + " - Correo: " + correo;
    }
}
