package com.game.monopoly.Client.controller;


import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import com.game.monopoly.Client.model.Objects.*;
import com.game.monopoly.Client.model.*;
import com.game.monopoly.Client.view.*;
import static com.game.monopoly.common.Comunication.IDMessage.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GameController implements IController, MouseListener{
    private GameWindow window;
    private Game game;
    private final Stack<String> globalMsg;
    private boolean isUIEnabled = true;
    
    public GameController(GameWindow window){
        this.window = window;
        globalMsg = new Stack<>();
    }
    
    @Override
    public void start(){
        window.setVisible(true);
        game.start();
    }

    @Override
    public void init() {
        game = new Game();
        
        window.pack();
        window.gameContainer.add(game);
        
        globalMsg.add("Dinero: $0");
        globalMsg.add("Player: " + Player.getInstance().getName());

        try {
            window.btnSend.addMouseListener(this);
            window.btnCards.addMouseListener(this);
            window.btnTurn.addMouseListener(this);
            window.btnDice.addMouseListener(this);
            
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnCards.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnCards.getWidth(), window.btnCards.getHeight()));
            window.btnSend.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnSend.getWidth(), window.btnSend.getHeight()));
            window.btnTurn.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnTurn.getWidth(), window.btnTurn.getHeight()));
            window.btnDice.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnDice.getWidth(), window.btnDice.getHeight()));
                    
            // Activamos el chat y el log
            GameListener.getInstance().setChatListener();
            GameListener.getInstance().setLogListener();
        } catch (IOException ex) {
            System.out.println("Imagen nula");
        }
    }

    @Override
    public void close() {
        window.setVisible(false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(window.btnCards)){
            System.out.println("Found it");
            onBtnCardsClicked();
        
        } else if (e.getSource().equals(window.btnSend)){
            onBtnSendClicked();
            
        } else if (e.getSource().equals(window.btnTurn) && isUIEnabled){
            onBtnTurn();
        
        } else if (e.getSource().equals(window.btnDice) && isUIEnabled){
            onBtnDice();
        }
    }
    
    public void triggerUI(boolean turnOn){
        isUIEnabled = turnOn;
        game.triggerMouse(turnOn);
    }
    
    // Permite agregar un mensaje a la ventana de chat
    public void addChatMsg(String msg){
        String currentChat = window.taChat.getText();
        
        currentChat += "\n" + msg;
        
        window.taChat.setText(currentChat);
    }

    public void addLogMsg(String msg){
        String currentLog = window.taLog.getText();

        currentLog += "\n" + msg;

        window.taLog.setText(currentLog);
    }
    
    // Permite activar la animacion de los dados dice debe estar entre 1-6
    public void triggerDiceAnimation(int[] dice){
        game.dice1.setAnimation(dice[0]);
        game.dice2.setAnimation(dice[1]);
        game.dice2.addEnd();
        // Activar mensaje global
    }

    public void setPlayerMoney(int amount){
        globalMsg.set(0, "Dinero: $"+amount);
        String fullMsg = "";

        for (int i = 0; i < globalMsg.size(); i++){
            if (i != globalMsg.size() - 1)
                fullMsg += globalMsg.get(i) + ", ";
            else
                fullMsg += globalMsg.get(i);
        }

        window.lbGeneralInfo.setText(fullMsg);
    }

    // Agregamos un mensaje momentaneo
    public void triggerGlobalMsg(String msg){
        StringBuilder fullMsg = new StringBuilder();
        
        globalMsg.add(msg);
        
        for (int i = 0; i < globalMsg.size(); i++){
            if (i != globalMsg.size() - 1)
                fullMsg.append(globalMsg.get(i)).append(", ");
            else
                fullMsg.append(globalMsg.get(i));
        }
        
        window.lbGeneralInfo.setText(fullMsg.toString());
        
        // Borramos el ultimo mensaje recibido
        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                StringBuilder fullMsg = new StringBuilder();

                globalMsg.pop();

                for (int i = 0; i < globalMsg.size(); i++){
                    if (i != globalMsg.size() - 1)
                        fullMsg.append(globalMsg.get(i)).append(", ");
                    else
                        fullMsg.append(globalMsg.get(i));
                }

                window.lbGeneralInfo.setText(fullMsg.toString());
            }
        }, 
        5000);
    }

    public void setPlayerPosition(Players player, boolean direction, int position){
        game.movePlayer(player, position, direction);
    }

    public void addPlayers(HashMap<Integer, Players> players){
        game.initPlayers(players);
    }

    private void onBtnTurn(){
        if (!Player.getInstance().isRolledDices()){
            triggerGlobalMsg("Usted no ha tirado los dados...");
            return;
        }

        triggerGlobalMsg("Cliente: el turno ha terminado");

        try {
            ServerCommunication serverCommunication = getServerCommunication();
            Player.getInstance().setTurn(false);
            triggerUI(false);
            serverCommunication.sendMessage(FINISHEDTURN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Evento cuando se clickea el boton de abrir propiedades
    private void onBtnCardsClicked(){
        var controller = new CardsController(new CardsWindow());
        
        controller.init();
        controller.start();
    }
    
    // Aqui se envian los 
    private void onBtnDice(){
        if (Player.getInstance().isRolledDices()){
            triggerGlobalMsg("Usted ya tiro los dados...");
            return;
        }

        try {
            Player.getInstance().setRolledDices(true);
            getServerCommunication().sendMessage(ROLLDICES);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    // Evento para enviar mensajes
    private void onBtnSendClicked(){
        String msg = window.tfChat.getText();
        
        if (!msg.isEmpty() && !msg.startsWith(" ")){
            if (msg.startsWith("--")){
                debugMsg(msg);
                return;
            }

            try {
                ServerCommunication server = getServerCommunication();
                
                server.sendMessageChat(msg);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            
            window.tfChat.setText("");
        }else {
            triggerGlobalMsg("El mensaje esta vacio...");
        }
    }

    private void debugMsg(String msg){
        String pos = msg.substring(2, msg.length());

        try{
            int playerPos = Integer.parseInt(pos);

            game.movePlayer(Player.getInstance(), playerPos, false);
        }catch (NumberFormatException ex){
            addChatMsg("DEBUG: Formato de debug incorrecto...");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public GameWindow getWindow() {
        return window;
    }

    public Game getGame() {
        return game;
    }
}
