package com.game.monopoly.Client.model.Objects;

public class Player extends Players{
    private static Player player;
    private boolean rolledDices;
    private boolean isJailFree;
    private boolean isInJail;

    private Player(){
        rolledDices = false;
        isJailFree = false;
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
        return isJailFree;
    }

    public void setJailFree(boolean jailFree) {
        isJailFree = jailFree;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }
}
