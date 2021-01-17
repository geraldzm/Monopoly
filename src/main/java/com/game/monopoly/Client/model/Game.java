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

    public Game(){
        matrix = new GameMatrix(11, 11, CANVAS_WIDTH, CANVAS_HEIGHT);
        
        handlerGameObjects = new HandlerGameObjects();

        // Agregamos los dados
        dice1 = new Dice(400, 400);
        dice2 = new Dice(460, 400);
        
        handlerGameObjects.addObject(dice1);
        handlerGameObjects.addObject(dice2);
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
    public void addHouse(int ID, int position){
        Houses house = new Houses();

        matrix.addPlayer(house, position);
    }

    @Override
    public void clicked(MouseEvent e) {

    }

    @Override
    public void clickReleased(MouseEvent e) {
        int selectedCard = matrix.getCardClicked(e.getX(), e.getY());
        
        if (selectedCard != -1){
            GameListener listener = GameListener.getInstance();

            HashMap<Integer, Players> players = listener.getPlayers();

            for (int i = 0; i < 6; i++) if (players.get(i).getCards().contains(selectedCard)){

                if (i == Player.getInstance().getID()){
                    new CardWindow(selectedCard, CardWindowType.FRIEND).setVisible(true);
                } else{
                    new CardWindow(selectedCard, CardWindowType.ENEMY).setVisible(true);
                }
                
                break;
            } else{
                new CardWindow(selectedCard, CardWindowType.BANk).setVisible(true);
                break;
            }
        }
    }
    
    public void triggerMouse(boolean turnOn){
        if (!turnOn){
            this.removeMouseListener(mouse);
        } else {
            this.addMouseListener(mouse);
        }
    }
}