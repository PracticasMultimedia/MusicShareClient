/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import GUI.ClientGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Clase que realizará una inundación UDP a través de la red local para buscar
 * Servidores disponibles. Esta es una forma más fácil de conectarse con el
 * servidor, si necesidad de saber su dirección IP. Para ello el Servidor debe
 * permitir realizar conexiones directas.
 *
 * @author Jesús Cuenca López | Adrián Luque Luque
 */
public class UDPBroadcast extends Thread {

    //Interfaz que llama al hilo, para poder comunicarnos con ella tanto si se 
    //encuentra servidor como si no.
    ClientGUI gui;

    /**
     * Establece la interfaz con la que se comunicará al finalizar la búsqueda.
     *
     * @param _gui
     */
    public UDPBroadcast(ClientGUI _gui) {
        this.gui = _gui;
    }

    /**
     * Inicia la inundación por la red local en busca de un Servidor que nos
     * conteste.
     */
    @Override
    public void run() {
        //Creamos un socket para comunicarnos con el servidor.
        try (DatagramSocket sckCliente = new DatagramSocket()) {
            //Establecemos que sera Broadcast
            sckCliente.setBroadcast(true);

            //Establecemos un timeout para no quedarnos esperando la respuesta 
            //un tiempo indeterminado. Si en 30s nadie contesta, daremos la 
            //búsqueda como fallida.
            sckCliente.setSoTimeout(30000);

            InetAddress dirIP = InetAddress.getByName("255.255.255.255");
            byte[] datosEnvio = new byte[1024];
            byte[] datosRecibidos = new byte[1024];
            datosEnvio = "MENSAJE PARA CONEXION DIRECTA".getBytes();

            //Enviamos el mensaje broadcast.
            DatagramPacket pckEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, dirIP, 9876);
            sckCliente.send(pckEnvio);

            //Esperamos la respuesta
            DatagramPacket pckRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
            try {
                sckCliente.receive(pckRecibido);
            } catch (SocketTimeoutException to) {
                //Si transcurridos 30 segundos nadie contesta, devolvemos null
                //para indicar que la búsqueda ha resultado fallida.
                returnIP(null);
                return;
            }

            //Si alguien nos contesta, obtenemos su dirección IP y se la mandamos a la interfaz.
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
