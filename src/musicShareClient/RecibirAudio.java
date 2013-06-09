/*
 * To change this template, choose Tools | Templates
 * and open the template data_input the editor.
 */
package musicShareClient;

import java.io.*;
import java.net.Socket;
import javax.sound.sampled.*;

/**
 * Es la clase recibe el audio enviado por el servidor y lo reproduce.
 *
 * @author Jesús Cuenca López | Adrián Luque Luque
 */
public class RecibirAudio extends Thread {

    private static SourceDataLine mLine;
    //Variables de control
    final int CONTROL_PORT = 5001;
    final String CONTROL_HOST;
    BufferedReader control_input;
    Socket control_sck;
    //Variables de datos
    final int DATA_PORT = 5002;
    final String DATA_HOST;
    Socket data_sck;
    DataInputStream data_input;
    //Variables auxiliares
    boolean keep_going;
    Conexion con;
    Ecualizador ec;

    /**
     * Crea un nuevo hilo para recibir audio, donde se inicializan la dirección
     * desde la que se escuchará.
     *
     * @param _host Dirección desde la que se escuchará.
     * @param _con Variable de tipo Conexion con la que trabajará el hilo
     * conjuntamente.
     */
    public RecibirAudio(String _host, Conexion _con) {
        CONTROL_HOST = _host;
        DATA_HOST = _host;
        con = _con;
    }

    /**
     * Establece la ventana del ecualizador, para poder mandarle los datos
     * recibidos, que éste se encargará de pintar.
     *
     * @param ec Ecualizador.
     */
    public void setEc(Ecualizador ec) {
        this.ec = ec;
        if (!ec.isAlive()) {
            ec.start();
        }
    }

    /**
     * Método sobreescrito de la clase Thread que iniciará la escucha de música
     * del servidor.
     */
    @Override
    public void run() {

        int frec = 0;

        try {
            keep_going = true;

            //Creamos los sockets para la transmisión de información.
            control_sck = new Socket(CONTROL_HOST, CONTROL_PORT);
            data_sck = new Socket(DATA_HOST, DATA_PORT);

            //Creamos los flujos de información asociados a cada socket.
            control_input = new BufferedReader(new InputStreamReader(control_sck.getInputStream()));
            data_input = new DataInputStream(new BufferedInputStream(data_sck.getInputStream()));

            //Variables auxiliares que necesitaremos.
            int sampleRate, format, channels;
            String entry;

            //Emezamos a leer información.
            entry = control_input.readLine();
            int aux = Integer.parseInt(entry);
            System.out.println(aux);

            //Tratamos la información recibida y leemos nuevos datos en caso necesario.
            while (aux != 0) {
                if (aux > 0) {
                    byte[] bytes = new byte[aux];
                    data_input.read(bytes);
                    playJavaSound(bytes);
                    entry = control_input.readLine();
                    ec.addData(entry, frec);

                } else if (aux == -1) {

                    terminateJavaSound();

                    entry = control_input.readLine();
                    System.out.println(entry);
                    entry = control_input.readLine();
                    System.out.println(entry);
                    sampleRate = Integer.parseInt(entry);
                    frec = Integer.parseInt(entry);
                    entry = control_input.readLine();
                    System.out.println(entry);
                    format = Integer.parseInt(entry);
                    entry = control_input.readLine();
                    System.out.println(entry);
                    channels = Integer.parseInt(entry);
                    openJavaSound(sampleRate, format, channels);

                } else if (!keep_going) {
                    terminateJavaSound();
                }

                entry = control_input.readLine();
                aux = Integer.parseInt(entry);
            }

            //Al terminar, cerramos el socket y finalizamos la reproducción de música.
            closeJavaSound();
            control_sck.close();

        } catch (Exception e) {
            con.connectError();
            System.out.println("Error: " + e.getMessage());

        }

    }

    /**
     * Leemos los datos e intentamos abrir la línea de reproducción.
     *
     * @param sampleRate
     * @param format
     * @param channels
     */
    private static void openJavaSound(int sampleRate, int format, int channels) {
        AudioFormat audioFormat = new AudioFormat(sampleRate, format, channels, true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            //Intentamos capturar el sonido
            mLine = (SourceDataLine) AudioSystem.getLine(info);
            //Abrimos la línea
            mLine.open(audioFormat);
            //Iniciamos la línea.
            mLine.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException("could not open audio line");
        }

    }

    /**
     * Reproducimos los datos en crudo.
     *
     * @param rawBytes
     */
    private static void playJavaSound(byte[] rawBytes) {
        //Reproducimos el sonido.
        mLine.write(rawBytes, 0, rawBytes.length);
    }

    /**
     * Finalizamos y cerramos la línea.
     */
    private static void closeJavaSound() {
        if (mLine != null) {
            //Esperamos a que la línea termine de reproducirse
            mLine.drain();
            //Cerramos la línea.
            mLine.close();

            mLine = null;
        }
    }

    /**
     * Finalizamos y cerramos la línea.
     */
    private static void terminateJavaSound() {
        if (mLine != null) {
            //Esperamos a que la línea termine de reproducirse.
            mLine.flush();
            //Cerramos la línea.
            mLine.close();

            mLine = null;
        }
    }

    /**
     * Establece el token 'keep_going' a false para detener la escucha de
     * información, y finalizar cuanto antes la reproducción.
     */
    public void stop_() {
        keep_going = false;

    }
}
