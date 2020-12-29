package Server;

import com.monopoly.common.Comunication.Connection;
import com.monopoly.common.Comunication.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ClientServer</h1>
 * */
public class Player extends Connection {

    private int cash;
    private boolean go; //if he has gone through GO

    public Player(@NotNull Socket socket) throws IOException {
        super(socket, null);

    }


}
