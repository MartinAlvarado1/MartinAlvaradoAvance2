
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

public class VentanaCrearCuenta extends JFrame {

    // Componentes del formulario
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;

    private JButton btnCrearCuenta;
    private JButton btnVolver;

    // Gestor compartido entre las ventanas
    private final GestorUsuarios gestorUsuarios;
    private final Runnable volverAlLogin;

    // Colores de FideHealtTrack
    private final Color colorPrincipal = new Color(22, 141, 154);
    private final Color colorFondo = new Color(234, 246, 248);

    // Constructor
    public VentanaCrearCuenta(GestorUsuarios gestorUsuarios,
            Runnable volverAlLogin) {

        this.gestorUsuarios = gestorUsuarios;
        this.volverAlLogin = volverAlLogin;

        setTitle("FideHealtTrack - Crear cuenta");
        setSize(500, 650);
        setMinimumSize(new Dimension(440, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    // Construye la interfaz gráfica
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(30, 45, 30, 45)
        );

        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setLayout(
                new BoxLayout(panelFormulario, BoxLayout.Y_AXIS)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Título de la ventana
        JLabel lblTitulo = new JLabel(
                "Crear cuenta", SwingConstants.CENTER
        );

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 27));
        lblTitulo.setForeground(colorPrincipal);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel(
                "Únete a FideHealtTrack"
        );

        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        // Crear los campos
        txtNombre = crearCampoTexto();
        txtCorreo = crearCampoTexto();

        txtContrasena = crearCampoContrasena();
        txtConfirmarContrasena = crearCampoContrasena();

        // Crear botones
        btnCrearCuenta = new JButton("Crear cuenta");
        btnCrearCuenta.setBackground(colorPrincipal);
        btnCrearCuenta.setForeground(Color.WHITE);
        btnCrearCuenta.setFocusPainted(false);
        btnCrearCuenta.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        btnCrearCuenta.setAlignmentX(CENTER_ALIGNMENT);
        btnCrearCuenta.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        btnVolver = new JButton("Volver al inicio de sesión");
        btnVolver.setForeground(colorPrincipal);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Agregar componentes
        panelFormulario.add(lblTitulo);
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(lblSubtitulo);
        panelFormulario.add(Box.createVerticalStrut(25));

        agregarCampo(panelFormulario, "Nombre completo:", txtNombre);
        agregarCampo(panelFormulario, "Correo electrónico:", txtCorreo);
        agregarCampo(panelFormulario, "Contraseña:", txtContrasena);
        agregarCampo(panelFormulario, "Confirmar contraseña:",
                txtConfirmarContrasena);

        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(btnCrearCuenta);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(btnVolver);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        add(panelPrincipal);

        // Eventos de los botones
        btnCrearCuenta.addActionListener(e -> registrarCuenta());
        btnVolver.addActionListener(e -> regresarAlLogin());
    }

    // Configura un campo de texto
    private JTextField crearCampoTexto() {

        JTextField campo = new JTextField();
        campo.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35)
        );

        return campo;
    }

    // Configura un campo de contraseña
    private JPasswordField crearCampoContrasena() {

        JPasswordField campo = new JPasswordField();
        campo.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35)
        );

        return campo;
    }

    // Agrega la etiqueta y su campo al formulario
    private void agregarCampo(JPanel panel, String etiqueta,
            javax.swing.JComponent campo) {

        JLabel lblCampo = new JLabel(etiqueta);

        panel.add(lblCampo);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(15));
    }

    // Valida y registra una nueva cuenta
    private void registrarCuenta() {

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();

        char[] clave = txtContrasena.getPassword();
        char[] confirmarClave = txtConfirmarContrasena.getPassword();

        try {

            if (!Arrays.equals(clave, confirmarClave)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Las contraseñas no coinciden.",
                        "Error de validación",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Registrar el usuario en el gestor
            gestorUsuarios.registrarUsuario(
                    nombre,
                    correo,
                    new String(clave),
                    null
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Cuenta creada correctamente.\n"
                    + "Ahora puede iniciar sesión.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            regresarAlLogin();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error al crear cuenta",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            // Limpiar los datos temporales de contraseña
            Arrays.fill(clave, '\0');
            Arrays.fill(confirmarClave, '\0');

            txtContrasena.setText("");
            txtConfirmarContrasena.setText("");
        }
    }

    // Regresa a la ventana de inicio de sesión
    private void regresarAlLogin() {

        dispose();

        if (volverAlLogin != null) {
            volverAlLogin.run();
        }
    }
}
