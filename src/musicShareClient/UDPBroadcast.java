/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import GUI.Cliente_Interfaz;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 *
 * @author Jesus
 */
public class UDPBroadcast extends Thread {

    Cliente_Interfaz gui;

    public UDPBroadcast(Cliente_Interfaz _gui) {
        this.gui = _gui;
    }

    //Cliente
    @Override
    public void run() {
        try (DatagramSocket sckCliente = new DatagramSocket()) {
            sckCliente.setBroadcast(true);
            sckCliente.setSoTimeout(1000);

            InetAddress dirIP = InetAddress.getByName("255.255.255.255");
            byte[] datosEnvio = new byte[1024];
            byte[] datosRecibidos = new byte[1024];
            datosEnvio = "MENSAJE PARA CONEXION DIRECTA".getBytes();

            DatagramPacket pckEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, dirIP, 9876);
            sckCliente.send(pckEnvio);

            DatagramPacket pckRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
            try {
                sckCliente.receive(pckRecibido);
            } catch (SocketTimeoutException to) {
                returnIP(null);
                return;
            }

            String ip = pckRecibido.getAddress().toString();
            ip = ip.substring(1);
            returnIP(ip);
            
        } catch (IOException ex) {
        }

    }

    private void returnIP(String ip) {
        gui.setIP(ip);
    }
}
