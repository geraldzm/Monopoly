package Server;


import common.Comunication.ChatConnection;
import common.Comunication.Connection;
import common.Comunication.Listener;
import common.Comunication.Message;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Predicate;

import static common.Comunication.IDMessage.LOGBOOK;
import static common.Comunication.IDMessage.MESSAGE;

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
