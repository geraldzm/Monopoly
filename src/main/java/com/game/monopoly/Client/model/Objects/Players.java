package com.game.monopoly.Client.model.Objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Players extends Token {
    private String name;
    private int token;

    private int ID;

    private ArrayList<Houses> houses;
    private ArrayList<Houses> hotel;

    public Players(){
        super();

        houses = new ArrayList<>();
        hotel = new ArrayList<>();
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
}
