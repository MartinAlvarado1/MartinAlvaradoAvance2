
package martinalvaradoavance2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VentanaEditarMedicion extends JDialog {

    // Medición que el usuario desea modificar
    private final PresionArterial medicion;
    private final Runnable alGuardar;

    // Campos del formulario
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtSistolica;
    private JTextField txtDiastolica;
    private JTextField txtFrecuencia;
    private JTextField txtPeso;
    private JTextArea txtObservaciones;

    // Colores de FideHealtTrack
    private final Color colorPrincipal = new Color(22, 141, 154);
    private final Color colorFondo = new Color(234, 246, 248);

    // Constructor
    public VentanaEditarMedicion(JFrame ventanaPadre,
            PresionArterial medicion, Runnable alGuardar) {

        super(ventanaPadre, "FideHealtTrack - Editar medición", true);

        this.medicion = medicion;
        this.alGuardar = alGuardar;

        setSize(550, 690);
        setMinimumSize(new Dimension(480, 570));
        setLocationRelativeTo(ventanaPadre);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    // Construye el formulario de edición
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JPanel formulario = new JPanel(new GridLayout(0, 1, 5, 5));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel titulo = new JLabel("Editar medición");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(colorPrincipal);

        JLabel subtitulo = new JLabel(
                "Registro número " + medicion.getIdRegistro()
        );

        // Cargar los datos actuales de la medición
        txtFecha = new JTextField(medicion.getFecha().toString());
        txtHora = new JTextField(medicion.getHora().toString());

        txtSistolica = new JTextField(
                String.valueOf(medicion.getSistolica())
        );

        txtDiastolica = new JTextField(
                String.valueOf(medicion.getDiastolica())
        );

        txtFrecuencia = new JTextField(
                String.valueOf(medicion.getFrecuenciaCardiaca())
        );

        txtPeso = new JTextField(
                String.valueOf(medicion.getPeso())
        );

        txtObservaciones = new JTextArea(
                medicion.getObservaciones(), 3, 20
        );

        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        // Agregar los campos al formulario
        formulario.add(titulo);
        formulario.add(subtitulo);

        formulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        formulario.add(txtFecha);

        formulario.add(new JLabel("Hora (HH:MM):"));
        formulario.add(txtHora);

        formulario.add(new JLabel("Presión sistólica (mmHg):"));
        formulario.add(txtSistolica);

        formulario.add(new JLabel("Presión diastólica (mmHg):"));
        formulario.add(txtDiastolica);

        formulario.add(new JLabel("Frecuencia cardíaca (lpm):"));
        formulario.add(txtFrecuencia);

        formulario.add(new JLabel("Peso (kg):"));
        formulario.add(txtPeso);

        formulario.add(new JLabel("Observaciones:"));
        formulario.add(new JScrollPane(txtObservaciones));

        JScrollPane scrollFormulario = new JScrollPane(formulario);
        scrollFormulario.setBorder(null);
        scrollFormulario.getVerticalScrollBar().setUnitIncrement(16);

        panelPrincipal.add(scrollFormulario, BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotones.setBackground(colorFondo);

        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setBackground(colorPrincipal);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusPainted(false);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0)
        );

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        // Eventos
        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> dispose());
    }

    // Valida y actualiza los datos del registro
    private void guardarCambios() {

        try {

            LocalDate fecha = LocalDate.parse(
                    txtFecha.getText().trim()
            );

            LocalTime hora = LocalTime.parse(
                    txtHora.getText().trim()
            );

            int sistolica = Integer.parseInt(
                    txtSistolica.getText().trim()
            );

            int diastolica = Integer.parseInt(
                    txtDiastolica.getText().trim()
            );

            int frecuencia = Integer.parseInt(
                    txtFrecuencia.getText().trim()
            );

            double peso = Double.parseDouble(
                    txtPeso.getText().trim().replace(",", ".")
            );

            String observaciones =
                    txtObservaciones.getText().trim();

            // Validar los valores numéricos
            if (sistolica <= 0 || diastolica <= 0
                    || frecuencia <= 0 || peso <= 0
                    || !Double.isFinite(peso)) {

                throw new IllegalArgumentException(
                        "Los valores deben ser números positivos y válidos."
                );
            }

            // Actualizar los atributos generales heredados
            medicion.modificarRegistro(
                    fecha, hora, peso, observaciones
            );

            // Actualizar los atributos de presión arterial
            medicion.registrarPresion(
                    sistolica, diastolica, frecuencia
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Medición actualizada correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Notificar al historial que debe actualizar la tabla
            if (alGuardar != null) {
                alGuardar.run();
            }

            dispose();

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha u hora no tiene un formato válido.\n"
                    + "Fecha: AAAA-MM-DD\n"
                    + "Hora: HH:MM",
                    "Error de fecha u hora",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese valores numéricos válidos.",
                    "Error de datos",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

