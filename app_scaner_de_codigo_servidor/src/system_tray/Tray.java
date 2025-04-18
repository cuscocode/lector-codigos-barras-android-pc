package system_tray;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Jose Angeles
 * https://www.youtube.com/c/JoseAngelesSoftware?sub_confirmation=1
 */
public class Tray {

    private static JFrame frmPrincipal;

    public Tray(JFrame vista) {
        frmPrincipal = vista;
    }

    boolean palanca = true;

    public void ocultar() {
        if (palanca) {
            if (SystemTray.isSupported()) {

                SystemTray systemTray = SystemTray.getSystemTray();
                Image imagen = new ImageIcon(Tray.class.getResource("cuscocode.png")).getImage();
                TrayIcon trayIcon = new TrayIcon(imagen, "Servidor encendido");
                trayIcon.setImageAutoSize(true);
                MouseAdapter mouseAdapter = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Tray.frmPrincipal.setOpacity(1.0F);
                        trayIcon.displayMessage("Scaner de codigo", "Scaner de codigo Trabajando...", java.awt.TrayIcon.MessageType.INFO);
                        frmPrincipal.setVisible(true);
                    }
                };
                trayIcon.addMouseListener(mouseAdapter);
                try {
                    systemTray.add(trayIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            palanca = false;
        }
    }
}
