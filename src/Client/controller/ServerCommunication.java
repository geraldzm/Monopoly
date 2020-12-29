package Client.controller;


import com.monopoly.common.Comunication.Connection;
import com.monopoly.common.Comunication.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;

/**
 * <p>This is the server for the client, and the only way to communicate with it.</p>
 * <p></p>
 * */
public class ServerCommunication extends Connection {

    public ServerCommunication(String ip, @Nullable Listener listener) throws IOException {
        super(new Socket(ip, 42069), listener);
    }

    public ServerCommunication(@NotNull Socket socket, @Nullable Listener listener) throws IOException {
        super(socket, listener);
    }

}
