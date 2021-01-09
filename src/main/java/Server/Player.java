package main.java.Server;


import main.java.common.Comunication.ChatConnection;
import main.java.common.Comunication.Message;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

/**
 * <h1>ClientServer</h1>
 * */
public class Player extends ChatConnection {

    private int cash, id;
    private String name;
    private boolean go; //if he has gone through GO
    private static int count;

    public Player(@NotNull Socket socket) throws IOException {
        super(socket, null);
        id = count++;
    }


    public void sendChatMessage(Message message){
        sendMessage(new Message(name + ": " +message.getString(), message.getIdMessage()));
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
}
