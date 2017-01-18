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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Controller extends MouseAdapter implements ActionListener {
    private View view;
    private Tablero tablero;

    Controller (View view) {
        this.view = view;
        tablero = new Tablero();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();
        String [] posc = label.getName().split(",");

        int f = Integer.parseInt(posc [0]);
        int c = Integer.parseInt(posc [1]);

        if (tablero.moverPieza(f, c)) {
            view.actualizarCeldas();

            if (view.gano ()) {
                view.mostrarCeldaVacia();
                new CuadroDeDialogo(view);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand ().equals("jmiSalir"))
            System.exit(0);

        else {
            Tablero.cargarTablero();
            view.actualizarCeldas();
        }
    }
}
