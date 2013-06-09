/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import GUI.ClientGUI;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author macosx
 */
public class Main {

    /**
     * Crea e inicializa las clases e hilos principales para poner a funcionar la aplicación.
     */
    public static void main(String[] args) {
        try {

            Connect con = new Connect();
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
            ClientGUI inter = new ClientGUI(con);
            con.setGui(inter);

        } catch (IOException e) {
            System.out.println("Err2or: " + e.toString() + ";" + e.getMessage());

        }


    }
}
