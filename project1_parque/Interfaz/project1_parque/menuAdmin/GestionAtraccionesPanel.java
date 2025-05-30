package project1_parque.menuAdmin;

import javax.swing.*;
import java.awt.*;
import sistema_parque.sisParque.PrincipalParque;
// Importa las clases necesarias para Atracciones (Atraccion, TipoAtraccion, etc.)
// import sistema_parque.atracciones.Atraccion;

public class GestionAtraccionesPanel extends JPanel {

    private PrincipalParque parquePrincipal;

    public GestionAtraccionesPanel(PrincipalParque parquePrincipal) {
        this.parquePrincipal = parquePrincipal;
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Atracciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Aquí iría toda la lógica y componentes para:
        // - Añadir nuevas atracciones (formulario)
        // - Modificar atracciones existentes (tabla con opciones de edición)
        // - Eliminar atracciones
        // - Mostrar detalles de atracciones
        // Necesitarás JTable, JTextFields, JButtons, etc.

        JPanel contentPanel = new JPanel(new GridLayout(0, 1)); // Panel para los controles de gestión
        contentPanel.add(new JLabel("Aquí irán los formularios y tablas para gestionar atracciones."));
        // Ejemplo de cómo acceder a los datos del parque:
        // if (parquePrincipal != null && parquePrincipal.getListaAtracciones() != null) {
        //     contentPanel.add(new JLabel("Número de atracciones actuales: " + parquePrincipal.getListaAtracciones().size()));
        // }
        
        add(contentPanel, BorderLayout.CENTER);
    }

    // Métodos para actualizar la UI con los datos del parque si cambian
    public void actualizarDatos() {
        // Lógica para recargar la tabla de atracciones o los formularios
        // Esto podría ser llamado desde MenuAdminFrame si un panel es modificado y necesita ser refrescado
    }
}