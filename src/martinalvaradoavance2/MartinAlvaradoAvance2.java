
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

        System.out.println("\n===== INFORMACION DEL USUARIO =====");
        System.out.println(usuario1);

        // PRUEBA 2: INICIO DE SESION
        System.out.println("\n===== PRUEBA DE INICIO DE SESION =====");

        System.out.println("Credenciales correctas: "
                + usuario1.iniciarSesion("juan@ejemplo.com", "Prueba123"));

        System.out.println("Credenciales incorrectas: "
                + usuario1.iniciarSesion("juan@ejemplo.com", "ClaveIncorrecta"));

        // PRUEBA 3: CREAR UN REGISTRO GENERAL
        RegistroSalud registro1 = new RegistroSalud(
                1,
                LocalDate.now(),
                LocalTime.of(8, 30),
                75.5,
                "Medicion realizada en la manana"
        );

        // PRUEBA 4: CREAR UN REGISTRO DE PRESION ARTERIAL
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

        System.out.println("\n===== PRUEBA DE HERENCIA =====");
        System.out.println("Fecha heredada: " + presion1.getFecha());
        System.out.println("Peso heredado: " + presion1.getPeso() + " kg");
        System.out.println("Presion sistolica: " + presion1.getSistolica());

        // PRUEBA 5: POLIMORFISMO
        System.out.println("\n===== PRUEBA DE POLIMORFISMO =====");

        RegistroSalud registroPolimorfico = presion1;
        System.out.println(registroPolimorfico.mostrarRegistro());

        // PRUEBA 6: CREAR EL HISTORIAL
        HistorialSalud historial = new HistorialSalud();

        historial.agregarRegistro(registro1);
        historial.agregarRegistro(presion1);

        System.out.println("\n===== HISTORIAL DE MEDICIONES =====");
        System.out.println(historial.mostrarHistorial());

        System.out.println("Cantidad de registros: "
                + historial.cantidadRegistros());

        // PRUEBA 7: BUSCAR UN REGISTRO
        System.out.println("\n===== BUSCAR REGISTRO =====");

        RegistroSalud encontrado = historial.buscarRegistro(2);

        if (encontrado != null) {
            System.out.println("Registro encontrado:");
            System.out.println(encontrado.mostrarRegistro());
        } else {
            System.out.println("No se encontro el registro.");
        }

        // PRUEBA 8: MANEJO DE EXCEPCIONES
        System.out.println("\n===== PRUEBA DE EXCEPCIONES =====");

        try {
            historial.agregarRegistro(presion1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        // PRUEBA 9: ELIMINAR UN REGISTRO
        System.out.println("\n===== ELIMINAR REGISTRO =====");

        boolean eliminado = historial.eliminarRegistro(1);

        System.out.println("Registro eliminado: " + eliminado);
        System.out.println("Cantidad actual: "
                + historial.cantidadRegistros());

        // Mostrar el historial actualizado
        System.out.println("\n===== HISTORIAL ACTUALIZADO =====");
        System.out.println(historial.mostrarHistorial());

        System.out.println("===== FIN DE LAS PRUEBAS =====");
    }
}
