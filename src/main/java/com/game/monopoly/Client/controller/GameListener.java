
package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;

import com.game.monopoly.Client.model.Objects.Player;
import com.game.monopoly.Client.model.Objects.Players;
import com.game.monopoly.Client.view.ComboBoxPopUp;
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

        Listener gameListener = msg -> {
            switch(msg.getIdMessage()){
                case ADMIN -> {
                    askAmountPlayers();

                    server.sendInt(amountPlayers, RESPONSE);

                    System.out.println("Servidor: Este usuario ahora es administrador");
                }
                case REJECTED -> {
                    System.out.println("Servidor: Se ha rechazado tu peticion de unirse...");
                }
                case ACCEPTED -> {
                    System.out.println("Servidor: Se ha aceptado tu peticion de unirse!");
                }
                case ID -> {
                    System.out.println("Servidor: Se ha recibido la ID: " + msg.getNumber());

                    player.setID(msg.getNumber());
                    server.setID(msg.getNumber());

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

                        Players currentPlayer = new Players();
                        currentPlayer.setID(ID);

                        players.put(ID, currentPlayer);
                    }

                    server.sendMessage(DONE);
                }

                case STARTED -> {
                    FrameController controller = FrameController.getInstance();

                    controller.openWindow(FramesID.GAME);

                    server.sendMessage(DONE);
                }
                case DICE -> {
                    FrameController controller = FrameController.getInstance();
                    GameController gameController = (GameController) controller.getWindow(FramesID.GAME);

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

                case TURNRS -> {
                    //* TURNRS (turn results): se envia cuando se esta eliguiendo el orden de turno, lleva un int que representa el turno del cliente
                    JOptionPane.showMessageDialog(null, "Mi turno será: " + msg.getNumber()+"  : " + player.getName());
                    server.sendMessage(DONE);
                }

                case GETTOKEN -> {
                    System.out.println("Tokens disponibles: " + Arrays.toString(msg.getNumbers()));
                    new ComboBoxPopUp(msg.getNumbers()); // la respuesta al server esta en la accion del boton (en el controlador)
                }

                case GIVEMONEY -> {
                    // trae un mensaje en el string con la razon por la que se le da la plata
                    // tiene la cantidad de plata que se le esta dando en el numero
                    GameController gameController = (GameController) FrameController.getInstance().getWindow(FramesID.GAME);

                    gameController.setPlayerMoney(msg.getNumber());
                    gameController.triggerGlobalMsg(String.format("%s: %s", msg.getString(), msg.getNumber(), player.getName()));
                    server.sendMessage(DONE);
                }

                case TOKENS -> {
                    System.out.println("Server: se recibieron los tokens");
                    int[] rs = msg.getNumbers(); // tokens recibidos, el orden es el mismo que el ID de cada jugador 0-n
                    System.out.println("Resultado de los tokens: ");

                    for (int i = 0; i < rs.length; i++) {
                        if (players.containsKey(i))
                            players.get(i).setTokenImg(rs[i]);

                        System.out.println(String.format("\tEl di: %d escogió el token= %d", i, rs[i]));
                    }

                    server.sendMessage(DONE);
                }
            }
        };


        server.setListener(gameListener);

        return gameListener;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }
}