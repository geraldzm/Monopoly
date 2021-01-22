package com.game.monopoly.Client.model.Objects;

import java.util.ArrayList;

public class Player extends Players{
    private static Player player;
    private boolean rolledDices;
    private boolean isInJail;
    private ArrayList<Integer> isJailFree;

    private Player(){
        rolledDices = false;
        isJailFree = new ArrayList<>();
        isInJail = false;
    }
    
    public static Player getInstance(){
        if (player == null){
            player = new Player();
        }
        
        return player;
    }

    public boolean isRolledDices() {
        return rolledDices;
    }

    public void setRolledDices(boolean rolledDices) {
        this.rolledDices = rolledDices;
    }

    public boolean isJailFree() {
        return !isJailFree.isEmpty();
    }

    public void setJailFree() {
        isJailFree.add(1);
    }

    public void removeJailFree() {
        isJailFree.remove(0);
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }
}
