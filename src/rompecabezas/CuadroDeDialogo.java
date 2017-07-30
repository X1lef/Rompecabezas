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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CuadroDeDialogo extends JDialog implements ActionListener {
    private View view;

    CuadroDeDialogo(View padre) {
        super (padre, true);
        view = padre;

        setSize (300, 150);
        setResizable(false);
        setLocationRelativeTo(padre);
        setLayout(new GridBagLayout());
        setTitle("Gano el juego");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Juego nuevo.
                Tablero.cargarTablero();
                view.actualizarCeldas();
                dispose();
            }
        });

        GridBagConstraints conf = new GridBagConstraints();
        conf.gridx = conf.gridy = 0;
        conf.weighty = 1.0;
        conf.insets = new Insets (15, 6, 15, 6);

        JLabel mensaje = new JLabel ("¡Felicidades haz ganado.!");
        add (mensaje, conf);

        conf.gridy = 1;
        conf.weighty = 0.0;
        conf.weightx = 1.0;
        conf.insets = new Insets(0, 10, 10, 10);
        conf.fill = GridBagConstraints.HORIZONTAL;

        add (panelBotones(), conf);

        setVisible(true);
    }

    private JPanel panelBotones () {
        JPanel panel = new JPanel ();

        JButton jbJugarDeNuevo = new JButton("Jugar de nuevo");
        jbJugarDeNuevo.addActionListener(this);

        JButton jbSalir = new JButton ("Salir");
        jbSalir.setActionCommand("jbSalir");
        jbSalir.addActionListener(this);

        panel.add(jbSalir);
        panel.add(jbJugarDeNuevo);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("jbSalir"))
            System.exit(0);

        //Juego nuevo.
        else {
            Tablero.cargarTablero();
            view.actualizarCeldas();
        }
        dispose();
    }
}
