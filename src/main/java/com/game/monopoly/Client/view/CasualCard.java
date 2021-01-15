/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.monopoly.Client.view;


import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/**
 *
 * @author fgm_o
 */
public class CasualCard extends Card{
    
    private JTextArea  string;

    public CasualCard(ImageIcon image, int id, String text) {
        super(image, id);
        string = new JTextArea();
        string.setEditable(false);//Puede escribir
        string.setOpaque(false);//Fondo
        string.setFont(new Font("Arial Black", Font.BOLD, 13));//Tipo de letra
        string.setForeground(Color.black);//Color de la letra
        string.setBounds(50, 90, 1000, 100);//Setear texto despues en la carta
        string.setText(text);
        add(string);
    }
    
    public String getText(){
        return string.getText();
    }
    
}
