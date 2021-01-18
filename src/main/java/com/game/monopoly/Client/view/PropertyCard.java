package com.game.monopoly.Client.view;

import com.game.monopoly.Client.model.*;
import java.io.*;
import javax.swing.*;


public class PropertyCard extends Card {

    public static enum Type {
        BUY,SELL,NONE;
    }
    
    public JLabel sell, buy, mortgage;
    private int price;

    public PropertyCard(ImageIcon image, int id, int price, Type type) {
        super(image, id);

        this.price = price;

        switch (type) {
            case BUY -> buy = initJLabel("Comprar", 15, 310, 85, 20, 2);

            case SELL -> {
                sell = initJLabel("Vender", 15, 325, 75, 20, 2);
                mortgage = initJLabel("Hipotecar", 170, 325, 90, 20, 2);
            }
        }
    }



    private JLabel initJLabel(String text, int x, int y, int w, int h, int layer){
        JLabel label = new JLabel(text);
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

        return label;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

