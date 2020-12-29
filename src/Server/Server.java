package Server;


import common.RunnableThread;

import java.util.ArrayList;

public class Server extends RunnableThread {

    private ArrayList<Player> players;
    private int turn;

    public Server() {
        players = new ArrayList<>();
        connectPlayers();
    }

    @Override
    public void execute() {


    }

    /**
     * <h1>Connects all players</h1>
     * */
    private void connectPlayers() {
        ServerConnection serverConnections = new ServerConnection(players);
        serverConnections.startThread();

        try {
            serverConnections.getThread().join(); // wait until they are ready
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
