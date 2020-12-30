package Client.controller;

import common.Comunication.Connection;
import common.Comunication.Listener;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;

/**
 * <p>This is the server for the client, and the only way to communicate with it.</p>
 * */
public class ServerCommunication extends Connection {

    private static ServerCommunication serverCommunication;

    private  ServerCommunication(String ip, @Nullable Listener listener) throws IOException {
        super(new Socket(ip, 42069), listener);
    }

    public static ServerCommunication getServerCommunication() {
        if(serverCommunication == null) {
            try {
                serverCommunication = new ServerCommunication("LocalHost", null);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("No fue posible conectarse con el servidor");
            }
        }
        return serverCommunication;
    }

}
