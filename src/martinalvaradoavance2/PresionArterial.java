
package martinalvaradoavance2;

import java.time.LocalDate;
import java.time.LocalTime;

public class PresionArterial extends RegistroSalud {

    // Atributos específicos de la presión arterial
    private int sistolica;
    private int diastolica;
    private int frecuenciaCardiaca;

    // Constructor de la clase PresionArterial
    public PresionArterial(int idRegistro, LocalDate fecha,
            LocalTime hora, double peso, String observaciones,
            int sistolica, int diastolica, int frecuenciaCardiaca) {

        // Inicializa los atributos heredados de RegistroSalud
        super(idRegistro, fecha, hora, peso, observaciones);

        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    // Métodos para consultar los atributos
    public int getSistolica() {
        return sistolica;
    }

    public int getDiastolica() {
        return diastolica;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    // Métodos para modificar los atributos
    public void setSistolica(int sistolica) {
        this.sistolica = sistolica;
    }

    public void setDiastolica(int diastolica) {
        this.diastolica = diastolica;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    // Actualiza los valores específicos de la medición
    public void registrarPresion(int sistolica, int diastolica,
            int frecuenciaCardiaca) {

        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    // Sobrescribe el método heredado para mostrar más información
    @Override
    public String mostrarRegistro() {

        return super.mostrarRegistro()
                + "\nPresión sistólica: " + sistolica + " mmHg"
                + "\nPresión diastólica: " + diastolica + " mmHg"
                + "\nFrecuencia cardíaca: " + frecuenciaCardiaca + " lpm";
    }

    // Muestra únicamente los valores de presión arterial
    public String mostrarPresion() {

        return "Presión arterial: " + sistolica + "/"
                + diastolica + " mmHg"
                + "\nFrecuencia cardíaca: "
                + frecuenciaCardiaca + " lpm";
    }
}
