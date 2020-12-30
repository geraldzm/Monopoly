package Server;

import common.Comunication.Connection;
import common.Comunication.Message;
import common.RunnableThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static common.Comunication.IDMessage.*;

/**
 * <h1>Connects the with the players</h1>
 * */
public class ServerConnection extends RunnableThread {

    private ServerSocket serverSocket;
    private Connection admin; //
    private ArrayList<Player> players;
    private int maxPlayers;

    public ServerConnection(ArrayList<Player> players) {

        try {
            this.serverSocket = new ServerSocket(42069);
        } catch (IOException e) {
            System.err.println("Puerto 42069 en uso");
            e.printStackTrace();
        }

        this.maxPlayers = -1;
        this.players = players;
    }

    @Override
    public void execute() {
        try {

            if(players.size() == 0) System.out.println("waiting for admin to connect...");
            else System.out.println("waiting connection...\tcurrent number of connections: " + players.size());

            Socket newClient  = serverSocket.accept();
            Player player = new Player(newClient);

            if (players.size() == 0) { // the first client is the admin
                players.add(player);

                // ask the amount of players
                admin = player;
                admin.setListener(message -> maxPlayers = message.getNumber());
                admin.setReceiverFilter(m -> m.getIdMessage() == RESPONSE);
                admin.sendMessage(ADMIN);

            } else if(players.size() < maxPlayers) {
                addPlayer(player);
            } else {
                // lo rechazamos
               player.sendMessageAndWait(REJECTED);
               player.closeConnection();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al connectar un cliente");
        }

    }

    private void addPlayer(Player player){
        players.add(player);
        player.sendMessage(ACCEPTED);
        if(players.size() == maxPlayers) initGame(); // if all players really then start
    }


    /**
     * <p>Notify all players that every one is really to start</p>
     * */
    private void initGame() {
        System.out.println("Iniciando!");

        ActionQueue queue = new ActionQueue(new ArrayList<>(players));
        queue.addAction(new Message(STARTED));
        queue.executeQueue();

        System.out.println("Todos listos!");
        stopThread();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
