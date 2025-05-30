package project1_parque.menuCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import sistema_parque.lugaresServicio.Taquilla;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Usuario;

public class ComprarTiquetesPanel extends JPanel {

    private PrincipalParque parquePrincipal;
    private Usuario cliente;
    private Taquilla taquilla;

    private JComboBox<String> tipoComboBox;
    private JButton buscarBtn;
    private JPanel panelResultados;

    public ComprarTiquetesPanel(PrincipalParque parquePrincipal, Usuario usuarioAutenticado) {
        this.parquePrincipal = parquePrincipal;
        this.cliente = usuarioAutenticado;
        this.taquilla = new Taquilla();

        List<Tiquete> tiquetes = parquePrincipal.getListaTiquetes();
        for (Tiquete tiquete : tiquetes) {
        	taquilla.getListaTiquetesVender().add(tiquete);
        }
        setLayout(new BorderLayout());

        // Panel superior con selector de tipo
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Seleccione el tipo de tiquete:"));

        tipoComboBox = new JComboBox<>(new String[]{"TEMPORADA", "INDIVIDUAL", "FASTPASS"});
        panelBusqueda.add(tipoComboBox);

        buscarBtn = new JButton("Buscar Tiquetes");
        panelBusqueda.add(buscarBtn);

        add(panelBusqueda, BorderLayout.NORTH);

        // Panel de resultados con scroll
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelResultados);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Acción de búsqueda
        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTiquetesFiltrados();
            }
        });
    }

    private void mostrarTiquetesFiltrados() {
        panelResultados.removeAll();

        String tipoSeleccionado = (String) tipoComboBox.getSelectedItem();
        List<Tiquete> tiquetesFiltrados = taquilla.getListaTiquetesVender().stream()
                .filter(t -> t.getTipo().equalsIgnoreCase(tipoSeleccionado))
                .collect(Collectors.toList());

        if (tiquetesFiltrados.isEmpty()) {
            panelResultados.add(new JLabel("No hay tiquetes disponibles de tipo: " + tipoSeleccionado));
        } else {
            for (Tiquete t : tiquetesFiltrados) {
                JLabel info = new JLabel("🎟 ID: " + t.getId() + " | Expira: " + t.getFechaExpiracion());
                info.setFont(new Font("SansSerif", Font.PLAIN, 14));
                info.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                panelResultados.add(info);
            }
        }

        // Refrescar el panel
        panelResultados.revalidate();
        panelResultados.repaint();
    }
}
