package com.game.monopoly.Server;



import com.game.monopoly.common.Comunication.Message;
import com.game.monopoly.common.RunnableThread;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.game.monopoly.common.Comunication.IDMessage.*;


public class Server extends RunnableThread {


    private Hashtable<Integer, Player> players;
    private int turn;

    public Server() {
        players = new Hashtable<>();
        gameInit(connectPlayers()); // conectamos a todos && settiamos el juego

    }

    @Override
    public void execute() {
        // Game starts


        stopThread();
    }

    /**
     * <h3>Game configs</h3>
     * */
    private void gameInit(ArrayList<Player> players) {

        // 1. assign ids
        ArrayList<Message> messages = new ArrayList<>();
        players.forEach(player ->{ // each player has a different message
            player.setId(messages.size());
            messages.add(new Message(player.getId(), ID));
        });

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));
        actionQueue.addAction(messages, null, DONE);
        actionQueue.executeQueue();

        // 2. request each player a name
        actionQueue.addAction(new Message(NAME), message -> players.get(message.getNumber()).setName(message.getString()));
        actionQueue.executeQueue();

        // 3. send names
        actionQueue.addAction(new Message( getNamesFromPlayers(players), NAMES));
        actionQueue.executeQueue();

        // 4. init chat
        players.forEach(p -> p.setChatListener(m -> {
            players.forEach(p2 -> p2.sendChatMessage(m, p));
        }));

        // 5. sort
        sortByTurn(players, players, new AtomicInteger(1));
    }

    private String getNamesFromPlayers(ArrayList<Player> players) {
        StringBuilder names = new StringBuilder();
        players.forEach(p -> names.append(p.getName()).append(","));

        return names.toString();
    }


    /**
     * Adds all the players to hasTable, id=turn value=player
     * */
    private void sortByTurn(ArrayList<Player> all, ArrayList<Player> players, AtomicInteger turn) {
        if(players.size() == 0) return;
        if(players.size() == 1){
            this.players.put(turn.getAndIncrement(), players.get(0));
            System.out.println("Se le asigna el turno a : " +  players.get(0).getName()+ " " +  (turn.get()-1));
            return;
        }

        TreeMap<Integer, ArrayList<Player>> list = new TreeMap<>();

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));
        actionQueue.addAction(rollAllDices(players), null, DONE);
        actionQueue.executeQueue();


        // 1. creamos cadena de nombres
        StringBuilder names = new StringBuilder();
        players.forEach(player -> {
            names.append(player.getName()).append(",");
        });

        // 2. sacamos los dados de todos
        int [] results = new int[3*players.size()];
        int index = 0;
        for (int i = 0; i < players.size(); i++) {
            int[] dices = players.get(i).getDices();
            results[index++] = dices[0];
            results[index++] = dices[1];
            results[index++] = dices[2];
        }

        //le informamos a todos:
        ActionQueue actionQueueAll = new ActionQueue(new ArrayList<>(all));
        actionQueueAll.addAction(new Message(results, getNamesFromPlayers(players), DICES));
        actionQueue.executeQueue();

        //1. tiramos los dados de todos
        for (Player player : players) {
            int[] dices = player.getDices(); // ya los tiramos en rollAllDices

            if (list.containsKey(dices[2]))
                list.get(dices[2]).add(player);
            else
                list.put(dices[2], new ArrayList<>(Arrays.asList(player)));
        }

        List<Integer> keys = list.entrySet().stream() // se ordenan las llaves de mayor a menor
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        for (int i: keys) { // se recorre del mas alto al mas bajo
            sortByTurn(all, list.get(i), turn);
        }
    }


    /**
     * tira los dados de todos los jugadores
     * */
    private ArrayList<Message> rollAllDices(ArrayList<Player> players) {
        ArrayList<Message> messages = new ArrayList<>();

        players.forEach(p -> {
            p.rollDices();
            messages.add(new Message(p.getDices(), DICE));
        });

        return messages;
    }

    /**
     * <h1>Connects all players</h1>
     * */
    private ArrayList<Player> connectPlayers() {

        ServerConnection serverConnections = new ServerConnection();
        serverConnections.startThread();

        try {
            serverConnections.getThread().join(); // wait until they are connected
            System.out.println("Se conectaron con exito: " + serverConnections.getPlayers().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serverConnections.getPlayers();
    }

    /**
     * <h1>main.java.Server shutdown</h1>
     * */
    @Override
    public synchronized void stopThread() {
        super.stopThread();

        players.forEach((i,p) -> p.sendMessageAndWait(END));
        players.forEach((i,p) -> p.closeConnection());
    }


    public static void main(String[] args) {
        new Server().startThread();
    }

}
