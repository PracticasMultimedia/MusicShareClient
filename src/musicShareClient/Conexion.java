/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import GUI.Cliente_Interfaz;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author macosx
 */
public class Conexion {

    final static String OK = "Successful";
    final static String NOK = "Not Understood";
    final int PORT = 5000;
    
    ArrayList<String> musicList;
    boolean connected;
    BufferedReader in;
    Cliente_Interfaz gui;
    DataOutputStream out;
    RecibirAudio cs;
    String name = InetAddress.getLocalHost().getHostName();
    String conName = "";
    String msg = "";
    Socket sck;
    String HOST;
    Thread ia;
    UDPBroadcast udp;

    /**
     * Crea una nueva Conexión.
     *
     * @throws UnknownHostException
     */
    public Conexion() throws UnknownHostException {
        musicList = new ArrayList<>();
        udp = null;
    }

    /**
     * Establece la interfaz con la que trabajaremos.
     *
     * @param gui Interfaz.
     */
    public void setGui(Cliente_Interfaz gui) {
        this.gui = gui;
    }

    /**
     * Solicita una conexión al servidor localizado en PORT:HOST, y definimos el
     * Ecualizador sobre el que dibujaremos la animación mientras se reproducen
     * canciones. En esta función también se establece la primera comunicación
     * para definir las variables necesarias en la interfaz la primera vez que
     * se cargue.
     *
     * @param _host Host por el que escucha el servidor.
     * @param ec Ecualizador sobre el que trabajaremos.
     * @return True si todo está en orden. False en caso de que haya algún
     * error.
     * @throws IOException
     */
    public boolean conectar(String _host, Ecualizador ec) throws IOException {

        if (connected == false) {
            HOST = _host;
            sck = new Socket(HOST, PORT);
            System.out.println(HOST);

            out = new DataOutputStream(sck.getOutputStream());
            in = new BufferedReader(new InputStreamReader(sck.getInputStream()));


            out.write((name + "\n").getBytes(Charset.forName("UTF-8")));

            connected = true;
            System.out.println("Conectando...");
            conName = in.readLine();

            musicList.clear();
            String s = in.readLine();
            System.out.println("Estableciendo music list");
            while (!s.equals(OK)) {
                System.out.println(s);
                musicList.add(s);
                s = in.readLine();
            }
            gui.setMusicList(musicList);

            cs = new RecibirAudio(HOST, this);

            cs.setEc(ec);

            cs.start();

            return true;
        }
        msg = "El cliente ya está lanzado";
        return false;

    }

    /**
     * Cierra la conexión establecida con el servidor. En caso de que no haya
     * ninguna conexión abierta, devolverá False.
     *
     * @return True si la conexión se cierra con éxito. False en caso contrario.
     */
    public boolean desconectar() {
        if (connected == true) {
            try {
                if (ia != null && cs != null) {
                    if (cs.isAlive()) {
                        cs.stop_();
                    }
                }

                out.write(("exit" + "\n").getBytes(Charset.forName("UTF-8")));

                sck.close();

                connected = false;
                return true;
            } catch (IOException ex) {
                msg = "Hubo un error al desconectar.";
                return false;
            }
        }
        msg = "El cliente no está lanzado";
        return false;
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////  Peticiones al servidor.   ////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Pide al servidor que nos devuelva la lista de archivos existentes en el
     * directorio en el que se encuentra actualmente.
     *
     * @return Lista de archivos del directorio actual. Null en caso de que haya
     * algún error.
     */
    public ArrayList<String> dir() {
        try {
            ArrayList<String> fich = new ArrayList<>();

            if (out == null) {
                System.out.println("aldjfadkljfañldkjfadf");
            }
            out.write(("dir" + "\n").getBytes(Charset.forName("UTF-8")));

            String entry = in.readLine();

            System.out.println(entry);
            while (!entry.equals("..")) {

                fich.add(entry);
                entry = in.readLine();
                System.out.println(entry);

            }

            return fich;
        } catch (IOException ex) {
            msg = "Error al cargar archivos.";
            return null;
        }
    }

    /**
     * Comunica al servidor que cambie el directorio en que se encuentra por el
     * que se le pasa por el canal de comunicación.
     *
     * @param folder Directorio al que debe cambiar el servidor. ".." en caso de
     * que nos queramos mover al directorio padre.
     * @return True en caso de que se haya realizado el cambio de directorio
     * correctamente, False en caso contrario.
     */
    public boolean cd(String folder) {
        try {
            out.write(("cd \"" + folder + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
            msg = "Error al cambiar de directorio.";
        }
        return false;
    }

    /**
     * Pide al servidor que reproduzca el archivo que se le pasa a través del
     * canal TCP.
     *
     * @param file Archivo que debe reproducir.
     * @return True en caso de que el archivo se pueda reproducir correctamente.
     * False en caso contrario.
     */
    public boolean play(String file) {
        try {
            out.write(("play \"" + file + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        msg = "Error al reproducir " + file + ".";
        return false;


    }

    /**
     * Pide al servidor que reproduzca un archivo de la lista de música
     * almacenada.
     *
     * @param file Índice de la canción dentro de la lista de música.
     * @return True si todo ha ido correctamente, False en caso contrario.
     */
    public boolean playFromMusic(int file) {
        try {
            out.write(("play_fm \"" + musicList.get(file) + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        msg = "Error al reproducir archivo.";
        return false;

    }

    /**
     * Pide al servidor que reproduzca un archivo de la lista de reproducción.
     *
     * @param file Índice de la canción dentro de la lista de reproducción.
     * @return True si todo ha ido correctamente, False en caso contrario.
     */
    public boolean playFromRepr(int file) {
        try {
            out.write(("play_fr \"" + file + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }

        msg = "Error al reproducir archivo.";
        return false;
    }

    /**
     * Pide al servidor que modifique la forma en que se reproducen las
     * canciones. Un valor True hará que las canciones se reproduzcan
     * cíclicamente, es decir, que cuando termine la última, empiece la primera.
     * Un valor False hará que cuando termine la última, se detenga la
     * reproducción.
     *
     * @param rep Valor al que establecer la reproducción.
     * @return
     */
    public boolean repeat(boolean rep) {
        String s;
        if (rep) {
            s = "true";
        } else {
            s = "false";
        }
        try {
            out.write(("repeat \"" + s + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        msg = "No se ha podido cambiar el método de reproducción.";
        return false;

    }

    /**
     * Pide al servidor que modifique la forma en que se reproducen las
     * canciones. Si shuffle está activado, las canciones se reproducirán de
     * forma aleatoria. En caso contrario, se reproducirán de forma lineal.
     *
     * @param shuffle Valor al que establecer la forma de reproducción.
     * @return
     */
    public boolean shuffle(boolean shuffle) {
        String s = shuffle ? "true" : "false";
        try {
            out.write(("shuffle \"" + s + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        msg = "No se ha podido cambiar el método de reproducción.";
        return false;
    }

    /**
     * Pide al servidor que detenta la reproducción de música.
     *
     * @throws IOException
     */
    public void stop() throws IOException {

        if (ia != null && cs != null) {
            if (cs.isAlive()) {
                out.write(("stop" + "\n").getBytes(Charset.forName("UTF-8")));

                cs.stop_();
                cs = null;
                ia = null;
            }
        }
    }

    /**
     * Pide al servidor que añada la canción que se le pasa a la lista de
     * reproducción.
     *
     * @param file Canción a añadir a la lista.
     * @return True si todo ha ido bien. False en caso de error.
     * @throws IOException
     */
    public boolean addSongtoList(String file) throws IOException {
        out.write(("add \"" + file + "\"\n").getBytes(Charset.forName("UTF-8")));
        if (in.readLine().equals(OK)) {
            return true;
        }
        msg = "No se ha podido añadir la canción a la lista.";
        return false;
    }

    /**
     * Pide al servidor que añada una canción de la lista de música almacenada a
     * la lista de reproducción.
     *
     * @param file Índice de la canción en la lista de música que queremos
     * añadir a la lista de reproducción.
     * @return
     * @throws IOException
     */
    public boolean addSongtoListFromMusic(int file) throws IOException {
        out.write(("add_fm \"" + musicList.get(file) + "\"\n").getBytes(Charset.forName("UTF-8")));
        if (in.readLine().equals(OK)) {
            return true;
        }
        msg = "No se ha podido añadir la canción a la lista.";
        return false;
    }

    /**
     * Devuelve el nombre de la conexión.
     *
     * @return El nombre de la conexión.
     */
    public String getConName() {
        return conName;
    }

    /**
     * Pide a la interfaz que avise al usuario de un error al conectar con el
     * servidor.
     */
    void connectError() {
        gui.connectError();
    }

    /**
     * Pide al servidor que elimine una canción de la lista de reproducción.
     *
     * @param song Canción a eliminar de la lista de reproducción.
     * @return True si se ha podido eliminar la canción de la lista. False en
     * caso contrario.
     * @throws IOException
     */
    public boolean deleteFromRepr(int song) throws IOException {
        System.out.println(song);
        out.write(("delete \"" + song + "\"\n").getBytes(Charset.forName("UTF-8")));
        if (in.readLine().equals(OK)) {
            return true;
        }
        msg = "No se ha podido eliminar la canción de la lista.";
        return false;
    }

    /**
     * Pide al servidor que reproduzca la siguiente canción de la lista de
     * reproducción.
     *
     * @return True se se ha podido reproducir la canción. False en caso
     * contrario.
     */
    public boolean next() {
        try {
            out.write("next\n".getBytes(Charset.forName("UTF-8")));

            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        msg = "No se ha podido reproducir la siguiente canción.";
        return false;
    }

    /**
     * Pide al servidor que reproduzca la canción anterior de la lista de
     * reproducción.
     *
     * @return True se se ha podido reproducir la canción. False en caso
     * contrario.
     */
    public boolean prev() {
        try {
            out.write("prev\n".getBytes(Charset.forName("UTF-8")));
            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (Exception e) {
        }
        msg = "No se ha podido reproducir la canción anterior.";
        return false;
    }

    /**
     * Pide al servidor que detenga la reproducción de música.
     *
     * @return True si se ha podido detener la música. False en caso contrario.
     */
    public boolean stopMusic() {
        try {
            out.write("stop\n".getBytes(Charset.forName("UTF-8")));
            if (in.readLine().equals(OK)) {
                return true;
            }
        } catch (Exception e) {
        }

        msg = "No se ha podido detener la reproducción.";
        return false;
    }

    /**
     * Devuelve el último mensaje de error establecido.
     *
     * @return El último mensaje de error establecido.
     */
    public String getMessage() {
        return msg;
    }
}
