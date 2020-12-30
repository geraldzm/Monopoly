package Server;


import common.Comunication.Connection;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

/**
 * <h1>ClientServer</h1>
 * */
public class Player extends Connection {

    private int cash, id;
    private boolean go; //if he has gone through GO
    private static int count;

    public Player(@NotNull Socket socket) throws IOException {
        super(socket, null);
        id = count++;
    }


}
