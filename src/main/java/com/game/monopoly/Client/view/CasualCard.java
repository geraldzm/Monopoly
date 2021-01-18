package com.game.monopoly.Client.view;

import javax.swing.*;
import java.awt.*;

public class CasualCard extends Card{

    private JTextArea string;

    public CasualCard(ImageIcon image, int id, String text) {
        super(image, id);
        string = new JTextArea();
        string.setEditable(false);//Puede escribir
        string.setOpaque(false);//Fondo
        string.setFont(new Font("Arial Black", Font.BOLD, 13));//Tipo de letra
        string.setForeground(Color.WHITE);//Color de la letra
        string.setBounds(50, 90, 1000, 100);//Setear texto despues en la carta
        string.setText(text);
        add(string, 2, 0);
    }

    public String getText(){
        return string.getText();
    }

}
