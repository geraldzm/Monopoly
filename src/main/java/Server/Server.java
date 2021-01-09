package main.java.Server;


import main.java.common.Comunication.Connection;
import main.java.common.Comunication.Message;
import main.java.common.RunnableThread;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static main.java.common.Comunication.IDMessage.*;

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
        System.out.println("Jugadores: ");
        players.values().forEach(p -> System.out.println("\t ["+p.getName()+ " " + p.getDices()[0]+" " + p.getDices()[1]+" " + p.getDices()[2] + "]"));


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

        System.out.println("IDs asignados!");

        // 2. request each player a name
        actionQueue.addAction(new Message(NAME), message -> players.get(message.getNumber()).setName(message.getString()));
        actionQueue.executeQueue();

        System.out.println("Nombres recibidos: ");
        players.forEach(p -> System.out.println(p.getId() +"  "+ p.getName())); // print ID Name


        // 3. init chat
        players.forEach(p -> p.setChatListener(m -> players.forEach(p2 -> p2.sendChatMessage(m))));

        sortByTurn(players, new AtomicInteger(1));
    }


    /**
     * Adds all the players to hasTable, id=turn value=player
     * */
    private void sortByTurn(ArrayList<Player> players, AtomicInteger turn) {
        System.out.println("Entran: " + players.size() + " turno: " + turn+ "{ ");
        players.forEach(p -> System.out.println("\tName: " + p.getName()));
        System.out.println("}");

        if(players.size() == 0) return;
        if(players.size() == 1){
            System.out.println("Se coloca: " + turn + " " +"\t ["+players.get(0).getName()+ " " + players.get(0).getDices()[0]+" " + players.get(0).getDices()[1]+" " + players.get(0).getDices()[2] + "]");
            this.players.put(turn.getAndIncrement(), players.get(0));
            return;
        }

        TreeMap<Integer, ArrayList<Player>> list = new TreeMap<>();

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));
        actionQueue.addAction(rollAllDices(players), null, DONE);

        //1. tiramos los dados de todos
        for (Player player : players) {
            int[] dices = player.getDices(); // ya los tiramos en rollAllDices

            if (list.containsKey(dices[2]))
                list.get(dices[2]).add(player);
            else
                list.put(dices[2], new ArrayList<>(Arrays.asList(player)));
        }

        actionQueue.executeQueue();

        List<Integer> keys = list.entrySet().stream() // se ordenan las llaves de mayor a menor
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        for (int i: keys) { // se recorre del mas alto al mas bajo
            System.out.println("Nivel: " + i);
            list.get(i).forEach(p -> System.out.println("\t ["+p.getName()+ " " + p.getDices()[0]+" " + p.getDices()[1]+" " + p.getDices()[2] + "]"));
            sortByTurn(list.get(i), turn);
        }
    }


    /**
     * tira los dados de todos los jugadores
     * */
    private ArrayList<Message> rollAllDices(ArrayList<Player> players){
        ArrayList<Message> messages = new ArrayList<>();

        players.forEach(p -> {
            p.rollDices();
            System.out.println("\t\tDEntrop " + p.getDices()[0] + " "+p.getDices()[1] + " "+ p.getDices()[2]);
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
