package com.game.monopoly.Client.model.Objects;

import java.util.*;

public class Players extends Token {
    private String name;
    private int token;

    private int ID;

    private ArrayList<Houses> houses;
    private ArrayList<Houses> hotel;
    private HashSet<Integer> cards;

    public Players(){
        super();

        houses = new ArrayList<>();
        hotel = new ArrayList<>();
        cards = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public ArrayList<Houses> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<Houses> houses) {
        this.houses = houses;
    }

    public ArrayList<Houses> getHotel() {
        return hotel;
    }

    public void setHotel(ArrayList<Houses> hotel) {
        this.hotel = hotel;
    }

    public HashSet<Integer> getCards() {
        return cards;
    }

    public void setCards(HashSet<Integer> cards) {
        this.cards = cards;
    }
    
    public int[] getCardsArray(){
        ArrayList<Integer> arr = new ArrayList<>();
        
        for (int i = 0; i < 39; i++) if (cards.contains(i)){
            arr.add(i);
        }
        int [] intArr = new int[arr.size()];
        
        for (int i = 0; i < arr.size(); i++){
            intArr[i] = arr.get(i);
        }
        
        return intArr;
    }
}
