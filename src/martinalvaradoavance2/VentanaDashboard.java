
package martinalvaradoavance2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaDashboard extends JFrame {

    // Información del usuario autenticado
    private final Usuario usuario;
    private final HistorialSalud historial;
    private final Runnable cerrarSesion;

    // Panel central del dashboard
    private JPanel panelContenido;

    // Colores principales
    private final Color colorPrincipal = new Color(22, 141, 154);
    private final Color colorFondo = new Color(234, 246, 248);

    // Constructor
    public VentanaDashboard(Usuario usuario,
            HistorialSalud historial, Runnable cerrarSesion) {

        this.usuario = usuario;
        this.historial = historial;
        this.cerrarSesion = cerrarSesion;

        setTitle("FideHealtTrack - Inicio");
        setSize(1000, 670);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
    }

    // Construye la ventana principal
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        // Encabezado
        JLabel lblTitulo = new JLabel("FideHealtTrack");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        lblTitulo.setForeground(colorPrincipal);

        JLabel lblUsuario = new JLabel(
                "Bienvenido, " + usuario.getNombre()
        );

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        encabezado.setBorder(
                BorderFactory.createEmptyBorder(18, 25, 18, 25)
        );

        encabezado.add(lblTitulo, BorderLayout.WEST);
        encabezado.add(lblUsuario, BorderLayout.EAST);

        panelPrincipal.add(encabezado, BorderLayout.NORTH);

        // Menú lateral
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(Color.WHITE);
        menu.setPreferredSize(new Dimension(210, 0));
        menu.setBorder(
                BorderFactory.createEmptyBorder(25, 15, 20, 15)
        );

        JButton btnInicio = crearBoton("Inicio");
        JButton btnNuevaMedicion = crearBoton("Nueva medición");
        JButton btnHistorial = crearBoton("Historial");
        JButton btnCerrarSesion = crearBoton("Cerrar sesión");

        menu.add(btnInicio);
        menu.add(Box.createVerticalStrut(15));
        menu.add(btnNuevaMedicion);
        menu.add(Box.createVerticalStrut(15));
        menu.add(btnHistorial);
        menu.add(Box.createVerticalGlue());
        menu.add(btnCerrarSesion);

        panelPrincipal.add(menu, BorderLayout.WEST);

        // Panel central
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(colorFondo);

        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        add(panelPrincipal);

        // Mostrar la información inicial
        actualizarDashboard();

        // Eventos del menú
        btnInicio.addActionListener(e -> actualizarDashboard());

        btnNuevaMedicion.addActionListener(
                e -> abrirNuevaMedicion()
        );

        btnHistorial.addActionListener(
                e -> abrirHistorial()
        );

        btnCerrarSesion.addActionListener(e -> {

            dispose();

            if (cerrarSesion != null) {
                cerrarSesion.run();
            }
        });
    }

    // Actualiza los indicadores de la última medición
    private void actualizarDashboard() {

        panelContenido.removeAll();

        JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBackground(colorFondo);
        contenido.setBorder(
                BorderFactory.createEmptyBorder(35, 30, 30, 30)
        );

        JLabel lblUltimaMedicion =
                new JLabel("Última medición");

        lblUltimaMedicion.setFont(
                new Font("Arial", Font.BOLD, 21)
        );

        contenido.add(lblUltimaMedicion, BorderLayout.NORTH);

        PresionArterial ultima = obtenerUltimaMedicion();

        if (ultima != null) {

            JPanel tarjetas = new JPanel(
                    new GridLayout(1, 3, 15, 0)
            );

            tarjetas.setOpaque(false);

            tarjetas.add(crearTarjeta(
                    "Presión arterial",
                    ultima.getSistolica() + "/"
                    + ultima.getDiastolica(),
                    "mmHg"
            ));

            tarjetas.add(crearTarjeta(
                    "Frecuencia cardíaca",
                    String.valueOf(
                            ultima.getFrecuenciaCardiaca()
                    ),
                    "lpm"
            ));

            tarjetas.add(crearTarjeta(
                    "Peso",
                    String.valueOf(ultima.getPeso()),
                    "kg"
            ));

            contenido.add(tarjetas, BorderLayout.CENTER);

            JLabel lblFecha = new JLabel(
                    "Fecha: " + ultima.getFecha()
                    + "     Hora: " + ultima.getHora()
            );

            lblFecha.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            contenido.add(lblFecha, BorderLayout.SOUTH);

        } else {

            JLabel lblSinDatos = new JLabel(
                    "Todavía no hay mediciones registradas.",
                    SwingConstants.CENTER
            );

            lblSinDatos.setFont(
                    new Font("Arial", Font.PLAIN, 17)
            );

            contenido.add(lblSinDatos, BorderLayout.CENTER);
        }

        panelContenido.add(contenido, BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // Abre la ventana para registrar mediciones
    private void abrirNuevaMedicion() {

        VentanaRegistrarMedicion ventanaMedicion =
                new VentanaRegistrarMedicion(
                        historial,
                        () -> {
                            actualizarDashboard();
                            setVisible(true);
                        }
                );

        setVisible(false);
        ventanaMedicion.setVisible(true);
    }

    // Abre la ventana del historial de salud
    private void abrirHistorial() {

        VentanaHistorial ventanaHistorial =
                new VentanaHistorial(
                        historial,
                        () -> {
                            actualizarDashboard();
                            setVisible(true);
                        }
                );

        setVisible(false);
        ventanaHistorial.setVisible(true);
    }

    // Crea botones del menú lateral
    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto);

        boton.setBackground(colorPrincipal);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);

        boton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        return boton;
    }

    // Crea una tarjeta para mostrar indicadores
    private JPanel crearTarjeta(String titulo,
            String valor, String unidad) {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(tarjeta, BoxLayout.Y_AXIS)
        );

        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(
                BorderFactory.createEmptyBorder(25, 10, 25, 10)
        );

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(
                new Font("Arial", Font.BOLD, 27)
        );
        lblValor.setForeground(colorPrincipal);
        lblValor.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblUnidad = new JLabel(unidad);
        lblUnidad.setAlignmentX(CENTER_ALIGNMENT);

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(lblValor);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblUnidad);
        tarjeta.add(Box.createVerticalGlue());

        return tarjeta;
    }

    // Obtiene la última medición de presión arterial
    private PresionArterial obtenerUltimaMedicion() {

        PresionArterial ultima = null;

        for (RegistroSalud registro :
                historial.getListaRegistros()) {

            if (registro instanceof PresionArterial) {

                PresionArterial actual =
                        (PresionArterial) registro;

                if (ultima == null
                        || actual.getFecha().isAfter(
                                ultima.getFecha())
                        || (actual.getFecha().equals(
                                ultima.getFecha())
                        && actual.getHora().isAfter(
                                ultima.getHora()))) {

                    ultima = actual;
                }
            }
        }

        return ultima;
    }
}
