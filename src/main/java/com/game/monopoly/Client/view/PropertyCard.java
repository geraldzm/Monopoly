package com.game.monopoly.Client.view;

import javax.swing.*;


public class PropertyCard extends Card {

    public static enum Type {
        BUY,SELL,NONE;
    }

    private JButton sell;
    private JButton buy;
    private JButton mortgage;

    public PropertyCard(ImageIcon image, int id, Type type) {
        super(image, id);

        switch (type){
            case BUY -> {
                buy = new JButton("Comprar");
                buy.setBounds(15, 310, 85, 20);
                add(buy);
            }
            case SELL -> {
                sell = new JButton("Vender");
                mortgage = new JButton("Hipotecar");

                sell.setBounds(15, 310, 75, 20);
                mortgage.setBounds(140, 310, 90, 20);

                add(sell);
                add(mortgage);
            }
        }
    }

}

