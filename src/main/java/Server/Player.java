package main.java.Server;


import main.java.common.Comunication.ChatConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Predicate;


/**
 * <h1>ClientServer</h1>
 * */
public class Player extends ChatConnection {

    private int cash, id;
    private String name;
    private boolean go; //if he has gone through GO
    private static int count;

    public Player(Socket socket) throws IOException {
        super(socket, null);
        id = count++;
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

    public boolean isGo() {
        return go;
    }
}