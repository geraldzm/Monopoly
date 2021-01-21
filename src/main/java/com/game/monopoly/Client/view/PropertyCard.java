package com.game.monopoly.Client.view;

import com.game.monopoly.Client.model.*;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import javax.swing.*;


public class PropertyCard extends Card {

    public static enum Type {
        BUY,SELL,NONE, JAIL;
    }

    public static enum Colors{
        BROWN, LIGHTBLUE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE;
    }

    
    public JLabel sell, buy, mortgage, sellHouse, buyHouse, sellHotel, buyHotel, freeJail, payTaxes;
    private int price, houseAmount, hotelAmount, houseCost, hotelCost;
    private Colors color;

    private int[] prices; // valores a pagar si alguien cae ahi, deben ser 5

    public PropertyCard(ImageIcon image, int id, int price, Type type, Colors color, int[] prices) {
        super(image, id);

        this.price = price;
        this.houseAmount = 0;
        this.hotelAmount = 0;
        
        this.houseCost = 0;
        this.hotelCost = 0;
        
        this.color = color;
        this.prices = prices;

        if (Utils.isUselessCard(id))
            type = Type.NONE;

        if (id == 10)
            type = Type.JAIL;

        switch (type) {
            case BUY -> buy = initJLabel("Comprar", 40, 350, 85, 20, 2);

            case SELL -> {
                sell = initJLabel("Vender", 10, 280, 120, 25, 2);
                mortgage = initJLabel("Hipotecar", 145, 280, 120, 25, 2);

                sellHouse = initJLabel("Vender casa", 10, 310, 120, 25, 2);
                buyHouse = initJLabel("Comprar casa", 145, 310, 120, 25, 2);
            }

            case JAIL -> {
                freeJail = initJLabel("Salir de la carcel", 10, 310, 120, 25, 2);
                payTaxes = initJLabel("Pagar multas", 145, 310, 120, 25, 2);
            }
        }
        
        switch (color) {
            case BROWN, LIGHTBLUE -> {houseCost = 50; hotelCost = 50;}
            case PINK, ORANGE -> {houseCost = 100; hotelCost = 100;}
            case RED, YELLOW -> {houseCost = 150; hotelCost = 150;}
            case GREEN, BLUE -> {houseCost = 200; hotelCost = 200;}
        }
    }

    private JLabel initJLabel(String text, int x, int y, int w, int h, int layer){
        JLabel label = new JLabel(text);
        label.setBounds(x+5, y, w, h);
        label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label.setForeground(new java.awt.Color(255, 255, 255));

        JLabel backGround = new JLabel();
        backGround.setBounds(x, y, w, h);

        label.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                backGround.setVisible(false);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                backGround.setVisible(true);
            }
        });

        try {
            backGround.setIcon(Utils.getComponentIcon("ButtonsBG.png", w, h));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(label, layer+1,0);
        add(backGround, layer,0);

        return label;
    }


    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    public int getPriceToPay() { // lo que paga si alguien cae en esa posicion
        if (hotelAmount == 1) return prices[5];
        return switch (houseAmount) {
            case 1 -> prices[1];
            case 2 -> prices[2];
            case 3 -> prices[3];
            case 4 -> prices[4];
            default -> prices[0];
        };
    }

    public int getMortgagePrice(){
        return price/2;
    }

    public void decreaseHouseAmount(){
        houseAmount--;
    }

    public void decreaseHotelAmount(){
        houseAmount--;
    }

    public void increaseHouseAmount(){
        houseAmount++;
    }

    public void increaseHotelAmount(){
        hotelAmount++;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHouseAmount() {
        return houseAmount;
    }

    public void setHouseAmount(int houseAmount) {
        this.houseAmount = houseAmount;
    }

    public int getHotelAmount() {
        return hotelAmount;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setHotelAmount(int hotelAmount) {
        this.hotelAmount = hotelAmount;
    }

    public Colors getColor() {
        return color;
    }

}

