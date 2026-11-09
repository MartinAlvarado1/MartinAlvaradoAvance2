
package martinalvaradoavance2;

import java.time.LocalDate;
import java.time.LocalTime;

public class MartinAlvaradoAvance2 {

    public static void main(String[] args) {

        System.out.println("===== BIENVENIDO A FIDEHEALTTRACK =====");
        System.out.println("Sistema de seguimiento personal de salud");
        System.out.println("----------------------------------------");

        // PRUEBA 1: CREAR UN USUARIO
        Usuario usuario1 = new Usuario(
                1,
                "Juan Perez",
                "juan@ejemplo.com",
                "Prueba123",
                LocalDate.of(1995, 5, 15)
        );

        System.out.println("\nINFORMACION DEL USUARIO");
        System.out.println("ID: " + usuario1.getIdUsuario());
        System.out.println("Nombre: " + usuario1.getNombre());
        System.out.println("Correo: " + usuario1.getCorreo());
        System.out.println("Fecha de nacimiento: "
                + usuario1.getFechaNacimiento());

        // PRUEBA 2: INICIO DE SESION
        System.out.println("\nPRUEBA DE INICIO DE SESION");

        boolean accesoCorrecto = usuario1.iniciarSesion(
                "juan@ejemplo.com", "Prueba123"
        );

        boolean accesoIncorrecto = usuario1.iniciarSesion(
                "juan@ejemplo.com", "ClaveIncorrecta"
        );

        System.out.println("Credenciales correctas: "
                + accesoCorrecto);
        System.out.println("Credenciales incorrectas: "
                + accesoIncorrecto);

        // PRUEBA 3: CREAR UN REGISTRO GENERAL
        System.out.println("\n===== REGISTRO GENERAL DE SALUD =====");

        RegistroSalud registro1 = new RegistroSalud(
                1,
                LocalDate.now(),
                LocalTime.of(8, 30),
                75.5,
                "Medicion realizada en la manana"
        );

        System.out.println(registro1.mostrarRegistro());

        // PRUEBA 4: CREAR UN REGISTRO DE PRESION ARTERIAL
        System.out.println("\n===== REGISTRO DE PRESION ARTERIAL =====");

        PresionArterial presion1 = new PresionArterial(
                2,
                LocalDate.now(),
                LocalTime.of(9, 15),
                75.5,
                "Medicion en reposo",
                120,
                80,
                72
        );

        System.out.println(presion1.mostrarRegistro());

        // PRUEBA 5: HERENCIA
        System.out.println("\n===== PRUEBA DE HERENCIA =====");

        System.out.println("Fecha heredada: "
                + presion1.getFecha());

        System.out.println("Peso heredado: "
                + presion1.getPeso() + " kg");

        System.out.println("Presion sistolica propia: "
                + presion1.getSistolica());

        // PRUEBA 6: POLIMORFISMO
        System.out.println("\n===== PRUEBA DE POLIMORFISMO =====");

        RegistroSalud registroPolimorfico = presion1;

        System.out.println(registroPolimorfico.mostrarRegistro());

        System.out.println("\n===== FIN DE LAS PRUEBAS =====");
    }
}
