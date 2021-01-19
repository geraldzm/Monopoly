package com.game.monopoly.Client.model.Objects;

public class Player extends Players{
    private static Player player;
    private boolean rolledDices;

    private Player(){
        rolledDices = false;
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
}
