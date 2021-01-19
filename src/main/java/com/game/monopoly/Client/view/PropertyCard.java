package com.game.monopoly.Client.view;

import com.game.monopoly.Client.model.*;
import java.io.*;
import javax.swing.*;


public class PropertyCard extends Card {

    public static enum Type {
        BUY,SELL,NONE, HOUSES;
    }

    public static enum Colors{
        BROWN, LIGHTBLUE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE;
    }
    
    /*
    "cafe" : [1, 3],
    "celeste" : [6,8,9],
    "rosa" : [11, 13, 14],
    "naranja" : [16,18,19],
    "rojo" : [21, 23, 24],
    "amarillo" : [26, 27, 29],
    "verde" : [31, 32, 34],
    "azul" : [37, 39]
    * */

    /*
    * PRECIO de las cuatro casas getPricetoPay()
    * precio de hotel  getPricetoPay()
    * precio hipoteca getMortgage()
    * valor de una casa gethouseCost()
    * valor de un hotel gethotelCost()
    * */
    
    public JLabel sell, buy, mortgage, sellHouse, buyHouse, sellHotel, buyHotel;
    private int price, houseAmount, hotelAmount,houseCost,hotelCost;
    private Colors color;

    private int[] prices; // valores a pagar si alguien cae ahi, deben ser 5

    public PropertyCard(ImageIcon image, int id, int price, Type type,Colors color,int[] prices) {
        super(image, id);

        this.price = price;
        this.houseAmount = 0;
        this.hotelAmount = 0;
        
        this.houseCost = 0;
        this.hotelCost = 0;
        
        this.color = color;
        this.prices = prices;

        switch (type) {
            case BUY -> buy = initJLabel("Comprar", 15, 310, 85, 20, 2);

            case SELL -> {
                sell = initJLabel("Vender", 20, 280, 100, 25, 2);
                mortgage = initJLabel("Hipotecar", 150, 280, 100, 25, 2);

                sellHouse = initJLabel("Vender casas", 20, 310, 100, 25, 2);
                buyHouse = initJLabel("Comprar casas", 150, 310, 100, 25, 2);

                sellHotel = initJLabel("Vender hotel", 20, 340, 100, 25, 2);
                buyHotel = initJLabel("Comprar hotel", 150, 340, 100, 25, 2);
            }
        }
        
        switch (color) {
            case BROWN -> {houseCost = 50; hotelCost = 50;}
            case LIGHTBLUE -> {houseCost = 50; hotelCost = 50;}       
            case PINK -> {houseCost = 100; hotelCost = 100;}     
            case ORANGE -> {houseCost = 100; hotelCost = 100;}     
            case RED -> {houseCost = 150; hotelCost = 150;} 
            case YELLOW -> {houseCost = 150; hotelCost = 150;}     
            case GREEN -> {houseCost = 200; hotelCost = 200;}     
            case BLUE -> {houseCost = 200; hotelCost = 200;}     
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


    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    public int getPriceToPay() { // lo que paga si alguien cae en esa posicion
        switch (houseAmount) {
            case 1: return prices[1];
            case 2: return prices[2];
            case 3: return prices[3];
            case 4: return prices[4];
        }
        if (hotelAmount == 1) return prices[5];
        return prices[0];
    }

    public int getMortgagePrice(){
        return price/2;
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

