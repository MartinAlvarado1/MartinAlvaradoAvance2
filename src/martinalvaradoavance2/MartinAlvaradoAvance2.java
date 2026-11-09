
package martinalvaradoavance2;

import java.time.LocalDate;

public class MartinAlvaradoAvance2 {

    public static void main(String[] args) {

        // Mensaje de bienvenida
        System.out.println("===== BIENVENIDO A FIDEHEALTTRACK =====");
        System.out.println("Sistema de seguimiento personal de salud");
        System.out.println("----------------------------------------");

        // Crear un usuario de prueba con datos ficticios
        Usuario usuario1 = new Usuario(
                1,
                "Juan Perez",
                "juan@ejemplo.com",
                "Prueba123",
                LocalDate.of(1995, 5, 15)
        );

        // Mostrar la informacion del usuario
        System.out.println("\nINFORMACION DEL USUARIO");
        System.out.println("ID: " + usuario1.getIdUsuario());
        System.out.println("Nombre: " + usuario1.getNombre());
        System.out.println("Correo: " + usuario1.getCorreo());
        System.out.println("Fecha de nacimiento: " 
                + usuario1.getFechaNacimiento());

        // Probar el metodo toString()
        System.out.println("\nDATOS GENERALES");
        System.out.println(usuario1.toString());

        // Primera prueba: inicio de sesion correcto
        System.out.println("\nPRUEBA 1: INICIO DE SESION CORRECTO");

        boolean accesoCorrecto = usuario1.iniciarSesion(
                "juan@ejemplo.com",
                "Prueba123"
        );

        if (accesoCorrecto) {
            System.out.println("Inicio de sesion exitoso.");
            System.out.println("Bienvenido, " + usuario1.getNombre());
        } else {
            System.out.println("Correo o contrasena incorrectos.");
        }

        // Segunda prueba: inicio de sesion incorrecto
        System.out.println("\nPRUEBA 2: INICIO DE SESION INCORRECTO");

        boolean accesoIncorrecto = usuario1.iniciarSesion(
                "juan@ejemplo.com",
                "ClaveIncorrecta"
        );

        if (accesoIncorrecto) {
            System.out.println("Inicio de sesion exitoso.");
        } else {
            System.out.println("Acceso denegado.");
            System.out.println("Correo o contrasena incorrectos.");
        }

        // Comprobar los resultados
        System.out.println("\nRESULTADOS DE LAS PRUEBAS");

        System.out.println("Credenciales correctas: "
                + accesoCorrecto);

        System.out.println("Credenciales incorrectas: "
                + accesoIncorrecto);

        System.out.println("\n===== FIN DE LAS PRUEBAS =====");
    }
}
