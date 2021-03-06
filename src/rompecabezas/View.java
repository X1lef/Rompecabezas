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

import java.awt.*;
import javax.swing.*;
import static rompecabezas.Tablero.VACIO;
import static rompecabezas.Tablero.poscVacio;
import static rompecabezas.Tablero.tablero;

public class View extends JFrame {
    private JLabel[][] celdas = new JLabel [4][4];

    private final Controller controller;

    View () {
        setSize(300, 300);
        setTitle("Rompecabezas");
        setLocationRelativeTo(null);
        setLayout (new GridBagLayout());
        getContentPane().setBackground(new Color(210, 210, 210));
        setMinimumSize(new Dimension (300, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        controller = new Controller(this);

        GridBagConstraints conf = new GridBagConstraints();
        conf.gridx  = conf.gridy = 0;
        conf.weightx = conf.weighty = 1.0;
        conf.fill = GridBagConstraints.BOTH;
        conf.insets = new Insets(5, 5, 5, 5);

        add (panelDeTablero (), conf);

        setJMenuBar(barraDeMenu());

        setVisible(true);
    }

    //Comprueba si hay nuevos valores para actualizar la celda.
    void actualizarCeldas () {
        for (int f = 0; f < 4; f ++) {
            for (int c = 0; c < 4; c ++) {
                //Compruebo si el valor de la celda ha cambiado para actualizarlo.
                if (!Integer.toString(tablero[f][c]).equals(celdas[f][c].getText())) {
                    celdas[f][c].setText("" + tablero[f][c]);

                    //Escondo la celda numero 16
                    if (tablero [f][c] == VACIO)
                        celdas [f][c].setVisible (false);

                    //Hago visible la celda si no esta visible.
                    else if (!celdas [f][c].isVisible())
                        celdas[f][c].setVisible(true);
                }
            }
        }
    }

    private JMenuBar barraDeMenu () {
        //Creación de los item de menus.
        JMenuItem jmiNuevoJuego = new JMenuItem("Nuevo juego");
        jmiNuevoJuego.addActionListener(controller);
        JMenuItem jmiSalir = new JMenuItem ("Salir");
        jmiSalir.setActionCommand("jmiSalir");
        jmiSalir.addActionListener(controller);

        JMenu jmJuego = new JMenu("Juego");

        //Inserción de los item de menu.
        jmJuego.add(jmiNuevoJuego);
        jmJuego.add(new JPopupMenu.Separator());
        jmJuego.add(jmiSalir);

        JMenuBar jmb = new JMenuBar();
        jmb.add(jmJuego);

        return jmb;
    }

    private JPanel panelDeTablero () {
        JPanel panel = new JPanel (new GridLayout(4, 4, 4, 4));
        panel.setBackground(new Color(210, 210, 210));

        for (int f = 0; f < 4; f ++) {
            for (int c = 0; c < 4; c ++) {
                celdas [f][c] = celdaConfig("" + tablero [f][c], String.format("%d,%d", f, c));

                if (tablero [f][c] == VACIO)
                    celdas [f][c].setVisible(false);

                //Agrego al panel el label.
                panel.add (celdas [f][c]);
            }
        }

        return panel;
    }

    private JLabel celdaConfig (String text, String name) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setOpaque(true);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.addMouseListener(controller);
        label.setForeground(new Color(30, 30, 30));
        label.setName(name);
        label.setFont(new Font("Tahoma", Font.BOLD, 25));
        label.setBorder(BorderFactory.createEtchedBorder());

        return label;
    }

    boolean gano () {
        int valor = 0;

        for (JLabel [] fila : celdas) {
            for (JLabel celda : fila) {
                valor ++;

                if (!Integer.toString(valor).equals(celda.getText()))
                    return false;

                if (valor == 15) return true;
            }
        }

        return true;
    }

    //Mostrar celda numero 16.
    void mostrarCeldaVacia () {
        celdas [poscVacio [0]][poscVacio [1]].setVisible(true);
    }
}
