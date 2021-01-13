package com.game.monopoly.Client.model;

public class Player {
    private static Player player;
    
    private String name;
    private String token;
    
    private int ID;
    
    private Player(String token) {
        this.token = token;
    }
    
    public static Player getInstance(){
        if (player == null){
            player = new Player(null);
        }
        
        return player;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        Player.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
