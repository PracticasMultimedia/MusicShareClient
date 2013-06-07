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
    boolean conectado;
    RecibirAudio cs;
    private static SourceDataLine mLine;
    String HOST;
    final int PUERTO = 5000;
    Socket sc;
    DataOutputStream out;
    BufferedReader entrada;
    String name = InetAddress.getLocalHost().getHostName();
    String conName = "";
    Thread ia;
    Cliente_Interfaz gui;
    ArrayList<String> musicList;
    UDPBroadcast udp;

    public void setGui(Cliente_Interfaz gui) {
        this.gui = gui;
    }

    public Conexion() throws UnknownHostException {
        musicList = new ArrayList<>();
        udp = null;
    }

    public boolean conectar(String _host, Ecualizador ec) throws IOException {

        if (conectado == false) {
            HOST = _host;
            sc = new Socket(HOST, PUERTO);
            System.out.println(HOST);

            out = new DataOutputStream(sc.getOutputStream());
            entrada = new BufferedReader(new InputStreamReader(sc.getInputStream()));


            out.write((name + "\n").getBytes(Charset.forName("UTF-8")));

            conectado = true;
            System.out.println("Conectando...");
            conName = entrada.readLine();

            musicList.clear();
            String s = entrada.readLine();
            System.out.println("Estableciendo music list");
            while (!s.equals(OK)) {
                System.out.println(s);
                musicList.add(s);
                s = entrada.readLine();
            }
            gui.setMusicList(musicList);

            cs = new RecibirAudio(HOST, this);

            cs.setEc(ec);

            cs.start();

            return true;
        }
        return false;

    }

    public boolean desconectar() {
        if (conectado == true) {
            try {
                if (ia != null && cs != null) {
                    if (cs.isAlive()) {
                        cs.stop_();
                    }
                }

                out.write(("exit" + "\n").getBytes(Charset.forName("UTF-8")));

                sc.close();

                conectado = false;
            } catch (IOException ex) {
                return false;
            }
        }

        return true;

    }

    public ArrayList<String> dir() {
        try {
            ArrayList<String> fich = new ArrayList<>();

            if (out == null) {
                System.out.println("aldjfadkljfañldkjfadf");
            }
            out.write(("dir" + "\n").getBytes(Charset.forName("UTF-8")));

            String entry = entrada.readLine();


            //        entry = entrada.readLine();
            System.out.println(entry);
            while (!entry.equals("..")) {

                fich.add(entry);
                entry = entrada.readLine();
                System.out.println(entry);

            }

            System.out.println("sali");

            return fich;
        } catch (IOException ex) {
            return null;
        }
    }

    public boolean cd(String folder) {
        try {
            out.write(("cd \"" + folder + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (entrada.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    public boolean play(String file) {
        try {
            out.write(("play \"" + file + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (entrada.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        return false;


    }

    public boolean playFromMusic(int file) {
        try {
            out.write(("play_fm \"" + musicList.get(file) + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (entrada.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }

        return false;

    }

    public boolean playFromRepr(int file) {
        try {
            out.write(("play_fr \"" + file + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (entrada.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }

        return false;


    }

    public boolean repeat(boolean rep) {
        String s;
        if (rep) {
            s = "true";
        } else {
            s = "false";
        }
        try {
            out.write(("repeat \"" + s + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (entrada.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        return false;

    }

    public boolean shuffle(boolean rep) {
        String s;
        if (rep) {
            s = "true";
        } else {
            s = "false";
        }
        try {
            out.write(("shuffle \"" + s + "\"\n").getBytes(Charset.forName("UTF-8")));

            if (entrada.readLine().equals(OK)) {
                return true;
            }
        } catch (IOException ex) {
        }
        return false;
    }

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

    public boolean addSongtoList(String file) throws IOException {
        out.write(("add \"" + file + "\"\n").getBytes(Charset.forName("UTF-8")));
        if (entrada.readLine().equals(OK)) {
            return true;
        }
        return false;
    }

    public boolean addSongtoListFromMusic(int file) throws IOException {
        out.write(("add_fm \"" + musicList.get(file) + "\"\n").getBytes(Charset.forName("UTF-8")));
        if (entrada.readLine().equals(OK)) {
            return true;
        }
        return false;
    }

    public void setSong(String song) {
        int index = song.lastIndexOf("\\");
        if (index < 0) {
            gui.setSong(song);
        } else {
            gui.setSong(song.substring(index + 1));
        }
    }

    public String getConName() {

        return conName;
    }

    void connectError() {
        gui.connectError();
    }

    public boolean deleteFromRepr(int song) throws IOException {
        System.out.println(song);
        out.write(("delete \"" + song + "\"\n").getBytes(Charset.forName("UTF-8")));
        if (entrada.readLine().equals(OK)) {
            return true;
        }
        return false;
    }

    public void initUDPBroadcast() {
        if (udp == null) {
            udp = new UDPBroadcast(this);
            udp.start();
        }
    }

    public void setHost(String host) throws IOException {
        System.out.println("servidor encontrado. iniciando app" + host);
        conectar(host, gui.getEcualizador());
        gui.showInterface();
    }

    public void next() {
    }
    
    public void prev(){};
}
