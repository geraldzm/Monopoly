package Server;

import common.Comunication.Connection;
import common.Comunication.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static common.Comunication.IDMessage.MESSAGE;

public class Chat {

    private List<Connection> users;

    public Chat(ArrayList<Player> players) {

        Listener listener = m -> users.forEach(u -> u.sendMessage(m)); // send the message to everyone

        users = players.stream()
                .map(p -> { // new channel
                    try {
                        Connection c = new Connection(p.getSocket(), listener);
                        c.setReceiverFilter(m -> m.getIdMessage() == MESSAGE);
                        return c;
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Fallo la conneccion con el Chat");
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
