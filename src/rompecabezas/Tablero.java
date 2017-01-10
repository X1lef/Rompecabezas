package rompecabezas;

import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.Math.random;

public class Tablero {
    private int [][] tablero = new int [4][4];
    private int [] poscVacio = new int [2];
    public static final int VACIO = 16;

    Tablero () {
        cargarTablero();
        mostrarTablero();
        hallarCeldaVacia();
    }

    private void cargarTablero () {
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
    }

    private void hallarCeldaVacia () {
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

    void mostrarTablero () {
        for (int f = 0; f < 4; f ++) {
            for (int c = 0; c < 4; c ++)
                out.print (tablero [f][c] + " | ");
            out.println ();
        }
    }

    public void moverPiezaHorizontal (int c1, int c2) {
        final int fila = poscVacio [0];

        if (c1 > c2) {
            //Mover hacia la derecha.
            for (int i = c1; i > c2; i --)
                tablero [fila][i] = tablero [fila][i - 1];
        } else {
            //Mover hacia la izquierda.
            for (int i = c1; i < c2; i ++)
                tablero [fila][i] = tablero [fila][i + 1];
        }
        tablero [fila][c2] = VACIO;
        //Actualizo posición de vacio.
        poscVacio [1] = c2;
    }
    
    public void moverPiezaVerticalmente (int f1, int f2) {
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

    public void celdaPulsada (int f, int c) {
        if (f != poscVacio [0] || c != poscVacio [1]) {
            if (f == poscVacio[0])
                moverPiezaHorizontal(poscVacio[1], c);

            else if (c == poscVacio[1])
                moverPiezaVerticalmente(poscVacio[0], f);
        }
    }

    public static void main (String [] args) {
        Tablero t = new Tablero();

        Scanner leer = new Scanner(System.in);

        int f, c;
        while (true) {
            out.print ("Fila : ");
            f = leer.nextInt();
            out.print ("Columna : ");
            c = leer.nextInt();

            t.celdaPulsada(f, c);
            t.mostrarTablero();
            out.println ("-------------------------------------------");
        }
    }
}
