// En project1_parque.menuAdmin.MenuAdminFrame.java
package project1_parque.menuAdmin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;

import sistema_parque.sisParque.PrincipalParque; // Asegúrate de importar PrincipalParque
import sistema_parque.usuarios.Administrador; // Opcional, si necesitas acceso específico al objeto Administrador

public class MenuAdminFrame extends JFrame {

    private PrincipalParque parquePrincipal;
    private String nombreAdministrador; // O podrías pasar directamente el objeto Administrador

    public MenuAdminFrame(String nombreAdmin, PrincipalParque parque) {
        this.nombreAdministrador = nombreAdmin;
        this.parquePrincipal = parque;

        setTitle("Menú de Administración - " + nombreAdmin);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar esta, cierra la app
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel mensajeBienvenida = new JLabel("Bienvenido, Administrador " + nombreAdmin + "!");
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 28));
        mensajeBienvenida.setHorizontalAlignment(JLabel.CENTER);

        panelPrincipal.add(mensajeBienvenida, BorderLayout.NORTH);

        // Aquí irían tus paneles de gestión:
        // GestionAtraccionesPanel, GestionEmpleadosPanel, ReportesPanel, etc.
        // Y los botones para navegar entre ellos.
        // Ejemplo (pseudo-código):
        // JPanel contentPanel = new JPanel(new CardLayout());
        // contentPanel.add(new GestionAtraccionesPanel(parquePrincipal), "Atracciones");
        // contentPanel.add(new GestionEmpleadosPanel(parquePrincipal), "Empleados");
        // panelPrincipal.add(contentPanel, BorderLayout.CENTER);

        add(panelPrincipal);
        // setVisible(true); // Ya se llama desde LoginFrame
    }

    // Aquí podrías añadir un método para guardar los cambios en el parque si es necesario al cerrar la ventana
    // O al hacer clic en un botón "Guardar cambios"
    // public void guardarCambiosAlCerrar() {
    //     CentralPersistencia central = new CentralPersistencia();
    //     PersistenciaParque persistencia = central.getPersistenciaParque(CentralPersistencia.JSON);
    //     persistencia.salvarParque("data/parque.json", parquePrincipal);
    //     JOptionPane.showMessageDialog(this, "Cambios guardados.");
    // }
}