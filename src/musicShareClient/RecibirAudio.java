/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import java.io.*;
import java.net.Socket;
import javax.sound.sampled.*;

/**
 *
 * @author macosx
 */
public class RecibirAudio extends Thread {

    /**
     * @param args the command line arguments
     */
    private static SourceDataLine mLine;
    final String HOST;
    final int PUERTO = 5001;
    final String HOST2;
    final int PUERTO2 = 5002;
    Socket sc;
    Socket sc2;
    DataOutputStream mensaje;
    BufferedReader entrada;
    DataInputStream in;
    boolean continuar;
    Conexion con;
    Ecualizador ec;

    public void setEc(Ecualizador ec) {
        this.ec = ec;
        if (!ec.isAlive()) {
            ec.start();
        }
    }

    public RecibirAudio(String _host, Conexion _con) {
        HOST = _host;
        HOST2 = _host;
        con = _con;
    }

//Cliente
    @Override
    public void run() /*
     * ejecuta este metodo para correr el cliente
     */ {

        int frec = 0;

        try {
            continuar = true;
            sc = new Socket(HOST, PUERTO);
            sc2 = new Socket(HOST2, PUERTO2);
            /*
             *
             * conectar a un servidor en localhost con puerto 5000
             */



//creamos el flujo de datos por el que se enviara un mensaje

            mensaje = new DataOutputStream(sc.getOutputStream());
            entrada = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            in = new DataInputStream(new BufferedInputStream(sc2.getInputStream()));
            int sampleRate, format, channels;
            String entry;



            entry = entrada.readLine();
            int aux = Integer.parseInt(entry);
            System.out.println(aux);





            while (aux != 0) {
                if (aux > 0) {
                    byte[] bytes = new byte[aux];
                    in.read(bytes);
                    playJavaSound(bytes);
                    entry = entrada.readLine();
                    ec.addData(entry, frec);

                } else if (aux == -1) {

                    terminateJavaSound();

                    entry = entrada.readLine();
                    con.setSong(entry);
                    System.out.println(entry);
                    entry = entrada.readLine();
                    System.out.println(entry);
                    sampleRate = Integer.parseInt(entry);
                    frec = Integer.parseInt(entry);
                    entry = entrada.readLine();
                    System.out.println(entry);
                    format = Integer.parseInt(entry);
                    entry = entrada.readLine();
                    System.out.println(entry);
                    channels = Integer.parseInt(entry);
                    openJavaSound(sampleRate, format, channels);

                } else if (!continuar) {
                    terminateJavaSound();
                }

                entry = entrada.readLine();
                aux = Integer.parseInt(entry);
            }

            closeJavaSound();

            sc.close();

        } catch (Exception e) {
            con.connectError();
            System.out.println("Error: " + e.getMessage());

        }

    }

    private static void openJavaSound(int sampleRate, int format, int channels) {
        AudioFormat audioFormat = new AudioFormat(sampleRate, format, channels, true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            mLine = (SourceDataLine) AudioSystem.getLine(info);
            /**
             * if that succeeded, try opening the line.
             */
            mLine.open(audioFormat);
            /**
             * And if that succeed, start the line.
             */
            mLine.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException("could not open audio line");
        }

    }

    private static void playJavaSound(byte[] rawBytes) {
        /**
         * We're just going to dump all the samples into the line.
         */
        mLine.write(rawBytes, 0, rawBytes.length);

    }

    private static void closeJavaSound() {
        if (mLine != null) {
            /*
             * Wait for the line to finish playing
             */
            mLine.drain();
            /*
             * Close the line.
             */
            mLine.close();
            mLine = null;
        }
    }

    private static void terminateJavaSound() {
        if (mLine != null) {
            /*
             * Wait for the line to finish playing
             */
            mLine.flush();
            /*
             * Close the line.
             */
        }
    }

    public void stop_() {
        continuar = false;

    }
}
