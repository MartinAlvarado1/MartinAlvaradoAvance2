
package martinalvaradoavance2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {

    // Componentes de la ventana
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnCrearCuenta;

    // Gestor compartido con la ventana de registro
    private final GestorUsuarios gestorUsuarios;

    // Colores principales
    private final Color colorPrincipal = new Color(22, 141, 154);
    private final Color colorFondo = new Color(234, 246, 248);

    // Constructor
    public VentanaLogin(GestorUsuarios gestorUsuarios) {

        this.gestorUsuarios = gestorUsuarios;

        setTitle("FideHealtTrack - Inicio de sesión");
        setSize(480, 520);
        setMinimumSize(new Dimension(420, 480));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
    }

    // Construye los componentes gráficos
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(35, 45, 35, 45)
        );

        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setLayout(
                new BoxLayout(panelFormulario, BoxLayout.Y_AXIS)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Título
        JLabel lblTitulo = new JLabel(
                "FideHealtTrack", SwingConstants.CENTER
        );
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 27));
        lblTitulo.setForeground(colorPrincipal);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel(
                "Seguimiento personal de salud"
        );
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        // Campos
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        JLabel lblContrasena = new JLabel("Contraseña:");

        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();

        txtCorreo.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35)
        );
        txtContrasena.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35)
        );

        // Botón de inicio de sesión
        btnIniciarSesion = new JButton("Iniciar sesión");
        btnIniciarSesion.setBackground(colorPrincipal);
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        btnIniciarSesion.setAlignmentX(CENTER_ALIGNMENT);
        btnIniciarSesion.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        // Botón para crear cuenta
        btnCrearCuenta = new JButton("Crear cuenta");
        btnCrearCuenta.setForeground(colorPrincipal);
        btnCrearCuenta.setFocusPainted(false);
        btnCrearCuenta.setContentAreaFilled(false);
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setAlignmentX(CENTER_ALIGNMENT);
        btnCrearCuenta.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Agregar componentes al formulario
        panelFormulario.add(lblTitulo);
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(lblSubtitulo);
        panelFormulario.add(Box.createVerticalStrut(35));

        panelFormulario.add(lblCorreo);
        panelFormulario.add(Box.createVerticalStrut(6));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(Box.createVerticalStrut(20));

        panelFormulario.add(lblContrasena);
        panelFormulario.add(Box.createVerticalStrut(6));
        panelFormulario.add(txtContrasena);
        panelFormulario.add(Box.createVerticalStrut(25));

        panelFormulario.add(btnIniciarSesion);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(btnCrearCuenta);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        add(panelPrincipal);

        // Eventos
        btnIniciarSesion.addActionListener(e -> iniciarSesion());

        txtContrasena.addActionListener(e -> iniciarSesion());

        btnCrearCuenta.addActionListener(e -> abrirCrearCuenta());
    }

    // Valida las credenciales ingresadas
    private void iniciarSesion() {

        String correo = txtCorreo.getText().trim();
        char[] claveIngresada = txtContrasena.getPassword();

        try {

            if (correo.isEmpty() || claveIngresada.length == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar su correo y contraseña.",
                        "Campos obligatorios",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Usuario usuario = gestorUsuarios.iniciarSesion(
                    correo, new String(claveIngresada)
            );

            if (usuario != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido a FideHealtTrack, "
                        + usuario.getNombre() + ".",
                        "Inicio de sesión exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // El dashboard se implementará después

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Correo o contraseña incorrectos.",
                        "Error de autenticación",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } finally {

            Arrays.fill(claveIngresada, '\0');
            txtContrasena.setText("");
        }
    }

    // Abre la ventana para registrar una cuenta
    private void abrirCrearCuenta() {

        VentanaCrearCuenta ventanaRegistro =
                new VentanaCrearCuenta(
                        gestorUsuarios,
                        () -> setVisible(true)
                );

        setVisible(false);
        ventanaRegistro.setVisible(true);
    }
}
