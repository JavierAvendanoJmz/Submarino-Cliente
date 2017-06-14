/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import modelo.Barco;

/**
 *
 * @author j4v13
 */
public class MainForm extends JFrame {
    
    private int nBarcos = 3;
    
    private Controlador controlador;
    private JPanel pnlNorte;
    private JPanel pnlCentro;
    private JPanel pnlEste;
    private JPanel pnlSur;
    private JLabel lblMensaje;
    private JPanel[][] pnlBarcos;
    private JPanel[][] pnlDisparos;
    private JRadioButton[][] rbtDisparos;
    private JButton btnDisparar;
    private Barco barco;
    private int contador;
    private int[] seleccionado;
    
    private Boolean derrota;
    
    private String empieza;

    public MainForm() {
        super("Submarino");
        super.setSize(420, 768);
        super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        derrota = false;
        pnlNorte = pnlBuscar();
        controlador = new Controlador(nBarcos);
        super.add(pnlNorte, BorderLayout.NORTH);
        super.setVisible(true);
    }        
    
    private JPanel pnlBuscar(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton btnBuscar = new JButton("Buscar Partida");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("1");
                btnBuscar.setText("Buscando...");
                btnBuscar.setEnabled(false);
                if(!btnBuscar.isEnabled()) {
                    MainForm.super.setVisible(true);
                    System.out.println("2");
                    boolean conectado = controlador.buscarPartida((JButton)ae.getSource());
                    if(conectado) {
//                    controlador.start();
    //                    System.out.println("3");
                        MainForm.super.remove(pnlNorte);
                        pnlNorte = pnlMensaje();
                        MainForm.super.add(pnlNorte, BorderLayout.NORTH);
                        pnlCentro = pnlTableros();
                        MainForm.super.add(pnlCentro, BorderLayout.CENTER);
                        pnlEste = pnlColocarBarco();
                        MainForm.super.add(pnlEste, BorderLayout.EAST);
    //                    pnlSur = pnlDisparar();
    //                    MainForm.super.add(pnlSur, BorderLayout.SOUTH);
                        MainForm.super.setVisible(true);
                    }
                }                    
//                System.out.println("4");
            }
        });
        panel.add(btnBuscar);
        return panel;
    }
    
    private JPanel pnlMensaje() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        lblMensaje = new JLabel("Jugador 2: Colocando barcos...");
        panel.add(lblMensaje);
        return panel;
    }
    
    
    private JPanel pnlObjeto(Component c) {
        JPanel pnlObj = new JPanel();
        pnlObj.setLayout(new FlowLayout());
        pnlObj.add(c);
        return pnlObj;
    }
    
    private void pintarBarcos() {
        int[][] mapa = controlador.obtenerTableroA();
        for (int i = 1; i < pnlBarcos.length; i++) {
            for (int j = 1; j < pnlBarcos.length; j++) {
                if(mapa[i-1][j-1] > 0) {
                    pnlBarcos[i][j].setBackground(Color.red);
                }
            }
        }
        super.setVisible(true);
    }
    
    private JPanel pnlColocarBarco() {
        JPanel pnlColocarBarco = new JPanel();
        pnlColocarBarco.setLayout(new GridLayout(18,1));
        
        TextField txtF = new TextField(2);
        TextField txtC = new TextField(2);
        String[] datos = {"Horizontal", "Vertical"};
        JComboBox cbxOrientacion = new JComboBox(datos);   
        JButton btnAgregar = new JButton("Agregar");
        int largo[] = {2,2,2,2,3,3,3,4,4,5};
        contador = 0;
        JLabel lblContador = new JLabel("Tamaño: " + largo[contador]);
        
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int f = Character.toUpperCase(txtF.getText().charAt(0)) - 'A';
                int c = Integer.parseInt(txtC.getText())-1;
                char o = cbxOrientacion.getSelectedIndex() == 0 ? 'h' : 'v';
                if(controlador.colocarBarco(f, c, largo[contador], o)) {
                    txtF.setText("");
                    txtC.setText("");
                    cbxOrientacion.setSelectedIndex(0);
                    contador++;
                }
                lblContador.setText("Tamaño: " + largo[contador]);
                pintarBarcos();
                System.out.println("agregar1");
                MainForm.this.setVisible(true);
                if (contador >= nBarcos) {
                    MainForm.this.remove(pnlEste);
                    System.out.println("agregar2");
                    try {
                        controlador.write("1");
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("agregar3");
                    MainForm.this.setVisible(true);
                    controlador.mostrarTablero();
                    try {
                        empieza = controlador.read();
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    System.out.println("agregar4 " + empieza);
                    iniciarPartida(empieza);
                }
            }
        });
        
        pnlColocarBarco.add(pnlObjeto(new JLabel("Colocar barco")));
        pnlColocarBarco.add(pnlObjeto(lblContador));
        pnlColocarBarco.add(pnlObjeto(new JLabel("Coordenadas:")));
        JPanel pnlAux = new JPanel();
        pnlAux.setLayout(new FlowLayout());
        pnlAux.add(txtF);
        pnlAux.add(txtC);
        pnlColocarBarco.add(pnlObjeto(pnlAux));
        pnlColocarBarco.add(pnlObjeto(new JLabel("Orientacion:")));
        pnlColocarBarco.add(pnlObjeto(cbxOrientacion));
        pnlColocarBarco.add(pnlObjeto(btnAgregar));
        
        return pnlColocarBarco;
    }    
    
    private void iniciarPartida(String aux) {
        System.out.println("jugador " + aux);
        if(aux.equals("1")) {
            lblMensaje.setText("");
            activarRbt();
            super.setVisible(true);
            System.out.println("hola");
                    
        } else {
                try {
                    lblMensaje.setText("");
                    String s = controlador.read();
                    System.out.println("asddsa"+ s);
                    String t = controlador.recibeDisparo(Integer.parseInt(s.charAt(0)+""),
                            Integer.parseInt(s.charAt(1)+""))+"";
                    if(controlador.isDerrota()) {
                        t+=1+"";                        
                        lblMensaje.setText("Perdiste");
                            derrota = true;
                    } else
                        t += 0+"";
                    controlador.write(t);
                    
                    activarRbt();
                    super.setVisible(true);
                    System.out.println("funcionara?");

                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
        }
    }
    
    private JPanel pnlTableros() {
        JPanel pnlTableros = new JPanel();
        pnlTableros.setLayout(new GridLayout(2, 1));
        pnlTableros.add(pnlTableroA());
        pnlTableros.add(pnlTableroB());
        return pnlTableros;
    }
    
    private JPanel pnlTableroA() {
        JPanel pnlTableroA = new JPanel();
        pnlTableroA.setLayout(new GridLayout(11,11));        
        pnlBarcos = new JPanel[11][11];
        int[] numeros = {1,2,3,4,5,6,7,8,9,10};
        char[] letras = {'A','B','C','D','E','F','G','H','I','J'};
        pnlBarcos[0][0] = new JPanel();
        pnlBarcos[0][0].add(new JLabel("F / C"));
        for (int i = 1; i < pnlBarcos.length; i++) {
            pnlBarcos[0][i] = new JPanel();
            pnlBarcos[i][0] = new JPanel();
            JLabel lblN = new JLabel(numeros[i-1]+"");
            JLabel lblL = new JLabel(letras[i-1]+"");
            pnlBarcos[0][i].add(lblN);
            pnlBarcos[i][0].add(lblL);
        }
        for (int i = 1; i < pnlBarcos.length; i++) {
            for (int j = 1; j < pnlBarcos[0].length; j++) {
                pnlBarcos[i][j] = new JPanel();
                pnlBarcos[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                pnlBarcos[i][j].setBackground(Color.blue);
            }
        }
        for (int i = 0; i < pnlBarcos.length; i++) {
            for (int j = 0; j < pnlBarcos[0].length; j++) {
                pnlTableroA.add(pnlBarcos[i][j]);
            }
        }
        return pnlTableroA;
    }
    
    private void activarRbt() {
        int[][] tableroB = controlador.obtenerTableroB();
        System.out.println("activarRbt");
        for (int i = 0; i < rbtDisparos.length; i++) {
            for (int j = 0; j < rbtDisparos.length; j++) {
                if(tableroB[i][j] == 0)
                    rbtDisparos[i][j].setEnabled(true);                
            }
        }
    }
    
    private void desactivarRbt() {
        for (int i = 0; i < rbtDisparos.length; i++) {
            for (int j = 0; j < rbtDisparos.length; j++) {
                rbtDisparos[i][j].setEnabled(false);
            }
        }
    }
    
    private JPanel pnlTableroB() {
        JPanel pnlTableroB = new JPanel();
        pnlTableroB.setLayout(new GridLayout(11,11));    
        
        pnlDisparos = new JPanel[11][11];
        rbtDisparos = new JRadioButton[10][10];
        int[] numeros = {1,2,3,4,5,6,7,8,9,10};
        char[] letras = {'A','B','C','D','E','F','G','H','I','J'};
        pnlDisparos[0][0] = new JPanel();
        pnlDisparos[0][0].add(new JLabel("F / C"));
        
        for (int i = 1; i < pnlDisparos.length; i++) {
            pnlDisparos[0][i] = new JPanel();
            pnlDisparos[i][0] = new JPanel();
            JLabel lblN = new JLabel(numeros[i-1]+"");
            JLabel lblL = new JLabel(letras[i-1]+"");
            pnlDisparos[0][i].add(lblN);
            pnlDisparos[i][0].add(lblL);
        }
        for (int i = 1; i < pnlDisparos.length; i++) {
            for (int j = 1; j < pnlDisparos[0].length; j++) {
                pnlDisparos[i][j] = new JPanel();
                rbtDisparos[i-1][j-1] = new JRadioButton();  
                
                rbtDisparos[i-1][j-1].addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent ie) {
                        try {
                            lblMensaje.setText("");
                            desactivarRbt();
                            JRadioButton aux = (JRadioButton)ie.getItem();
                            int k = 0;
                            int l = 0;
                            int f = 0;
                            int c = 0;
                            for (k = 0; k < 10; k++) {
                                for (l = 0; l < 10; l++) {
                                    if(rbtDisparos[k][l] == aux) {
                                        f = k;
                                        c = l;
                                    }
                                }
                            }
                            System.out.println(f + " " + c);
                            desactivarRbt();
                            String m;
                            controlador.write(f+""+c);
                            m = controlador.read();
                            System.out.println(m);
                            if (m.charAt(0) != '0')
                                pnlDisparos[f+1][c+1].setBackground(Color.RED);       
                            if (m.charAt(1) == 1) {
                                derrota = true;
                                lblMensaje.setText("Ganaste!!!");
                                desactivarRbt();
                            }
                            
                            if(derrota == false){
                                
                                lblMensaje.setText("");

                                String s = controlador.read();
                                System.out.println("asddsa"+ s);
                                String t = controlador.recibeDisparo(Integer.parseInt(s.charAt(0)+""),
                                        Integer.parseInt(s.charAt(1)+""))+"";
                                if(controlador.isDerrota()) {
                                    t+=1+"";
                                    lblMensaje.setText("Perdiste");
                                    derrota = true;
                                    desactivarRbt();
                                } else {
                                    t += 0+"";
                                }
                                controlador.write(t);

                                activarRbt();
                            }
                            
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                pnlDisparos[i][j].add(rbtDisparos[i-1][j-1]);
            }
        }
        for (int i = 0; i < pnlDisparos.length; i++) {
            for (int j = 0; j < pnlDisparos[0].length; j++) {
                pnlTableroB.add(pnlDisparos[i][j]);
            }
        }
        desactivarRbt();
        return pnlTableroB;
    }
    
    private JPanel pnlDisparar() {
        JPanel pnlDisparar = new JPanel();
        pnlDisparar.setLayout(new FlowLayout());
        btnDisparar = new JButton("Disparar");
        btnDisparar.setEnabled(false);
        pnlDisparar.add(btnDisparar);
        return pnlDisparar;
    }
        
}
