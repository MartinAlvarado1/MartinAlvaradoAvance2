
package martinalvaradoavance2;

import java.io.Serializable;
import java.time.LocalDate;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos del usuario
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private LocalDate fechaNacimiento;

    // Constructor
    public Usuario(int idUsuario, String nombre, String correo,
                   String contrasena, LocalDate fechaNacimiento) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Métodos para consultar los atributos
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Métodos para modificar los atributos
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Verifica si las credenciales coinciden
    public boolean iniciarSesion(String correo, String contrasena) {
        return this.correo.equalsIgnoreCase(correo)
                && this.contrasena.equals(contrasena);
    }

    // Devuelve información básica del usuario
    @Override
    public String toString() {
        return "Usuario: " + nombre + " - Correo: " + correo;
    }
}

