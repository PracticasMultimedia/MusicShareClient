/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Jesus
 */
public class UDPBroadcast extends Thread {
    
    Conexion con;

    public UDPBroadcast(Conexion con) {
        this.con = con;
    }
    
    //Cliente
    @Override
    public void run() {
        try {
            DatagramSocket sckCliente = new DatagramSocket();
            InetAddress dirIP = InetAddress.getByName("localhost");
            byte[] datosEnvio = new byte[1024];
            byte[] datosRecibidos = new byte[1024];
            datosEnvio = "MENSAJE PARA CONEXION DIRECTA".getBytes();
            DatagramPacket pckEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, dirIP, 9876);
            sckCliente.send(pckEnvio);
            DatagramPacket pckRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
            sckCliente.receive(pckRecibido);
            String modifiedSentence = new String(pckRecibido.getData()).trim();
            System.out.println("FROM SERVER:" + modifiedSentence.trim());
            con.setHost(pckRecibido.getAddress().toString());
            sckCliente.close();
        } catch (Exception ex) {
            
        }
    }
}
