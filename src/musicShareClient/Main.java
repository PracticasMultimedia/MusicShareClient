/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import GUI.Cliente_Interfaz;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author macosx
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
//            Interfaz inter = new Interfaz(new Conexion());

            Conexion con = new Conexion();
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
            Cliente_Interfaz inter = new Cliente_Interfaz(con);
            con.setGui(inter);

        } catch (IOException e) {
            System.out.println("Err2or: " + e.toString() + ";" + e.getMessage());

        }


    }
}
