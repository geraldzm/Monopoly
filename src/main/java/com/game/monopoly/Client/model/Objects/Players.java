package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.controller.GameListener;
import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.PropertyCard;

import java.awt.*;
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

    @Override
    public void render(Graphics g) {
        if (canStart()){
            g.drawImage(getImg().getImage(), pos.x, pos.y, null);

            Color color = g.getColor();

            g.setColor(Color.white);
            g.fillRect(pos.x - 4, pos.y - 25, 40, 20);

            g.setColor(Color.BLACK);
            g.drawString(getName(), pos.x, pos.y - 10);

            g.setColor(color);
        }
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

        return contains(getCardsArray(), Utils.getCardArrayByColor(colors));
    }

    public static boolean hasColorInSet(PropertyCard.Colors colors){
        Players admin = GameListener.getInstance().getPlayers().get(0);

        int[] colorArray = Utils.getCardArrayByColor(colors);

        for (int value : colorArray){
            boolean hasCard = Player.getInstance().getCards().contains(value);
            boolean hasHouses = admin.getHouses().containsKey(value) && admin.getHouses().get(value).getAmountHouse() != 0;
            boolean hasHotels = admin.getHotel().containsKey(value) && admin.getHotel().get(value).getAmountHouse() != 0;

            if (hasCard || hasHouses || hasHotels)
                return true;
        }

        return false;
    }

    public static boolean enemiHasCard(int cardID){
        var players = GameListener.getInstance().getPlayers();

        for (int i = 0; i < 6; i++){
            if (!players.containsKey(i) || Player.getInstance().getID() == i) continue;

            if (players.get(i).getCards().contains(cardID)) return true;
        }

        return false;
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
