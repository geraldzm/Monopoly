package com.game.monopoly.Server;

import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.view.PropertyCard;
import com.game.monopoly.common.Comunication.*;
import com.game.monopoly.common.*;
import com.game.monopoly.common.Comunication.*;
import static com.game.monopoly.common.Comunication.IDMessage.*;

import java.net.http.WebSocket;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;


public class Server extends RunnableThread implements Listener{


    private Hashtable<Integer, Player> players; // Integer = turn; 0-n
    private ArrayList<Player> playersByIds; // order = id; 0-n
    private int turn;
    private Player currentPlayer;
    private final Object diceLocker, turnLocker;

    public Server() {
        players = new Hashtable<>();
        playersByIds = connectPlayers();
        LogMessageFactory.setAllPlayers(playersByIds); // sign all players to receive LogMessages
        gameInit(playersByIds); // conectamos a todos && settiamos el juego
        turn = 1;
        diceLocker = new Object();
        turnLocker = new Object();
    }

    @Override
    public void execute() {
         // Game starts
        currentPlayer = players.get(turn); //
        ActionQueue actionQueue = new ActionQueue(currentPlayer);

        actionQueue.addAction(new Message(TURN)); // notify that his turn has started
        actionQueue.executeQueue();

        currentPlayer.setListener(this); // start listening this player
        currentPlayer.removeReceiverFilter();


        //wait until he rolls the dices
        waitWith(diceLocker);

        //roll dices
        currentPlayer.rollDices();
        actionQueue.addAction(new Message(currentPlayer.getDices(), DICE));
        actionQueue.executeQueue();

        currentPlayer.move(currentPlayer.getDices()[2]);
        quickActionQueue(playersByIds, new Message(new int[]{currentPlayer.getId(), 1, currentPlayer.getPosition()}, MOVE));


        currentPlayer.setListener(this); // start listening this player
        currentPlayer.removeReceiverFilter();

        // wait for further actions (sell houses etc)
        waitWith(turnLocker);


        turn = turn+1 > playersByIds.size() ? 1: turn+1; // next turn

        //stopThread();
    }

    private void waitWith(Object locker) {
        synchronized (locker){
            try {
                locker.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <h3>Game configs</h3>
     * */
    private void gameInit(ArrayList<Player> players) {
        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(players));

        // 1. assign ids
        assignIds(players, actionQueue);

        // 2. request each player a name
        actionQueue.addAction(new Message(NAME), message -> players.get(message.getNumber()).setName(message.getString()));
        actionQueue.executeQueue();

        // 3. send names
        actionQueue.addAction(new Message(getNamesFromPlayers(players), NAMES));
        actionQueue.executeQueue();

        // 4. init chat
        players.forEach(p -> p.setChatListener(m -> players.forEach(p2 -> p2.sendChatMessage(m, p))));

        // 5. sort
        sortByTurn(players, players, new AtomicInteger(1));

        // 6. get tokens
        getSelectedTokens();

        // 7. set initial money
        actionQueue.addAction(new Message(1500,"El banco le da $1500", GIVEMONEY));
        actionQueue.executeQueue();
        players.forEach(p -> p.setCash(1500));

        // 8. notify start
        actionQueue.addAction(new Message(GAMEREADY));
        actionQueue.executeQueue();
    }

    private void getSelectedTokens() {
        var ref = new Object() {
            int[] tokens = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        };

        for(int i = 1; i <= players.size(); i++) {
            Player p = this.players.get(i);
            ActionQueue singleAction = new ActionQueue(p);

            Listener tokenSelectionListener = msg -> {
                System.out.println("ID " + msg.getId() + " eligio=" + msg.getNumber());
                p.setToken(msg.getNumber());
                ref.tokens = Arrays.stream(ref.tokens).filter(in -> in != msg.getNumber()).toArray();
            };

            singleAction.addAction(new Message(ref.tokens, GETTOKEN), tokenSelectionListener);
            singleAction.executeQueue();
        }


        //set tokens to everyone
        ActionQueue actionQueue= new ActionQueue(new ArrayList<>(playersByIds));

        AtomicInteger i = new AtomicInteger(0);
        int[] tokens = new int[players.size()];
        playersByIds.forEach(p -> tokens[i.getAndIncrement()] = p.getToken());

        actionQueue.addAction(new Message(tokens, TOKENS));
        actionQueue.executeQueue();
    }

    private void assignIds(ArrayList<Player> players, ActionQueue actionQueue) {
        ArrayList<Message> messages = new ArrayList<>();
        players.forEach(player ->{ // each player has a different message
            player.setId(messages.size());
            messages.add(new Message(player.getId(), ID));
        });

        actionQueue.addAction(messages, null, DONE);
        actionQueue.executeQueue();

        System.out.println("Se asignan los IDs");
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

            quickActionQueue(Arrays.asList(players.get(0)), new Message((turn.get()-1), TURNRS));
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
        quickActionQueue(all, new Message(results, getNamesFromPlayers(players), DICES));


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

    private void quickActionQueue(List<Player> all, Message message) {
        ActionQueue actionQueueAll = new ActionQueue(new ArrayList<>(all));
        actionQueueAll.addAction(message);
        actionQueueAll.executeQueue();
    }

    @Override
    public void action(Message message) {
        System.out.println("Se recibe el mensaje: " + message.getIdMessage());

        switch (message.getIdMessage()){
            case FINISHEDTURN -> {
                synchronized (turnLocker){
                    turnLocker.notify();
                }
            }
            case ROLLDICES -> {
                System.out.println("Se intenta tirar los dados");
                synchronized (diceLocker){
                    diceLocker.notify();
                }
            }

            case BUYPROPERTY -> {
                System.out.println("Se intenta comprar una carta: " + message.getNumber());

                ActionQueue actionQueueAll = new ActionQueue(new ArrayList<>(playersByIds));
                ActionQueue actionQueue = new ActionQueue(currentPlayer);

                PropertyCard propertyCard = (PropertyCard)CardFactory.getCard(message.getNumber());

                if(currentPlayer.getCash() >= propertyCard.getPrice()) {
                    System.out.println("Tiene plata suficiente");
                    currentPlayer.reduceMoney(propertyCard.getPrice());
                    currentPlayer.addCard(propertyCard.getId());

                    System.out.println("El nuevo saldo del cliente sera" + currentPlayer.getCash());
                    actionQueue.addAction(new Message(currentPlayer.getCash(), TAKEMONEY));
                    actionQueue.executeQueue();

                    System.out.println("Se le va a notificar a todo el mundo : ID:" + currentPlayer.getId() + " card:" + propertyCard.getId());
                    actionQueueAll.addAction(new Message(new int[]{currentPlayer.getId(), propertyCard.getId()}, ADDCARD));
                    actionQueueAll.executeQueue();

                }else{
                    System.out.println("no tiene plata suficiente");
                    actionQueue.addAction(new Message(CANTBUY));
                    actionQueue.executeQueue();
                }

                System.out.println("Validamos que el cliente no se haya quedado pobre");
                if(currentPlayer.getCash() <= 0) {
                    System.out.println("Se quedo pobre");
                    System.out.println("Le notificamos que perdio");
                    actionQueue.addAction(new Message(LOOSER));
                    actionQueue.executeQueue();

                    System.out.println("Le notificamos a tooodos que un jugador perdio");
                    actionQueueAll.addAction(new Message("EL jugador "+currentPlayer.getName() + " ha perdido" , LOOSERS));
                    actionQueueAll.executeQueue();
                }

                System.out.println("Volvemos a escuchar a ese jugador con este server");
                currentPlayer.setListener(this);
                currentPlayer.removeReceiverFilter();

                System.out.println("fin BUYPROPERTY");
            }

            case SELLPROPERTY ->{
                System.out.println("Se intenta vender una carta: " + message.getNumber());
            }

            default -> System.out.println("Not supported: "+ message.getIdMessage());
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
        CardFactory.getCard(2, PropertyCard.Type.NONE);
        new Server().startThread();
    }

}
