package project1_parque.menuCliente;

import sistema_parque.tiquetes.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImprimirFrame extends JFrame {

    private Tiquete tiquete;
    private boolean yaImpreso;
    private ImageIcon imagenFondo;

    public ImprimirFrame(Tiquete tiquete) {
        this.tiquete = tiquete;
        this.yaImpreso = tiquete.isImpreso();

        // Configuración básica del frame
        setTitle("Impresión de Tiquete - " + tiquete.getId());
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Cargar imagen de fondo
        try {
            imagenFondo = new ImageIcon("imagenes/imagenParque.jpg");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de fondo", "Error", JOptionPane.ERROR_MESSAGE);
            imagenFondo = null;
        }

        // Panel principal con imagen de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de contenido (transparente)
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenido.setBackground(new Color(255, 255, 255, 180));

        // Obtener fecha actual para la impresión
        String fechaImpresion = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        // Determinar tipo de tiquete con instanceof
        String tipoTiquete = determinarTipoTiquete();

        // Información del tiquete
        panelContenido.add(crearLineaInfo("No.", tiquete.getId()));
        panelContenido.add(crearLineaInfo("Fecha Expedición", fechaImpresion));
        panelContenido.add(crearLineaInfo("Válido hasta", tiquete.getFechaExpiracion().toString()));
        panelContenido.add(crearLineaInfo("Tipo", tipoTiquete));

        // Separador
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(70, 130, 180));

        // Panel del QR
        JPanel panelQR = new JPanel();
        panelQR.setOpaque(false);
        panelQR.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Generar código QR
        try {
            String qrData = generarDatosQR(tipoTiquete);
            BufferedImage qrImage = generarQRCode(qrData, 200, 200);
            JLabel lblQR = new JLabel(new ImageIcon(qrImage));
            panelQR.add(lblQR);
        } catch (WriterException e) {
            JLabel lblErrorQR = new JLabel("Error generando QR");
            lblErrorQR.setForeground(Color.RED);
            panelQR.add(lblErrorQR);
        }

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnImprimir = crearBotonEstilizado("Imprimir");
        btnImprimir.addActionListener(e -> imprimirTiquete());

        JButton btnCerrar = crearBotonEstilizado("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnImprimir);
        panelBotones.add(btnCerrar);

        // Advertencia si ya fue impreso
        if (yaImpreso) {
            JLabel lblAdvertencia = new JLabel("⚠ Este tiquete ya fue impreso anteriormente");
            lblAdvertencia.setFont(new Font("Arial", Font.ITALIC, 12));
            lblAdvertencia.setForeground(new Color(200, 100, 0));
            lblAdvertencia.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContenido.add(Box.createVerticalStrut(10));
            panelContenido.add(lblAdvertencia);
        }

        // Agregar componentes al panel principal
        panelContenido.add(separador);
        panelContenido.add(panelQR);
        panelContenido.add(Box.createVerticalGlue());
        panelContenido.add(panelBotones);

        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private String determinarTipoTiquete() {
        if (tiquete instanceof TiqueteTemporada) {
            return "TEMPORADA";
        } else if (tiquete instanceof FastPass) {
            return "FASTPASS";
        } else if (tiquete instanceof TiqueteIndividual) {
            return "INDIVIDUAL";
        } else {
            return "DESCONOCIDO";
        }
    }

    private JPanel crearLineaInfo(String etiqueta, String valor) {
        JPanel linea = new JPanel(new BorderLayout());
        linea.setOpaque(false);
        linea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel lblEtiqueta = new JLabel(etiqueta + ":");
        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        lblEtiqueta.setForeground(new Color(70, 130, 180));
        lblEtiqueta.setPreferredSize(new Dimension(100, 20));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValor.setForeground(Color.BLACK);

        linea.add(lblEtiqueta, BorderLayout.WEST);
        linea.add(lblValor, BorderLayout.CENTER);

        return linea;
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(70, 130, 180, 180));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(50, 100, 150, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(70, 130, 180, 180));
            }
        });
        
        return boton;
    }

    private String generarDatosQR(String tipoTiquete) {
        // Formato simple y legible para el QR
        return "Tipo: " + tipoTiquete + "\n" +
               "ID: " + tiquete.getId() + "\n" +
               "Fecha Expedición: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    private BufferedImage generarQRCode(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // Aumentamos el margen para mejor legibilidad
        java.util.Map<com.google.zxing.EncodeHintType, Object> hints = new java.util.EnumMap<>(com.google.zxing.EncodeHintType.class);
        hints.put(com.google.zxing.EncodeHintType.MARGIN, 2);
        
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        
        return image;
    }

    private void imprimirTiquete() {
        if (yaImpreso) {
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "Este tiquete ya fue impreso antes. ¿Desea imprimirlo de nuevo?",
                "Confirmar reimpresión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        // Marcar como impreso
        tiquete.marcarComoImpreso();
        yaImpreso = true;
        
        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(
            this,
            "Tiquete enviado a impresión correctamente",
            "Impresión exitosa",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Actualizar la interfaz si es necesario
        if (!yaImpreso) {
            JLabel lblAdvertencia = new JLabel("⚠ Este tiquete ya fue impreso anteriormente");
            lblAdvertencia.setFont(new Font("Arial", Font.ITALIC, 12));
            lblAdvertencia.setForeground(new Color(200, 100, 0));
            lblAdvertencia.setAlignmentX(Component.CENTER_ALIGNMENT);
         // Obtener el panel de contenido principal
            Container contentPane = getContentPane();
            JPanel mainPanel = (JPanel) contentPane.getComponent(0);

            // Obtener el layout del panel principal (que debería ser BorderLayout)
            BorderLayout layout = (BorderLayout) mainPanel.getLayout();
            JPanel centerPanel = (JPanel) mainPanel.getComponent(1); // Asumiendo que el panel de contenido está en la posición 1

            // Añadir la advertencia al panel correcto
            centerPanel.add(lblAdvertencia, 0); // Añade al principio
            centerPanel.revalidate();
            centerPanel.repaint();
            revalidate();
            repaint();
        }
    }
}