package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.view.PropertyCard;

import java.util.*;

import static com.game.monopoly.Client.model.Constant.*;
import static com.game.monopoly.Client.model.Utils.contains;

public class Players extends Token {
    private String name;
    private int token;
    private boolean turn;
    private boolean hasCompletedRound;

    private int ID;

    private HashMap<Integer, Houses> houses;
    private HashMap<Integer, Houses> hotel;
    private HashSet<Integer> cards;

    public Players(){
        super();

        turn = false;
        hasCompletedRound = false;
        houses = new HashMap<>();
        hotel = new HashMap<>();
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

    // Retorna true si un jugador ha comprado un set de cartas
    public boolean hasBoughtSet(PropertyCard.Colors colors){
        if (isDebug) return true;

        switch (colors){
            case BROWN -> {
                return contains(getCardsArray(), brown);
            }
            case LIGHTBLUE -> {
                return contains(getCardsArray(), lightBlue);
            }
            case PINK -> {
                return contains(getCardsArray(), pink);
            }
            case ORANGE -> {
                return contains(getCardsArray(), orange);
            }
            case RED -> {
                return contains(getCardsArray(), red);
            }
            case YELLOW -> {
                return contains(getCardsArray(), yellow);
            }
            case GREEN -> {
                return contains(getCardsArray(), green);
            }
            case BLUE -> {
                return contains(getCardsArray(), blue);
            }
        }

        return false;
    }

    public HashMap<Integer, Houses> getHouses() {
        return houses;
    }

    public void setHouses(HashMap<Integer, Houses> houses) {
        this.houses = houses;
    }

    public HashMap<Integer, Houses> getHotel() {
        return hotel;
    }

    public void setHotel(HashMap<Integer, Houses> hotel) {
        this.hotel = hotel;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isHasCompletedRound() {
        return hasCompletedRound;
    }

    public void setHasCompletedRound(boolean hasCompletedRound) {
        this.hasCompletedRound = hasCompletedRound;
    }
}
