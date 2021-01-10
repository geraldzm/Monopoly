package com.game.monopoly.Server;


import com.game.monopoly.common.Comunication.ChatConnection;
import com.game.monopoly.common.Comunication.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * <h1>ClientServer</h1>
 * */
public class Player extends ChatConnection {

    private int cash, id;
    private String name;
    private boolean go; //if he has gone through GO
    private static int count;
    private int[] dices;

    public Player(Socket socket) throws IOException {
        super(socket, null);
        id = count++;
    }


    public void sendChatMessage(Message message){
        sendMessage(new Message(name + ": " +message.getString(), message.getIdMessage()));
    }

    public int[] rollDices() {
        Random random = new Random();
        dices = new int[3];
        dices[0] = random.nextInt(6)+1;
        dices[1] = random.nextInt(6)+1;
        dices[2] = dices[1] + dices[0];

        return dices;
    }

    //-----------Setters/Getters-----------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCash() {
        return cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGo() {
        return go;
    }

    public int[] getDices() {
        return dices;
    }

    public void setDices(int[] dices) {
        this.dices = dices;
    }
}
