package com.game.monopoly.Client.model;

import com.game.monopoly.Client.controller.*;
import static com.game.monopoly.Client.model.Constant.*;
import com.game.monopoly.Client.model.Handler.*;
import com.game.monopoly.Client.model.Interfaces.*;
import com.game.monopoly.Client.model.Objects.*;
import com.game.monopoly.Client.view.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class Game extends Canvas implements Runnable, Clickable {
    private Thread thread;
    private boolean running = false;
    
    private final GameMatrix matrix;
    private final HandlerGameObjects handlerGameObjects;
    
    private ImageIcon background = new ImageIcon(
            Utils.getIcon.apply(GAME_BACKGROUND).getScaledInstance(CANVAS_WIDTH, CANVAS_HEIGHT, 0)
    );
    
    public Dice dice1, dice2;
    private Mouse mouse;
    private boolean isClickTriggered = false;
    private boolean isUIDisabled = false;

    public Game(){
        matrix = new GameMatrix(11, 11, CANVAS_WIDTH, CANVAS_HEIGHT);
        
        handlerGameObjects = new HandlerGameObjects();

        // Agregamos los dados
        dice1 = new Dice(400, 400);
        dice2 = new Dice(460, 400);
        
        handlerGameObjects.addObject(dice1);
        handlerGameObjects.addObject(dice2);
        
        Houses x1 = new Houses(true);
        Houses x2 = new Houses(true);
        
        mouse = new Mouse(this);
        
        this.addMouseListener(mouse);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0D;
        double ns = 1000000000D / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            boolean shouldRender = false;
            while(delta >=1) {
                tick();
                delta--;
                shouldRender = true;
            }

            if(shouldRender) {
                render();
                frames++;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        g.drawImage(background.getImage(), 0, 0, this);

        handlerGameObjects.render(g);
        
        g.dispose();
        bs.show();
    }

    private void tick() {
        // it must be contained by the controller.
        handlerGameObjects.tick();
    }

    // Esta funcion se va a encargar de recibir a los jugadores
    public void initPlayers(HashMap<Integer, Players> players){
        for (int ID = 0; ID < 6; ID++) if (players.containsKey(ID)) {
            Players currentPlayer = players.get(ID);

            matrix.addPlayer(currentPlayer);
            handlerGameObjects.addObject(currentPlayer);
        }
    }

    // Esta funcion se va a encargar de mover al jugador
    public void movePlayer(Players player, int position, boolean direction){
        matrix.movePlayer(player, position, direction);
    }

    // Permite agregar una casa
    public void addHouse(int position){
        Houses house;
        int amount = 1;
        int ID = 0;
        
        GameListener listener = GameListener.getInstance();

        HashMap<Integer, Players> players = listener.getPlayers();

        Players tmp = players.get(0);

        if (tmp.getHouses().containsKey(position)){
            house = tmp.getHouses().get(position);
            
            house.addHouse(amount);
        }else{
            house = new Houses(true);

            tmp.getHouses().put(position, house);
            
            matrix.addPlayer(house, position);
            
            handlerGameObjects.addObject(house);
        }
    }

    // Permite agregar una casa
    public void addHotel(int position){
        Houses hotel;
        int amount = 1;
        int ID = 0;
        
        GameListener listener = GameListener.getInstance();

        HashMap<Integer, Players> players = listener.getPlayers();

        Players tmp = players.get(0);

        if (tmp.getHotel().containsKey(position)){
            hotel = tmp.getHotel().get(position);
            
            hotel.addHouse(amount);
        }else{
            hotel = new Houses(false);

            Houses house = tmp.getHouses().get(position);
            house.subHouse(house.getAmountHouse());

            handlerGameObjects.removeObject(house);
            tmp.getHotel().put(position, hotel);
            
            matrix.addPlayer(hotel, position);
            
            handlerGameObjects.addObject(hotel);
        }
    }

    // Permite remover una casa del tablero
    public void removeHouse(int position){
        GameListener listener = GameListener.getInstance();

        HashMap<Integer, Players> players = listener.getPlayers();
        
        Houses house = players.get(0).getHouses().get(position);
        
        removePropertyToken(house, 1);
    }

    // Permite remover un hotel del tablero
    public void removeHotel(int position){
        GameListener listener = GameListener.getInstance();

        HashMap<Integer, Players> players = listener.getPlayers();
        
        Houses hotel = players.get(0).getHotel().get(position);

        removePropertyToken(hotel, hotel.getAmountHouse());
    }

    // Funcion que permite eliminar un token de casa u hotel del tablero
    private void removePropertyToken(Houses token, int amount){
        token.subHouse(amount);

        if (token.getAmountHouse() <= 0){
            matrix.removePlayer(token);

            handlerGameObjects.removeObject(token);
        }

    }

    @Override
    public void clicked(MouseEvent e) {
    }

    private int clicksAmount;

    @Override
    public void clickReleased(MouseEvent e) {
        if (isClickTriggered ){
            return;
        }

        clicksAmount = 0;

        int selectedCard = matrix.getCardClicked(e.getX(), e.getY());

        if (selectedCard == -1 || (selectedCard != 10 && selectedCard % 10 == 0)) {
            return;
        }

        try{
            Player current = Player.getInstance();

            boolean contain = current.getCards().contains(selectedCard);

            boolean canOperate = current.isHasCompletedRound() && Player.getInstance().isRolledDices() && !Players.enemiHasCard(selectedCard) ;

            if (isDebug) // Si quiere dejar esto en true, vaya a constants, ahi esta esa variable
                canOperate = true;

            if(current.isTurn() && current.getCurrentPos() == selectedCard && !contain && canOperate){
                isClickTriggered = true;

                new CardWindow(selectedCard, CardWindowType.BANk).setVisible(true);
            }else if(current.isTurn() && contain && canOperate){
                isClickTriggered = true;

                new CardWindow(selectedCard, CardWindowType.FRIEND).setVisible(true);
            }else{
                isClickTriggered = true;

                new CardWindow(selectedCard, CardWindowType.ENEMY).setVisible(true);
            }

            if (isClickTriggered)
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        isClickTriggered = false;
                    }
                },100);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void triggerMouse(boolean turnOn){
        isUIDisabled = !turnOn;
    }
}