package com.game.monopoly.Client.view;

import com.game.monopoly.Client.model.Utils;

import javax.swing.*;
import java.io.IOException;


public class PropertyCard extends Card {

    public static enum Type {
        BUY,SELL,NONE;
    }

    public JLabel sell, buy, mortgage;

    public PropertyCard(ImageIcon image, int id, Type type) {
        super(image, id);

        switch (type) {
            case BUY -> initJLabel("Comprar", 15, 310, 85, 20, buy, 2);

            case SELL -> {
                initJLabel("Vender", 15, 310, 75, 20, sell, 2);
                initJLabel("Hipotecar", 140, 310, 90, 20, mortgage, 2);
            }
        }
    }

    private void initJLabel(String text, int x, int y, int w, int h, JLabel label, int layer){
        label = new JLabel(text);
        label.setBounds(x+5, y, w, h);
        label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label.setForeground(new java.awt.Color(255, 255, 255));

        JLabel backGround = new JLabel();
        backGround.setBounds(x, y, w, h);
        try {
            backGround.setIcon(Utils.getComponentIcon("ButtonsBG.png", w, h));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(label, layer+1,0);
        add(backGround, layer,0);
    }

}

