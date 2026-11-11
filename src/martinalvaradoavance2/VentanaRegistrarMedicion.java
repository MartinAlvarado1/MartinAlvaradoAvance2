
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VentanaRegistrarMedicion extends JFrame {

    // Campos del formulario
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtSistolica;
    private JTextField txtDiastolica;
    private JTextField txtFrecuencia;
    private JTextField txtPeso;
    private JTextArea txtObservaciones;

    // Historial del usuario autenticado
    private final HistorialSalud historial;
    private final Runnable volverDashboard;

    // Colores principales
    private final Color colorPrincipal = new Color(22, 141, 154);
    private final Color colorFondo = new Color(234, 246, 248);

    // Constructor
    public VentanaRegistrarMedicion(
            HistorialSalud historial,
            Runnable volverDashboard) {

        this.historial = historial;
        this.volverDashboard = volverDashboard;

        setTitle("FideHealtTrack - Registrar medición");
        setSize(600, 700);
        setMinimumSize(new Dimension(550, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        inicializarComponentes();
    }

    // Construir la interfaz gráfica
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        JPanel formulario = new JPanel();
        formulario.setLayout(
                new BoxLayout(formulario, BoxLayout.Y_AXIS)
        );
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel titulo = new JLabel("Registrar nueva medición");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(colorPrincipal);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Ingrese sus datos de salud"
        );
        subtitulo.setAlignmentX(LEFT_ALIGNMENT);

        formulario.add(titulo);
        formulario.add(Box.createVerticalStrut(8));
        formulario.add(subtitulo);
        formulario.add(Box.createVerticalStrut(25));

        // Fecha y hora actuales
        txtFecha = new JTextField(LocalDate.now().toString());
        txtHora = new JTextField(
                LocalTime.now().withSecond(0).withNano(0).toString()
        );

        // Campos de medición
        txtSistolica = new JTextField();
        txtDiastolica = new JTextField();
        txtFrecuencia = new JTextField();
        txtPeso = new JTextField();

        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        // Agregar campos de fecha y hora
        agregarCampo(formulario, "Fecha (AAAA-MM-DD):", txtFecha);
        agregarCampo(formulario, "Hora (HH:MM):", txtHora);

        // Presión arterial
        JLabel lblPresion = new JLabel("Presión arterial");
        lblPresion.setFont(new Font("Arial", Font.BOLD, 15));
        lblPresion.setAlignmentX(LEFT_ALIGNMENT);

        formulario.add(lblPresion);
        formulario.add(Box.createVerticalStrut(10));

        agregarCampo(formulario, "Sistólica (mmHg):", txtSistolica);
        agregarCampo(formulario, "Diastólica (mmHg):", txtDiastolica);

        // Otros indicadores
        agregarCampo(formulario,
                "Frecuencia cardíaca (lpm):", txtFrecuencia);

        agregarCampo(formulario, "Peso (kg):", txtPeso);

        // Observaciones
        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(lblObservaciones);
        formulario.add(Box.createVerticalStrut(5));

        JScrollPane scrollObservaciones =
                new JScrollPane(txtObservaciones);

        scrollObservaciones.setAlignmentX(LEFT_ALIGNMENT);
        scrollObservaciones.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 90)
        );

        formulario.add(scrollObservaciones);
        formulario.add(Box.createVerticalStrut(25));

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );
        panelBotones.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnGuardar = new JButton("Guardar medición");
        btnGuardar.setBackground(colorPrincipal);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusPainted(false);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        formulario.add(panelBotones);

        JScrollPane scrollFormulario =
                new JScrollPane(formulario);

        scrollFormulario.setBorder(null);
        scrollFormulario.getVerticalScrollBar()
                .setUnitIncrement(16);

        panelPrincipal.add(
                scrollFormulario, BorderLayout.CENTER
        );

        add(panelPrincipal);

        // Eventos
        btnGuardar.addActionListener(e -> guardarMedicion());
        btnCancelar.addActionListener(e -> regresar());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(
                    java.awt.event.WindowEvent e) {
                regresar();
            }
        });
    }

    // Agrega un campo con su etiqueta
    private void agregarCampo(JPanel panel,
            String etiqueta, JTextField campo) {

        JLabel lblCampo = new JLabel(etiqueta);
        lblCampo.setAlignmentX(LEFT_ALIGNMENT);

        campo.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 32)
        );

        campo.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(lblCampo);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(12));
    }

    // Validar y guardar una medición
    private void guardarMedicion() {

        try {

            // Recuperar fecha y hora
            LocalDate fecha = LocalDate.parse(
                    txtFecha.getText().trim()
            );

            LocalTime hora = LocalTime.parse(
                    txtHora.getText().trim()
            );

            // Convertir los valores numéricos
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

            // Validar valores positivos
            if (sistolica <= 0 || diastolica <= 0
                    || frecuencia <= 0 || peso <= 0
                    || !Double.isFinite(peso)) {

                throw new IllegalArgumentException(
                        "Los valores de salud deben ser "
                        + "números positivos y válidos."
                );
            }

            // Determinar el siguiente identificador
            int nuevoId = 1;

            for (RegistroSalud registro :
                    historial.getListaRegistros()) {

                if (registro.getIdRegistro() >= nuevoId) {
                    nuevoId = registro.getIdRegistro() + 1;
                }
            }

            // Crear la medición
            PresionArterial nuevaMedicion =
                    new PresionArterial(
                            nuevoId,
                            fecha,
                            hora,
                            peso,
                            observaciones,
                            sistolica,
                            diastolica,
                            frecuencia
                    );

            // Agregar al historial del usuario
            historial.agregarRegistro(nuevaMedicion);

            JOptionPane.showMessageDialog(
                    this,
                    "Medición registrada correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            regresar();

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
                    "Debe ingresar valores numéricos válidos "
                    + "en los campos de salud.",
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

    // Regresar al dashboard
    private void regresar() {

        dispose();

        if (volverDashboard != null) {
            volverDashboard.run();
        }
    }
}
