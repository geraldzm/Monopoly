
package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import com.game.monopoly.Client.model.Objects.*;
import com.game.monopoly.Client.view.*;
import com.game.monopoly.common.Comunication.*;
import static com.game.monopoly.common.Comunication.IDMessage.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GameListener {
    private static GameListener listener;
    private JFrame window;
    private int amountPlayers;

    private HashMap<Integer, Players> players;

    private GameListener(){
        players = new HashMap<>();
    }

    // Singleton para el Listener
    public static GameListener getInstance(){
        if (listener == null){
            listener = new GameListener();
        }

        return listener;
    }

    private void askAmountPlayers(){
        int amount = -1;

        while(true){
            try{
                amount = Integer.parseInt(JOptionPane.showInputDialog(window, "Cuantas personas van a jugar? (incluyendote)"));
            }catch(NumberFormatException ex){
                amount = -1;
            }

            if (amount < 0 || amount > 6)
                JOptionPane.showMessageDialog(window, "Introduzca un numero valido...");
            else
                break;
        }

        amountPlayers = amount;
    }

    public Listener setLogListener() throws IOException{
        ServerCommunication server = getServerCommunication();

        Listener logListener = msg -> {
            FrameController controller = FrameController.getInstance();

            GameController gameController = (GameController) controller.getWindow(FramesID.GAME);

            gameController.addLogMsg(msg.getString());
        };

        server.setLogbookListener(logListener);

        return logListener;
    }

    // Settea el listener del chat
    public Listener setChatListener() throws IOException{
        ServerCommunication server = getServerCommunication();

        Listener chatListener = msg -> {
            System.out.println("Un mensaje");
            FrameController controller = FrameController.getInstance();

            GameController gameController = (GameController) controller.getWindow(FramesID.GAME);

            gameController.addChatMsg(msg.getString());
        };

        server.setChatListener(chatListener);

        return chatListener;
    }

    public Listener setListener() throws IOException{
        ServerCommunication server = getServerCommunication();
        server.removeReceiverFilter();

        Player player = Player.getInstance();
        
        GameController gameController = (GameController) FrameController.getInstance().getWindow(FramesID.GAME);

        Listener gameListener = msg -> {
            switch(msg.getIdMessage()){
                case ADMIN -> {
                    askAmountPlayers();
                    server.sendInt(amountPlayers, RESPONSE);
                    System.out.println("Servidor: Este usuario ahora es administrador");
                }
                case REJECTED -> {
                    JOptionPane.showMessageDialog(null, "Se ha rechazado tu peticion...");
                }
                case ACCEPTED -> {
                    JOptionPane.showMessageDialog(null, "Se ha aceptado tu peticion!");
                }
                case ID -> {
                    System.out.println("Servidor: Se ha recibido la ID: " + msg.getNumber());

                    player.setID(msg.getNumber());
                    server.setID(msg.getNumber());

                    players.put(msg.getNumber(), player);

                    server.sendMessage(DONE);
                }

                case NAME -> {
                    System.out.println( player.getName());
                    server.sendMessage(new Message(player.getID(), player.getName(), RESPONSE));
                }

                case NAMES -> {
                    System.out.println("Client: Adding players");

                    String playerNames[] = msg.getString().split(",");

                    for (int ID = 0; ID < playerNames.length; ID++){
                        if (ID == player.getID()) continue;

                        System.out.println("Adding ID#" + ID + ", Name: " + playerNames[ID]);
                        
                        Players currentPlayer = new Players();

                        currentPlayer.setID(ID);
                        currentPlayer.setName(playerNames[ID]);

                        players.put(ID, currentPlayer);
                    }

                    server.sendMessage(DONE);
                }

                case STARTED -> {
                    FrameController controller = FrameController.getInstance();

                    controller.openWindow(FramesID.GAME);
                    
                    
                    window = ((GameController) controller.getWindow(FramesID.GAME)).getWindow();
                    server.sendMessage(DONE);
                }

                case MOVE -> {
                    Players cPlayer = players.get(msg.getNumbers()[0]);
                    int pos = msg.getNumbers()[2];
                    boolean dir = msg.getNumbers()[1] == 0; // 0 atras, 1 hacia adelante

                    gameController.setPlayerPosition(cPlayer, dir, pos);

                }

                case GAMEREADY -> {
                    gameController.addPlayers(players);
                    server.sendDone();
                }
                case DICE -> {
                    gameController.triggerDiceAnimation(msg.getNumbers());
                    gameController.triggerGlobalMsg("Se han tirado los dados!");
                }

                case DICES -> {
                    System.out.println("Dices: ");
                    System.out.println(msg.getString());
                    System.out.println(Arrays.toString(msg.getNumbers()));

                    FrameController controller = FrameController.getInstance();
                    OrderController order = (OrderController) controller.generateWindow(FramesID.DICEORDER);

                    order.setDices(msg.getNumbers());
                    order.setPlayers(msg.getString().split(","));

                    order.init();
                    order.start();
                }
                case CANTBUY -> gameController.triggerGlobalMsg("No tienes dinero suficiente.");
                case LOOSER -> {

                    JOptionPane.showMessageDialog(window, "Haz perdido: " + players.get(msg.getNumber()).getName());
                    if(msg.getNumber() == player.getID()) gameController.triggerUI(false);
                    server.sendDone();
                }
                case TURNRS -> {
                    //* TURNRS (turn results): se envia cuando se esta eliguiendo el orden de turno, lleva un int que representa el turno del cliente
                    JOptionPane.showMessageDialog(window, "Mi turno será: " + msg.getNumber()+"  : " + player.getName());
                    server.sendMessage(DONE);
                }

                case GETTOKEN -> {
                    new ComboBoxPopUp(msg.getNumbers(), window); // la respuesta al server esta en la accion del boton (en el controlador)
                }

                case GIVEMONEY -> {
                    gameController.setPlayerMoney(msg.getNumber());
                    gameController.triggerGlobalMsg(msg.getString());
                    server.sendDone();
                }

                case TOKENS -> {
                    int[] rs = msg.getNumbers(); // tokens recibidos, el orden es el mismo que el ID de cada jugador 0-n

                    for (int i = 0; i < rs.length; i++) {
                        if (players.containsKey(i))
                            players.get(i).setTokenImg(rs[i]);
                    }

                    server.sendMessage(DONE);
                }

                case TURN -> {
                    // hago un JOptionPane en vez del mensaje de abajo porque hay que hacer mas que un simple mensaje, solo como recordatorio
                    JOptionPane.showMessageDialog(window, "Turno de: " + player.getName());

                    player.setRolledDices(false);
                    player.setTurn(true);

                    gameController.triggerUI(true);
                    server.sendDone();
                }


                case ADDCARD -> {                    
                    System.out.println("Se intenta agregar una card con el id: " + msg.getNumbers()[1]);
                    players.get(msg.getNumbers()[0]).getCards().add(msg.getNumbers()[1]);
                    server.sendDone();
                }

                case REMOVECARD -> {
                    System.out.println("Se intenta quitar una card con el id: " + msg.getNumbers()[1]);

                    players.get(msg.getNumbers()[0]).getCards().remove(msg.getNumbers()[1]);
                    
                    server.sendDone();
                }
                case REJECTEDBUYATTEND -> {
                    JOptionPane.showMessageDialog(window, "No se pudo comprar la carta con el id: " + msg.getNumber());
                    JOptionPane.showMessageDialog(window, "Por la razon: " + msg.getString());
                    server.sendDone();
                }
                case TAKEMONEY -> {
                    gameController.setPlayerMoney(msg.getNumber());
                    gameController.triggerGlobalMsg("Nuevo saldo...");
                }

                case PUTHOUSE ->{
                    System.out.println("Agregando casas...");
                    gameController.getGame().addHouse(msg.getNumber());
                    server.sendDone();
                }
                
                case REMOVEHOUSE ->{
                    System.out.println("Quitando casas...");
                    int position = msg.getNumbers()[0];
                    int amount = msg.getNumbers()[1];
                    int ID = msg.getNumbers()[2];
                    
                    gameController.getGame().removeHouse(ID, position, amount);

                    server.sendDone();
                }
                
                case PUTHOTEL ->{
                    System.out.println("Agregando hoteles...");
                    int position = msg.getNumbers()[0];
                    int amount = msg.getNumbers()[1];
                    int ID = msg.getNumbers()[2];
                    
                    gameController.getGame().addHotel(ID, position, amount);

                    server.sendDone();
                }
                
                case REMOVEHOTEL ->{
                    System.out.println("Removiendo hoteles...");
                    int position = msg.getNumbers()[0];
                    int amount = msg.getNumbers()[1];
                    int ID = msg.getNumbers()[2];
                    
                    gameController.getGame().removeHotel(ID, position, amount);

                    server.sendDone();
                }
/*3. Cliente solicita vender
4. Cliente solicita hipotecar
8. Server le puede notificar al cliente que muestre cartas
10.
* */
            }
        };


        server.setListener(gameListener);

        return gameListener;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public HashMap<Integer, Players> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<Integer, Players> players) {
        this.players = players;
    }
}