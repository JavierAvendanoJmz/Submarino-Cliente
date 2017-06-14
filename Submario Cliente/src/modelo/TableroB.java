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
public class TableroB extends Tablero {        
    
    public boolean disparar(int f,int c) {
        if(fueraDeMapa(f,c) || mapa[f][c] == 1)
            return false;
        else{
            mapa[f][c] = 1;
            return true;
        }
    }

}
