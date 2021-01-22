package com.game.monopoly.Client.view;

import com.game.monopoly.Client.controller.ServerCommunication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class CasualCard extends Card{

    private JTextArea string;
    private JButton accept;
    private JFrame parent;

    public CasualCard(ImageIcon image, int id, String text) {
        super(image, id);
        string = new JTextArea();
        string.setEditable(false);//Puede escribir
        string.setOpaque(false);//Fondo
        string.setFont(new Font("Arial Black", Font.BOLD, 13));//Tipo de letra
        string.setForeground(Color.WHITE);//Color de la letra
        string.setBounds(50, 90, 1000, 100);//Setear texto despues en la carta
        string.setText(text);
        accept = new JButton();
        accept.setBounds(150, 250, 100, 25);

        accept.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(parent != null) parent.dispose();
                    ServerCommunication.getServerCommunication().sendDone();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        accept.setText("Aceptar");

        add(accept, 3, 0);
        add(string, 2, 0);
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public String getText(){
        return string.getText();
    }

}
