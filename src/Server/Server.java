package Server;


import common.Comunication.Connection;
import common.Comunication.Message;
import common.RunnableThread;

import java.util.ArrayList;

import static common.Comunication.IDMessage.*;

public class Server extends RunnableThread {

    private ArrayList<Player> players;
    private int turn;

    public Server() {
        players = new ArrayList<>();
        connectPlayers();// conectamos a todos
    }

    @Override
    public void execute() {

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));
        actionQueue.addAction(new Message((int)(Math.random()*100), DICE));
        actionQueue.addAction(new Message((int)(Math.random()*20),MOVE));
        actionQueue.addAction(new Message((int)(Math.random()*100), DICE));
        actionQueue.executeQueue();

        stopThread();
    }

    /**
     * <h1>Connects all players</h1>
     * */
    private void connectPlayers() {
        ServerConnection serverConnections = new ServerConnection(players);
        serverConnections.startThread();

        try {
            serverConnections.getThread().join(); // wait until they are connected
            players = serverConnections.getPlayers(); // players connected
            System.out.println("Se conectaron con exito: " + players.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h1>Server shutdown</h1>
     * */
    @Override
    public synchronized void stopThread() {
        super.stopThread();

        players.forEach(p -> p.sendMessageAndWait(END));
        players.forEach(Connection::closeConnection);
    }
}
