/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import system_tray.Tray;
import vista.mesajes.funcionesAnimaciones;

public class frm_principal extends javax.swing.JFrame {

    Tray ocultaTray = new Tray(this);
    public static final String CONFIG = "config.properties";
    int x, y;
    int puerto = 8000;

    public frm_principal() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("logoCircle.png")).getImage());
        this.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        mostrar();
        generar_codigo();
        
    }

    private void mostrar() {
        try (InputStream lecturaE = new FileInputStream(CONFIG)) {
            Properties propiedadesE = new Properties();
            propiedadesE.load(lecturaE);
            //leer propiedades de la configuracion
            txt_puerto.setText(propiedadesE.getProperty("puerto"));
            puerto = Integer.parseInt(propiedadesE.getProperty("puerto"));
        } catch (IOException e) {
            txt_puerto.setText("8000");
            puerto = 8000;
        }
    }

    private void guardar_configuracion() {
        if (!txt_puerto.getText().isEmpty()) {
            try (OutputStream salida = new FileOutputStream(CONFIG)) {
                Properties propiedades = new Properties();

                propiedades.setProperty("puerto", txt_puerto.getText());

                propiedades.store(salida, null);
                //  System.out.println(propiedades);

                funcionesAnimaciones.mostrarMensajeDialogo("menje", "la configuracion se guardo con exito");

                funcionesAnimaciones.mostrarMensajeDialogo("menje", "habilita el puerto ingresado en el firewall"); //
                funcionesAnimaciones.mostrarMensajeDialogo("menje", "vuelva a abrir el programa");
                System.exit(0);

            } catch (IOException e) {

                JOptionPane.showConfirmDialog(null, "ocurrio un error:\n " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "complete los campos");
        }
    }

    public void generar_codigo() {
        try {
            int size = 1000;
            String FileType = "png";

            String codigo = lb_ip_servidor.getText().trim() + "|" + puerto;
            // Elegir la ruta de la imagen

            // Generar el nombre
            // UUID uuid = UUID.randomUUID();
            String randonName = "qr";//uuid.toString();

            // Generar el QR 
            QRCodeWriter qrcode = new QRCodeWriter();
            try {
                BitMatrix matrix = qrcode.encode(codigo, BarcodeFormat.QR_CODE, size, size);
                File f = new File(randonName + "." + FileType);
                int matrixWidth = matrix.getWidth();
                BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
                image.createGraphics();

                Graphics2D gd = (Graphics2D) image.getGraphics();
                gd.setColor(Color.WHITE); // Fondo
                gd.fillRect(0, 0, size, size);
                gd.setColor(Color.black); // Qr

                for (int b = 0; b < matrixWidth; b++) {
                    for (int j = 0; j < matrixWidth; j++) {
                        if (matrix.get(b, j)) {
                            gd.fillRect(b, j, 1, 1);
                        }
                    }
                }

                // Mostrar la imagen generada
                ImageIO.write(image, FileType, f);
                Image mImagen = new ImageIcon(randonName + "." + FileType).getImage();
                ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(lb_codigo.getWidth(), lb_codigo.getHeight(), 0));
                lb_codigo.setIcon(mIcono);

            } catch (WriterException ex) {
                Logger.getLogger(frm_principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(frm_principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
        }
    }

   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbl_nombre_negocio2 = new javax.swing.JLabel();
        lcodigo17 = new javax.swing.JLabel();
        lb_ip_servidor = new javax.swing.JLabel();
        lcodigo18 = new javax.swing.JLabel();
        btnclose = new rojerusan.RSPanelImage();
        txt_puerto = new rojeru_san.RSMTextFull();
        lblurl = new javax.swing.JLabel();
        lblurl1 = new javax.swing.JLabel();
        lblurl2 = new javax.swing.JLabel();
        lcodigo32 = new javax.swing.JLabel();
        lcodigo10 = new javax.swing.JLabel();
        lcodigo31 = new javax.swing.JLabel();
        lb_servir_estatus = new javax.swing.JLabel();
        lb_cliente_estado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new rojerusan.RSTableMetro();
        btn_usuario3 = new rojerusan.RSMaterialButtonRound();
        lb_codigo = new javax.swing.JLabel();
        btnmin1 = new rojerusan.RSPanelImage();
        btnmin2 = new rojerusan.RSPanelImage();
        lb_host = new javax.swing.JLabel();
        lcodigo19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(244, 248, 247));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(253, 19, 94), 3));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_nombre_negocio2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_nombre_negocio2.setForeground(new java.awt.Color(46, 78, 114));
        lbl_nombre_negocio2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_nombre_negocio2.setText("INFORMACION DEL SERVIDOR");
        jPanel1.add(lbl_nombre_negocio2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 370, 30));

        lcodigo17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lcodigo17.setForeground(new java.awt.Color(46, 78, 114));
        lcodigo17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lcodigo17.setText("IP DEL SERVIDOR:");
        jPanel1.add(lcodigo17, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 150, 20));

        lb_ip_servidor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_ip_servidor.setForeground(new java.awt.Color(46, 78, 114));
        lb_ip_servidor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_ip_servidor.setText("127.0.0.1");
        jPanel1.add(lb_ip_servidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 190, 20));

        lcodigo18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lcodigo18.setForeground(new java.awt.Color(46, 78, 114));
        lcodigo18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lcodigo18.setText("PUERTO:");
        jPanel1.add(lcodigo18, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 70, 20));

        btnclose.setToolTipText("Cerrar");
        btnclose.setImagen(new javax.swing.ImageIcon(getClass().getResource("/vistas/close.png"))); // NOI18N
        btnclose.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btncloseMouseMoved(evt);
            }
        });
        btnclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncloseMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btncloseMouseExited(evt);
            }
        });
        jPanel1.add(btnclose, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 26, 26));

        txt_puerto.setBackground(new java.awt.Color(46, 78, 114));
        txt_puerto.setForeground(new java.awt.Color(46, 78, 114));
        txt_puerto.setText("8000");
        txt_puerto.setBordeColorFocus(new java.awt.Color(46, 78, 114));
        txt_puerto.setBotonColor(new java.awt.Color(70, 187, 151));
        txt_puerto.setCaretColor(new java.awt.Color(70, 187, 151));
        txt_puerto.setColorTransparente(true);
        txt_puerto.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        txt_puerto.setPlaceholder(" *");
        txt_puerto.setSoloNumeros(true);
        txt_puerto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_puertoFocusLost(evt);
            }
        });
        txt_puerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_puertoActionPerformed(evt);
            }
        });
        txt_puerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_puertoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_puertoKeyTyped(evt);
            }
        });
        jPanel1.add(txt_puerto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 190, 37));

        lblurl.setBackground(new java.awt.Color(255, 255, 255));
        lblurl.setForeground(new java.awt.Color(46, 78, 114));
        lblurl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblurl.setText("cuscocode.com");
        lblurl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblurl.setName(""); // NOI18N
        jPanel1.add(lblurl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 220, 20));

        lblurl1.setBackground(new java.awt.Color(255, 255, 255));
        lblurl1.setForeground(new java.awt.Color(46, 78, 114));
        lblurl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblurl1.setText("Raul Gabriel Hacho Cutipa");
        lblurl1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblurl1.setName(""); // NOI18N
        jPanel1.add(lblurl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 230, 20));

        lblurl2.setBackground(new java.awt.Color(255, 255, 255));
        lblurl2.setForeground(new java.awt.Color(46, 78, 114));
        lblurl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblurl2.setText("tel:+51940500006");
        lblurl2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblurl2.setName(""); // NOI18N
        jPanel1.add(lblurl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 410, 220, 20));

        lcodigo32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lcodigo32.setForeground(new java.awt.Color(46, 78, 114));
        lcodigo32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lcodigo32.setText("DETALLES DEL SERVIDOR");
        jPanel1.add(lcodigo32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 710, 20));

        lcodigo10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lcodigo10.setForeground(new java.awt.Color(46, 78, 114));
        lcodigo10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lcodigo10.setText("ESTADO:");
        jPanel1.add(lcodigo10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 70, 20));

        lcodigo31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lcodigo31.setForeground(new java.awt.Color(46, 78, 114));
        lcodigo31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lcodigo31.setText("CLIENTE:");
        jPanel1.add(lcodigo31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 100, 20));

        lb_servir_estatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_servir_estatus.setForeground(new java.awt.Color(46, 78, 114));
        lb_servir_estatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_servir_estatus.setText("APAGADO");
        jPanel1.add(lb_servir_estatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 100, 20));

        lb_cliente_estado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_cliente_estado.setForeground(new java.awt.Color(46, 78, 114));
        lb_cliente_estado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_cliente_estado.setText("ESPERANDO...");
        jPanel1.add(lb_cliente_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 100, 20));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Cliente", "Telefono"
            }
        ));
        tabla.setAltoHead(25);
        tabla.setColorBackgoundHead(new java.awt.Color(70, 187, 151));
        tabla.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tabla.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tabla.setColorFilasBackgound2(new java.awt.Color(172, 224, 208));
        tabla.setColorFilasForeground1(new java.awt.Color(131, 209, 185));
        tabla.setColorFilasForeground2(new java.awt.Color(255, 255, 255));
        tabla.setColorSelBackgound(new java.awt.Color(50, 105, 125));
        tabla.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabla.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla.setGridColor(new java.awt.Color(255, 255, 255));
        tabla.setGrosorBordeFilas(0);
        tabla.setMultipleSeleccion(false);
        tabla.setSelectionBackground(new java.awt.Color(227, 94, 106));
        tabla.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tablaMouseMoved(evt);
            }
        });
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 410, 120));

        btn_usuario3.setBackground(new java.awt.Color(70, 187, 151));
        btn_usuario3.setBorder(null);
        btn_usuario3.setText("Guardar");
        btn_usuario3.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        btn_usuario3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuario3ActionPerformed(evt);
            }
        });
        jPanel1.add(btn_usuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 230, 50));
        jPanel1.add(lb_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 220, 190));

        btnmin1.setToolTipText("Minimizar");
        btnmin1.setImagen(new javax.swing.ImageIcon(getClass().getResource("/vistas/minR.png"))); // NOI18N
        btnmin1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnmin1MouseMoved(evt);
            }
        });
        btnmin1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnmin1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnmin1MouseExited(evt);
            }
        });
        jPanel1.add(btnmin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 26, 26));

        btnmin2.setToolTipText("Minimizar");
        btnmin2.setImagen(new javax.swing.ImageIcon(getClass().getResource("/vistas/ocultar.png"))); // NOI18N
        btnmin2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnmin2MouseMoved(evt);
            }
        });
        btnmin2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnmin2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnmin2MouseExited(evt);
            }
        });
        jPanel1.add(btnmin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 30, 20));

        lb_host.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_host.setForeground(new java.awt.Color(46, 78, 114));
        lb_host.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_host.setText("anonymous");
        jPanel1.add(lb_host, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 190, 20));

        lcodigo19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lcodigo19.setForeground(new java.awt.Color(46, 78, 114));
        lcodigo19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lcodigo19.setText("NOMBRE HOST:");
        jPanel1.add(lcodigo19, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 150, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncloseMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncloseMouseMoved
        // TODO add your handling code here:
        btnclose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    }//GEN-LAST:event_btncloseMouseMoved

    private void btncloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncloseMouseClicked
        System.exit(-1);

    }//GEN-LAST:event_btncloseMouseClicked

    private void btncloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncloseMouseExited
        // TODO add your handling code here:

    }//GEN-LAST:event_btncloseMouseExited

    private void txt_puertoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_puertoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_puertoFocusLost

    private void txt_puertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_puertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_puertoActionPerformed

    private void txt_puertoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_puertoKeyPressed

    }//GEN-LAST:event_txt_puertoKeyPressed

    private void txt_puertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_puertoKeyTyped

    }//GEN-LAST:event_txt_puertoKeyTyped

    private void tablaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseMoved
        // TODO add your handling code here:
        //   Animacion.Animacion.mover_izquierda(0, -180, 2, 10, pnlMenu);
    }//GEN-LAST:event_tablaMouseMoved

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        //

    }//GEN-LAST:event_tablaMouseClicked

    private void tablaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMousePressed

    }//GEN-LAST:event_tablaMousePressed

    private void btn_usuario3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuario3ActionPerformed
        guardar_configuracion();
    }//GEN-LAST:event_btn_usuario3ActionPerformed

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void btnmin1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmin1MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btnmin1MouseMoved

    private void btnmin1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmin1MouseClicked
        // TODO add your handling code here:
         this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btnmin1MouseClicked

    private void btnmin1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmin1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnmin1MouseExited

    private void btnmin2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmin2MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btnmin2MouseMoved

    private void btnmin2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmin2MouseClicked
        ocultaTray.ocultar();
        this.setVisible(false);
    }//GEN-LAST:event_btnmin2MouseClicked

    private void btnmin2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmin2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnmin2MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRound btn_usuario3;
    private rojerusan.RSPanelImage btnclose;
    private rojerusan.RSPanelImage btnmin1;
    private rojerusan.RSPanelImage btnmin2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lb_cliente_estado;
    private javax.swing.JLabel lb_codigo;
    public javax.swing.JLabel lb_host;
    public javax.swing.JLabel lb_ip_servidor;
    public javax.swing.JLabel lb_servir_estatus;
    private javax.swing.JLabel lbl_nombre_negocio2;
    private javax.swing.JLabel lblurl;
    private javax.swing.JLabel lblurl1;
    private javax.swing.JLabel lblurl2;
    private javax.swing.JLabel lcodigo10;
    private javax.swing.JLabel lcodigo17;
    private javax.swing.JLabel lcodigo18;
    private javax.swing.JLabel lcodigo19;
    private javax.swing.JLabel lcodigo31;
    private javax.swing.JLabel lcodigo32;
    public rojerusan.RSTableMetro tabla;
    public rojeru_san.RSMTextFull txt_puerto;
    // End of variables declaration//GEN-END:variables
}
