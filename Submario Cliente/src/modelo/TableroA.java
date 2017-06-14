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
public class TableroA extends Tablero {
        
    private int contador;
    private Barco[] barcos;
    private boolean derrota;    

    public TableroA(int b) {
        super();
        contador = 1;
        barcos = new Barco[b];
        for (int i = 0; i < b; i++)
            barcos[i] = new Barco();
        derrota = false;
    }       
    
    public boolean colocarBarco(int f, int c, Barco barco) {        
        if(fueraDeMapa(f,c) ||
                (barco.getOrientacion() == 'v' && f + barco.getLargo() > 10) ||
                (barco.getOrientacion() == 'h' && c + barco.getLargo() > 10))
            return false;
        else{
            if(barco.getOrientacion() == 'v') {
                for (int i = 0; i < barco.getLargo(); i++) {                 
                    if(mapa[f+i][c] > 0)
                        return false;
                }
                for (int i = 0; i < barco.getLargo(); i++)
                    mapa[f+i][c] = contador;
                
            } else {
                for (int i = 0; i < barco.getLargo(); i++) {
                    if(mapa[f][c+i] > 0)
                        return false;
                }
                for (int i = 0; i < barco.getLargo(); i++)
                    mapa[f][c+i] = contador;
            }
        }
        int[] aux = {f,c};
        barco.setInicio(aux);
        barcos[contador-1] = barco;
//        System.out.println("Contador: " + contador);
//        System.out.println("Barco: " + barcos[contador-1]);
        contador++;
        return true;
    }
    
    public int recibirDisparo(int f, int c) {
        if(fueraDeMapa(f,c))
            return 0;
        else if(mapa[f][c] > 0 ) {
            Barco aux = barcos[(mapa[f][c])-1];
            mapa[f][c] = -1;
            if(aux.getOrientacion() == 'h') {
                if(aux.recibirDisparo(c-aux.getInicio()[1]))
                    return 2;
            }
            else {
                if(aux.recibirDisparo(f-aux.getInicio()[0]))
                    return 2;
            }
            return 1;
        } else
            return 0;
    }

    public boolean isDerrota() {
        for (int i = 0; i < barcos.length; i++) {
            System.out.println(barcos[i]);
            if(!barcos[i].isDestruido())
                return false;
        }
        return true;
    }

    @Override
    public void mostrarTablero() {
        super.mostrarTablero();
        System.out.println("Barcos: " + (contador-1));
        for (int i = 0; i < contador-1; i++) {
            System.out.println(barcos[i]);
        }
    }
    
    
}
