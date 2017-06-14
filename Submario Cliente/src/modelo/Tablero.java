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
public class Tablero {
    
    protected int[][] mapa;

    public Tablero() {
        this.mapa = new int[10][10];
    }
    
    protected boolean fueraDeMapa(int f, int c) {
        return (f < 0 || c < 0 || f > 9 || c > 9);
    }
    
    public void mostrarTablero(){
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public int[][] getMapa() {
        return mapa;
    }
    
}
