package main.java.Server;


import main.java.common.Comunication.Connection;
import main.java.common.Comunication.Message;
import main.java.common.RunnableThread;

import java.util.ArrayList;

import static main.java.common.Comunication.IDMessage.*;

public class Server extends RunnableThread {


    private ArrayList<Player> players;
    private int turn;

    public Server() {
        players = new ArrayList<>();
        connectPlayers();// conectamos a todos
        gameInit(); // settiamos el juego
    }

    @Override
    public void execute() {
        // Game starts

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));
        actionQueue.addAction(new Message((int)(Math.random()*100), DICE));
        actionQueue.addAction(new Message((int)(Math.random()*20),MOVE));
        actionQueue.addAction(new Message((int)(Math.random()*100), DICE));
        actionQueue.executeQueue();

        stopThread();
    }

    /**
     * <h3>Game configs</h3>
     * */
    private void gameInit() {

        // 1. assign ids
        ArrayList<Message> messages = new ArrayList<>();
        players.forEach(player ->{ // each player has a different message
            player.setId(messages.size());
            messages.add(new Message(player.getId(), ID));
        });

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));
        actionQueue.addAction(messages, null, DONE);
        actionQueue.executeQueue();

        System.out.println("IDs asignados");

        // 2. request each player a name
        actionQueue.addAction(new Message(NAME), message -> players.get(message.getNumber()).setName(message.getString()));
        actionQueue.executeQueue();

        System.out.println("Nombres recibidos: ");
        players.forEach(p -> System.out.println(p.getId() +"  "+ p.getName())); // print ID Name

        // 3. init chat
        players.forEach(p -> p.setChatListener(m -> {
            players.forEach(p2 -> p2.sendChatMessage(m));
        }));


        // 4. tiramos dados y ordenamos turno

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
     * <h1>main.java.Server shutdown</h1>
     * */
    @Override
    public synchronized void stopThread() {
        super.stopThread();

        players.forEach(p -> p.sendMessageAndWait(END));
        players.forEach(Connection::closeConnection);
    }


    public static void main(String[] args) {
        new Server().startThread();
    }

}
