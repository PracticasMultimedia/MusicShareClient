/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicShareClient;

import GUI.EqualizerGUI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrián
 */
public class Equalizer extends Thread {
    
    EqualizerGUI gui;
    ArrayList<float[]> data;
    ArrayList<Integer> temp;
    boolean salir = false;
    double[] buffer;
    int turno;
    
    public Equalizer(EqualizerGUI _gui) {
        
        gui = _gui;
        data = new ArrayList<>();
        temp = new ArrayList<>();
        turno = 0;
    }
    
    public void addData(String dat, int _frec) {
        
        int n = 1024 / (_frec * 2) * 1000;
                
        
        float[] aux = new float[7];
        String[] slices = dat.split(";");
        
        for (int i = 0; i < aux.length; i++) {
            
            aux[i] = Float.valueOf(slices[i]);
            
        }
        
        
        
        data.add(aux);
        
        
        temp.add(n);
        
        turno = (turno + 1) % 4;
    }
    
    public void clearData() {
        
        data.clear();
        
    }
    
    @Override
    public void run() {
        long t = System.currentTimeMillis();
        while (!salir) {
            
            if (data.size() > 0) {
                
                
                
                gui.printEcualizer(data.get(0));
                
                long n = temp.get(0);
                
                data.remove(0);
                temp.remove(0);
                
                try {
                    long espera = n - (System.currentTimeMillis() - t);
                    if (espera > 0) {
                        sleep(espera);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Equalizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                
                try {
                    sleep(50);
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Equalizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
    }
    
    public void reset() {
        gui.reset();
    }
    
    public void setVisible(boolean visible) {
        gui.setVisible(visible);
    }
    
    public void terminar() {
        
        salir = true;
        
    }
}
