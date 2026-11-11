
package martinalvaradoavance2;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos generales de una medicion de salud
    private int idRegistro;
    private LocalDate fecha;
    private LocalTime hora;
    private double peso;
    private String observaciones;

    // Constructor para inicializar un registro de salud
    public RegistroSalud(int idRegistro, LocalDate fecha,
            LocalTime hora, double peso, String observaciones) {

        this.idRegistro = idRegistro;
        this.fecha = fecha;
        this.hora = hora;
        this.peso = peso;
        this.observaciones = observaciones;
    }

    // Metodos para consultar la informacion
    public int getIdRegistro() {
        return idRegistro;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public double getPeso() {
        return peso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    // Metodos para modificar la informacion
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Permite mostrar la informacion completa del registro
    public String mostrarRegistro() {

        return "ID del registro: " + idRegistro
                + "\nFecha: " + fecha
                + "\nHora: " + hora
                + "\nPeso: " + peso + " kg"
                + "\nObservaciones: " + observaciones;
    }

    // Actualiza los datos generales de una medicion
    public void modificarRegistro(LocalDate fecha, LocalTime hora,
            double peso, String observaciones) {

        this.fecha = fecha;
        this.hora = hora;
        this.peso = peso;
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return mostrarRegistro();
    }
}
