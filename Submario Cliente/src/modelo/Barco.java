/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author j4v13
 */
public class Barco {
        
    private int[] inicio;
    private Integer largo;    
    private Character orientacion;
    private int[] barco;
    
    public Barco() {
    }

    public Barco(Integer largo) {
        this.largo = largo;
        barco = new int[largo];
    }

    public Barco(Integer largo, Character orientacion) {
        this.largo = largo;
        this.orientacion = orientacion;
        barco = new int[largo];
    }
    
    public boolean isDestruido() {
        System.out.println("----");
        for (int i = 0; i < largo; i++) {
            System.out.println("barco: "+i+" "+barco[i]);
            if(barco[i] == 0) 
                return false;
        }
        System.out.println("barco?");
        return true;
    }
    
    public boolean recibirDisparo(int i) {
        barco[i] = 1;
        return isDestruido();
    }

    public int[] getInicio() {
        return inicio;
    }

    public void setInicio(int[] inicio) {
        this.inicio = inicio;
    }        
    
    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
        barco = new int[largo];
    }

    public Character getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Character orientacion) {
        this.orientacion = orientacion;
    }

    @Override
    public String toString() {
        String b = new String();
        for (int i = 0; i < barco.length; i++)
            b += barco[i]+"";
        return "Barco{" + "inicio=" + inicio[0] + "," + inicio[1] + ", largo=" + largo + ", orientacion=" + orientacion + " "+ b + '}';
    }        
            
}
