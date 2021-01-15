package com.game.monopoly.Client.model.Objects;

public class Player extends Players{
    private static Player player;
    
    private Player(){
    }
    
    public static Player getInstance(){
        if (player == null){
            player = new Player();
        }
        
        return player;
    }
}
