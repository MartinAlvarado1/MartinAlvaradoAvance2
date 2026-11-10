
package martinalvaradoavance2;

import java.io.IOException;
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

        // PRUEBA 5: HERENCIA
        System.out.println("\n===== PRUEBA DE HERENCIA =====");
        System.out.println("Fecha heredada: " + presion1.getFecha());
        System.out.println("Peso heredado: " + presion1.getPeso() + " kg");
        System.out.println("Presion sistolica: " + presion1.getSistolica());

        // PRUEBA 6: POLIMORFISMO
        System.out.println("\n===== PRUEBA DE POLIMORFISMO =====");

        RegistroSalud registroPolimorfico = presion1;
        System.out.println(registroPolimorfico.mostrarRegistro());

        // PRUEBA 7: HISTORIAL DE SALUD
        HistorialSalud historial = new HistorialSalud();

        historial.agregarRegistro(registro1);
        historial.agregarRegistro(presion1);

        System.out.println("\n===== HISTORIAL DE MEDICIONES =====");
        System.out.println(historial.mostrarHistorial());

        System.out.println("Cantidad de registros: "
                + historial.cantidadRegistros());

        // PRUEBA 8: BUSCAR UN REGISTRO
        System.out.println("\n===== BUSCAR REGISTRO =====");

        RegistroSalud encontrado = historial.buscarRegistro(2);

        if (encontrado != null) {
            System.out.println("Registro encontrado:");
            System.out.println(encontrado.mostrarRegistro());
        } else {
            System.out.println("No se encontro el registro.");
        }

        // PRUEBA 9: MANEJO DE EXCEPCIONES
        System.out.println("\n===== PRUEBA DE EXCEPCIONES =====");

        try {
            historial.agregarRegistro(presion1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        // PRUEBA 10: ELIMINAR UN REGISTRO
        System.out.println("\n===== ELIMINAR REGISTRO =====");

        boolean eliminado = historial.eliminarRegistro(1);

        System.out.println("Registro eliminado: " + eliminado);
        System.out.println("Cantidad actual: "
                + historial.cantidadRegistros());

        // PRUEBA 11: SERIALIZACION
        System.out.println("\n===== PRUEBA DE SERIALIZACION =====");

        // Archivo local para guardar las mediciones
        RepositorioSalud repositorio =
                new RepositorioSalud("historial.dat");

        try {

            // Guardar el historial en el archivo
            repositorio.guardarHistorial(historial);

            System.out.println("Historial guardado correctamente.");

            // Recuperar el historial desde el archivo
            HistorialSalud historialRecuperado =
                    repositorio.cargarHistorial();

            System.out.println("\nHistorial recuperado desde el archivo:");
            System.out.println(historialRecuperado.mostrarHistorial());

            System.out.println("Cantidad de registros recuperados: "
                    + historialRecuperado.cantidadRegistros());

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error al guardar o recuperar el historial: "
                    + e.getMessage());
        }

        System.out.println("\n===== FIN DE LAS PRUEBAS =====");
    }
}
