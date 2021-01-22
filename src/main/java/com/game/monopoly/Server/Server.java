package com.game.monopoly.Server;

import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.view.Card;
import com.game.monopoly.Client.view.CasualCard;
import com.game.monopoly.Client.view.PropertyCard;
import com.game.monopoly.common.Comunication.*;
import com.game.monopoly.common.*;

import static com.game.monopoly.common.Comunication.IDMessage.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;


public class Server extends RunnableThread implements Listener{


    private Hashtable<Integer, Player> players; // Integer = turn; 0-n
    private ArrayList<Player> playersByIds; // order = id; 0-n
    private ArrayList<Integer> loosers;
    private int turn;
    private boolean turnFinished;
    private Player currentPlayer;
    private final Object diceLocker, turnLocker;
    private final ActionQueue gameRequests; //

    private final Bank bank;

    public Server() {
        players = new Hashtable<>();
        playersByIds = connectPlayers();
        LogMessageFactory.setAllPlayers(playersByIds); // sign all players to receive LogMessages
        gameInit(playersByIds); // conectamos a todos && settiamos el juego
        turn = 1;
        diceLocker = new Object();
        turnLocker = new Object();
        gameRequests = new ActionQueue(players);
        bank = new Bank();
        loosers = new ArrayList<>();
    }

    @Override
    public void execute() {
         // Game starts
        turnFinished = false;
        currentPlayer = players.get(turn); //
        ActionQueue actionQueue = new ActionQueue(currentPlayer);

        if(currentPlayer.isInJail()){
            if(currentPlayer.getJailTurns() == 3){
                currentPlayer.outOfJail();
                gameRequests.addAction(new Message("El jugador " + currentPlayer.getName()+ " ha salido de la carcel", OUTOFJAIL));
                gameRequests.executeQueue();
            }else {
                currentPlayer.increaseJailTurns();
                nextTurn();
                return;
            }
        }

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

        int positionBefore = currentPlayer.getPosition(); // to validate go

        currentPlayer.move(currentPlayer.getDices()[2]);
        quickActionQueue(playersByIds, new Message(new int[]{currentPlayer.getId(), 1, currentPlayer.getPosition()}, MOVE));

        if(positionBefore > currentPlayer.getPosition()) {// if he went through go
            currentPlayer.addCash(200, "A " + currentPlayer.getName()+ " se le da $200 por pasar GO");
            currentPlayer.setGo(true);
        }

        //if the player moves to an enemy property
        if(currentPlayer.isGo()){
            if(!validateLandLord()) {
                waitForClientRequests();
            }
        }else { // si no ha pasado por go no se valida carcel ni impuestos.
            waitForClientRequests();
        }

        nextTurn();

        //stopThread();
    }

    private void waitForClientRequests() {
        currentPlayer.setListener(this); // start listening this player
        currentPlayer.removeReceiverFilter();

        // wait for further actions (sell houses etc)
        synchronized (turnLocker) {
            try {
                while (!turnFinished) {
                    turnLocker.wait();
                    gameRequests.executeQueue();
                    currentPlayer.setListener(this);
                    currentPlayer.removeReceiverFilter();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void nextTurn(){
        turn = turn+1 > playersByIds.size() ? 1: turn+1; // next turn
        if(loosers.contains(turn)) nextTurn();
    }

    // casilla donde cae
    private boolean validateLandLord() {

        int position = currentPlayer.getPosition();
        Card card = CardFactory.getCard(position);

        if(card instanceof PropertyCard) {  // if it is a property
            int toPay = ((PropertyCard) card).getPriceToPay();

            Player landLord = playersByIds.stream()
                    .filter(p -> p.getCards().contains(position))
                    .findFirst()
                    .orElse(null);

            if(landLord != null && landLord != currentPlayer) { // if it was an enemy property

                if(card.getId() == 12 || card.getId() == 28) // if it is a public service
                    toPay = publicService(landLord);
                else if(card.getId() == 5 || card.getId() == 15 || card.getId() == 25 || card.getId() == 35) // ferrocarril
                    toPay = railway((PropertyCard) card, landLord);

                currentPlayer.reduceMoney(toPay, "Se le reduce $" + toPay +  " de renta" + " a " + currentPlayer.getName());
                landLord.addCash(toPay, currentPlayer.getName()+" le ha pagado $" + toPay + " a " + landLord.getName() + " de renta");

            } else if(position == 4 || position == 12 || position == 28 || position == 38){ // taxes
                currentPlayer.reduceMoney(toPay, "Ha pagado " + toPay + " de impuestos ");
            }
        }else if(currentPlayer.isGo() && ( position == 10 || position == 30)) { // carsel
            currentPlayer.toJail();
            gameRequests.addAction(new Message("El jugador " + currentPlayer.getName() +" se va a la carcel", TOJAIL));
            if(position != 10) quickActionQueue(playersByIds, new Message(new int[]{currentPlayer.getId(), 1, 10}, MOVE));
            gameRequests.executeQueue();

        }else if(position == 2 || position == 17 || position == 33 ) { //iron throne
            System.out.println("Iron throne");

        }else if(position == 7 || position == 22 || position == 36 ) {//Valar
            System.out.println("Valar");
        }

        if(currentPlayer.getCash() <= 0) { // validate looser
            gameRequests.addAction(new Message(currentPlayer.getId(), LOOSER));
            gameRequests.executeQueue();
            return true;
        }

        return false;
    }

    private int railway(PropertyCard card, Player landLord) {
        int amountOfRailway = (int) landLord.getCards()
                .stream().filter(i -> i == 5 || i == 15 || i == 25 || i == 35) // filtramos por ferrocarril
                .count();

        return card.getPrices()[amountOfRailway-1];
    }

    private int publicService(Player landLord) {
        // 1. tiramos dados
        currentPlayer.rollDices();
        ActionQueue actionQueue = new ActionQueue(currentPlayer);
        actionQueue.addAction(new Message(currentPlayer.getDices(), DICE));
        actionQueue.executeQueue();
        // 2. sacamos lo que tiene que pagar, si tiene una carta entonces dados * 4, si tiene dos entonces 10 * dados
        return currentPlayer.getDices()[2] * (landLord.getCards().contains(12) && landLord.getCards().contains(28) ? 10: 4);
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

    public void validateLooser(Player player){

        if(player.getCash() <= 0) {
            gameRequests.addAction(new Message(currentPlayer.getId(), LOOSER));

        }

        synchronized (turnLocker){
            turnLocker.notify();
        }
    }

    @Override
    public void action(Message message) {

        switch (message.getIdMessage()){
            case FINISHEDTURN -> {
                synchronized (turnLocker){
                    turnLocker.notify();
                    turnFinished = true;
                }
            }
            case ROLLDICES -> {
                synchronized (diceLocker){
                    diceLocker.notify();
                }
            }

            case BUYPROPERTY -> {
                PropertyCard propertyCard = (PropertyCard)CardFactory.getCard(message.getNumber());

                if(currentPlayer.getCash() >= propertyCard.getPrice()) {
                    currentPlayer.reduceMoney(propertyCard.getPrice(), currentPlayer.getName()+ " ha comprado una propiedad por $"+propertyCard.getPrice());
                    currentPlayer.addCard(propertyCard.getId());

                    gameRequests.addAction(new Message(new int[]{currentPlayer.getId(), propertyCard.getId()}, ADDCARD));

                }else{
                    currentPlayer.sendMessage(new Message(CANTBUY));
                }

                if(currentPlayer.getCash() <= 0) {
                    gameRequests.addAction(new Message(currentPlayer.getId(), LOOSER));
                }

                synchronized (turnLocker){
                    turnLocker.notify();
                }
            }

            case SELLHOTEL -> {
                PropertyCard propertyCard = (PropertyCard)CardFactory.getCard(message.getNumber());

                currentPlayer.addCash(propertyCard.getHotelCost(), "A " + currentPlayer.getName()+ " se le acredita $"+propertyCard.getHotelCost()+ " por la venta de un hotel");
                propertyCard.decreaseHotelAmount();
                bank.hotel++;
                gameRequests.addAction(new Message(propertyCard.getId(), "Se vente un hotel en " + propertyCard.getId(), REMOVEHOTEL));

                synchronized (turnLocker){
                    turnLocker.notify();
                }
            }

            case BUYHOTEL -> {
                if(bank.hotel <= 0) { // no hay casas
                    currentPlayer.sendMessage(new Message("No hay hoteles disponibles para comprar", NOAVAILABE));
                    return;
                }

                PropertyCard propertyCard = (PropertyCard)CardFactory.getCard(message.getNumber());

                if(currentPlayer.getCash() >= propertyCard.getHotelCost()) {

                    currentPlayer.reduceMoney(propertyCard.getHotelCost(), "A " +currentPlayer.getName() + " se le debita $"+propertyCard.getHotelCost() +" por la compra de un hotel");
                    gameRequests.addAction(new Message(propertyCard.getId(), PUTHOTEL));

                    //"Se remueve una casa de " +propertyCard.getId()+ " para poner un hotel"

                    propertyCard.increaseHotelAmount();
                    propertyCard.setHouseAmount(0);
                    bank.house += 4;
                    bank.hotel--;

                }else{
                    currentPlayer.sendMessage(new Message(CANTBUY));
                }

                if(currentPlayer.getCash() <= 0) {
                    gameRequests.addAction(new Message(currentPlayer.getId(), LOOSER));
                }

                synchronized (turnLocker){
                    turnLocker.notify();
                }
            }

            case BUYHOUSE -> {

                if(bank.house <= 0) { // no hay casas
                    currentPlayer.sendMessage(new Message("No hay casas disponibles para comprar", NOAVAILABE));
                    return;
                }

                PropertyCard propertyCard = (PropertyCard)CardFactory.getCard(message.getNumber());

                if(currentPlayer.getCash() >= propertyCard.getHouseCost()) {

                    currentPlayer.reduceMoney(propertyCard.getHouseCost(), "A " +currentPlayer.getName() + " se le debita $"+propertyCard.getHouseCost() +" por la compra de una casa");
                    gameRequests.addAction(new Message(propertyCard.getId(), PUTHOUSE));
                    propertyCard.increaseHouseAmount();
                    bank.house--;

                }else{
                    currentPlayer.sendMessage(new Message(CANTBUY));
                }

                if(currentPlayer.getCash() <= 0) {
                    gameRequests.addAction(new Message(currentPlayer.getId(), LOOSER));
                }

                synchronized (turnLocker){
                    turnLocker.notify();
                }

            }

            case SELLHOUSE -> {
                PropertyCard propertyCard = (PropertyCard)CardFactory.getCard(message.getNumber());

                currentPlayer.addCash(propertyCard.getHouseCost(), "A " + currentPlayer.getName()+ " se le acredita $"+propertyCard.getHouseCost()+ " por la venta de una casa");
                gameRequests.addAction(new Message(propertyCard.getId(), "Se vente una casa en " + propertyCard.getId(), REMOVEHOUSE));
                propertyCard.decreaseHouseAmount();
                bank.house++;

                synchronized (turnLocker){
                    turnLocker.notify();
                }
            }

            case SELLPROPERTY -> {

                PropertyCard propertyCard = (PropertyCard) CardFactory.getCard(message.getNumber());

                currentPlayer.getCards().remove(propertyCard.getId()); // delete property

                gameRequests.addAction(new Message(new int[]{currentPlayer.getId(), propertyCard.getId()}, REMOVECARD));

                currentPlayer.addCash(propertyCard.getPrice(), "A " + currentPlayer.getName()+ " se le acredita $"+propertyCard.getPrice()+" por la venta de una propiedad" );

                synchronized (turnLocker){
                    turnLocker.notify();
                }
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

    // los que no han perdido
    public ArrayList<Player> playing(){
        Stream<Player> playerStream = playersByIds.stream().filter(p -> !loosers.contains(p.getId()));

        return new ArrayList<Player>(playerStream.collect(Collectors.toList()));
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
        CardFactory.getCard(1, PropertyCard.Type.NONE);
        new Server().startThread();
    }

}