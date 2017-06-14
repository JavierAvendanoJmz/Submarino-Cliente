/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import modelo.*;

/**
 *
 * @author j4v13
 */
public class Controlador extends Thread {
    
    private TableroA tableroA;
    private TableroB tableroB;

    private Socket cliente;
    private OutputStream output;
    private ObjectOutputStream writer;
    private InputStream input;
    private ObjectInputStream reader;
    
    public Controlador(int i) {
        tableroA = new TableroA(i);
        tableroB = new TableroB();        
    }
    
    private boolean conectar() {
        try {
//            System.out.println("hola1");
            cliente = new Socket("127.0.0.1",3000);
            output = cliente.getOutputStream();
            writer = new ObjectOutputStream(output);
            input = cliente.getInputStream();
            reader = new ObjectInputStream(input);
//            System.out.println("hola2");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
    }
    
    public boolean buscarPartida(JButton btnBuscar) {
        btnBuscar.setText("Buscando...");
        btnBuscar.setEnabled(false);
        return conectar();
    }    
    
    public int recibeDisparo(int f, int c) {
        return tableroA.recibirDisparo(f, c);
    }
    
    public boolean isDerrota(){
        return tableroA.isDerrota();
    }
    
    public boolean colocarBarco(int f, int c, int l, char o) {
        Barco barco = new Barco(l, o);
        return tableroA.colocarBarco(f, c, barco);
    }
    
    public void mostrarTablero() {
        tableroA.mostrarTablero();
    }
    
    public int[][] obtenerTableroA() {
        return tableroA.getMapa();
    }
    
    public int[][] obtenerTableroB() {
        return tableroB.getMapa();
    }
    
    public void write(String s) throws IOException {
        writer.writeObject(s);
    }
    
    public String read() throws IOException, ClassNotFoundException {
        return (String)reader.readObject();
    }

    @Override
    public void run() {
        try {
            cliente = new Socket("127.0.0.1",3000);
            output = cliente.getOutputStream();
            writer = new ObjectOutputStream(output);
            input = cliente.getInputStream();
            reader = new ObjectInputStream(input);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
 
}
