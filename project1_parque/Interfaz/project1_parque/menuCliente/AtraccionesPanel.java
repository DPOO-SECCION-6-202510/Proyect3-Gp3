package project1_parque.menuCliente;

import java.util.List;

import javax.swing.*;
import java.awt.*;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.atracciones.Atraccion;

public class AtraccionesPanel extends JPanel {

    private PrincipalParque parquePrincipal;
    private List<Atraccion> atracciones;

    public AtraccionesPanel(PrincipalParque parquePrincipal) {
        this.parquePrincipal = parquePrincipal;
        this.atracciones = parquePrincipal.getListaAtracciones();

        setLayout(new BorderLayout());

        // Panel interno para mostrar atracciones en columna
        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

        for (Atraccion atraccion : atracciones) {
            String nombre = atraccion.getNombre();
            String categoria = atraccion.getClasificacionCategoria();
            int cupoMaximo = atraccion.getCupoMaximo();

            JLabel lbl = new JLabel("🎢 " + nombre + " | Categoría: " + categoria + " | Cupo Máximo: " + cupoMaximo);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 

            panelLista.add(lbl);
        }

        // Scroll vertical
        JScrollPane scrollPane = new JScrollPane(panelLista);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }
}
