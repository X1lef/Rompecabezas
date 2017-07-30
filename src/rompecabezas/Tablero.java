/*
 * Copyright (C) 2017 Félix Pedrozo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package rompecabezas;

import static java.lang.Math.random;
import static java.lang.System.arraycopy;

public class Tablero {
    //Guarda las posiciones de las celdas.
    static int [][] tablero;

    //Guarda la posición de la celda vacia.
    static int [] poscVacio = new int [2];

    //Constante que representa al valor de la celda vacia.
    static final int VACIO = 16;

    static {
        tablero = new int [4][4];
        cargarTablero();
    }

    static void cargarTablero () {
        int valor = 1;

        //Cargo la matriz con numeros del 1 al 16.
        for (int f = 0; f < 4; f ++) {
            for (int c = 0; c < 4; c ++)
                tablero [f][c] = valor ++;
        }

        //Desordeno los elementos de la matriz.
        int fm, cm, aux;
        for (int f = 0; f < 4; f ++) {
            for (int c = 0; c < 4; c ++) {
                //Genera posición para realizar el intercambio.
                do {
                    fm = (int) (random() * 4);
                    cm = (int) (random() * 4);
                } while (f != fm && c != cm);

                //Realizar intercambio.
                aux = tablero [f][c];
                tablero [f][c] = tablero [fm][cm];
                tablero [fm][cm] = aux;
            }
        }

        hallarCeldaVacia();
    }

    //Determino la posición de la celda vacia (Celda con valor 16).
    private static void hallarCeldaVacia () {
        for (int f = 0; f < 4; f ++) {
            for (int c = 0; c < 4; c ++) {
                if (tablero [f][c] == VACIO) {
                    poscVacio [0] = f;
                    poscVacio [1] = c;
                    return;
                }
            }
        }
    }

    private void moverPiezaHorizontalmente (int c1, int c2) {
        final int fila = poscVacio [0];

        if (c1 > c2)
            //Mover hacia la derecha.
            arraycopy(tablero[fila], c2, tablero[fila], c2 + 1, c1 - c2);
        else
            //Mover hacia la izquierda.
            arraycopy(tablero [fila], c1 + 1, tablero [fila], c1, c2 - c1);

        tablero [fila][c2] = VACIO;
        //Actualizo posición de vacio.
        poscVacio [1] = c2;
    }
    
    private void moverPiezaVerticalmente (int f1, int f2) {
        final int colum  = poscVacio [1];

        if (f1 > f2) {
            //Mover hacia arriba.
            for (int i = f1; i > f2; i --)
                tablero [i][colum] = tablero [i - 1][colum];
        } else {
            //Mover hacia abajo.
            for (int i = f1; i < f2; i ++)
                tablero [i][colum] = tablero [i + 1][colum];
        }
        tablero [f2][colum] = VACIO;
        //Actualizo posición de vacio.
        poscVacio [0] = f2;
    }

    boolean moverPieza (int f, int c) {
        //Compruebo si la posición obtenida no es la celda vacia (celda con valor 16).
        if (f != poscVacio [0] || c != poscVacio [1]) {
            if (f == poscVacio[0]) {
                moverPiezaHorizontalmente(poscVacio[1], c);
                return true;

            } else if (c == poscVacio[1]) {
                moverPiezaVerticalmente(poscVacio[0], f);
                return true;
            }
        }

        //Retorna falso si no se ha podido mover la pieza.
        return false;
    }
}
