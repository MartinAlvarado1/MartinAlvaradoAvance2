
package martinalvaradoavance2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorial extends JFrame {

    private final HistorialSalud historial;
    private final Runnable volverDashboard;

    private JTable tablaMediciones;
    private DefaultTableModel modeloTabla;

    private final Color colorPrincipal = new Color(22, 141, 154);
    private final Color colorFondo = new Color(234, 246, 248);

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final DateTimeFormatter formatoHora =
            DateTimeFormatter.ofPattern("HH:mm");

    // Constructor
    public VentanaHistorial(HistorialSalud historial,
            Runnable volverDashboard) {

        this.historial = historial;
        this.volverDashboard = volverDashboard;

        setTitle("FideHealtTrack - Historial de salud");
        setSize(1000, 570);
        setMinimumSize(new Dimension(850, 470));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        inicializarComponentes();
        cargarMediciones();
    }

    // Construir la ventana del historial
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 20));
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel lblTitulo = new JLabel("Historial de mediciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        lblTitulo.setForeground(colorPrincipal);

        JLabel lblSubtitulo = new JLabel(
                "Consulte y administre sus mediciones"
        );

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 0, 5));
        encabezado.setOpaque(false);
        encabezado.add(lblTitulo);
        encabezado.add(lblSubtitulo);

        panelPrincipal.add(encabezado, BorderLayout.NORTH);

        // Modelo de la tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{
                    "ID", "Fecha", "Hora",
                    "Presión", "Frecuencia", "Peso"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaMediciones = new JTable(modeloTabla);
        tablaMediciones.setRowHeight(30);
        tablaMediciones.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaMediciones.setAutoCreateRowSorter(true);
        tablaMediciones.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tablaMediciones);
        scrollTabla.setBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 225, 230)
                )
        );

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Botones de administración
        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnVerDetalle = new JButton("Ver detalle");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnVolver = new JButton("Volver al inicio");

        btnVerDetalle.setBackground(colorPrincipal);
        btnVerDetalle.setForeground(Color.WHITE);

        btnEditar.setBackground(colorPrincipal);
        btnEditar.setForeground(Color.WHITE);

        btnEliminar.setBackground(new Color(190, 65, 65));
        btnEliminar.setForeground(Color.WHITE);

        btnVerDetalle.setFocusPainted(false);
        btnEditar.setFocusPainted(false);
        btnEliminar.setFocusPainted(false);
        btnActualizar.setFocusPainted(false);
        btnVolver.setFocusPainted(false);

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Eventos
        btnVerDetalle.addActionListener(e -> verDetalle());
        btnEditar.addActionListener(e -> editarMedicion());
        btnEliminar.addActionListener(e -> eliminarMedicion());
        btnActualizar.addActionListener(e -> cargarMediciones());
        btnVolver.addActionListener(e -> regresar());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(
                    java.awt.event.WindowEvent e) {
                regresar();
            }
        });
    }

    // Cargar las mediciones a la tabla
    private void cargarMediciones() {

        modeloTabla.setRowCount(0);

        for (RegistroSalud registro :
                historial.getListaRegistros()) {

            String presion = "-";
            String frecuencia = "-";

            if (registro instanceof PresionArterial) {

                PresionArterial medicion =
                        (PresionArterial) registro;

                presion = medicion.getSistolica()
                        + "/" + medicion.getDiastolica();

                frecuencia = String.valueOf(
                        medicion.getFrecuenciaCardiaca()
                );
            }

            modeloTabla.addRow(new Object[]{
                registro.getIdRegistro(),
                registro.getFecha().format(formatoFecha),
                registro.getHora().format(formatoHora),
                presion,
                frecuencia,
                registro.getPeso() + " kg"
            });
        }
    }

    // Obtener el ID seleccionado de la tabla
    private int obtenerIdSeleccionado() {

        int fila = tablaMediciones.getSelectedRow();

        if (fila == -1) {
            return -1;
        }

        int filaModelo =
                tablaMediciones.convertRowIndexToModel(fila);

        return (int) modeloTabla.getValueAt(filaModelo, 0);
    }

    // Consultar detalle de una medición
    private void verDetalle() {

        int id = obtenerIdSeleccionado();

        if (id == -1) {
            mostrarSeleccionObligatoria();
            return;
        }

        RegistroSalud registro = historial.buscarRegistro(id);

        if (registro != null) {

            JOptionPane.showMessageDialog(
                    this,
                    registro.mostrarRegistro(),
                    "Detalle de la medición",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Abrir la ventana para modificar una medición
    private void editarMedicion() {

        int id = obtenerIdSeleccionado();

        if (id == -1) {
            mostrarSeleccionObligatoria();
            return;
        }

        RegistroSalud registro = historial.buscarRegistro(id);

        if (registro instanceof PresionArterial) {

            PresionArterial medicion = (PresionArterial) registro;

            VentanaEditarMedicion ventanaEditar =
                    new VentanaEditarMedicion(
                            this,
                            medicion,
                            () -> cargarMediciones()
                    );

            ventanaEditar.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Solo se pueden editar mediciones de presión arterial.",
                    "Edición no disponible",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // Eliminar una medición
    private void eliminarMedicion() {

        int id = obtenerIdSeleccionado();

        if (id == -1) {
            mostrarSeleccionObligatoria();
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar la medición " + id + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            boolean eliminado = historial.eliminarRegistro(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Medición eliminada correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );

                cargarMediciones();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró la medición.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Advertencia cuando no hay una fila seleccionada
    private void mostrarSeleccionObligatoria() {

        JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una medición de la tabla.",
                "Seleccione una medición",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // Regresar al dashboard
    private void regresar() {

        dispose();

        if (volverDashboard != null) {
            volverDashboard.run();
        }
    }
}
