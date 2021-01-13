package com.game.monopoly.Client.model;

import static com.game.monopoly.Client.model.Constant.*;
import com.game.monopoly.Client.model.Handler.*;
import com.game.monopoly.Client.model.Objects.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class Game extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;
    
    private final GameMatrix matrix;
    private final HandlerGameObjects handlerGameObjects;
    
    private ImageIcon background = new ImageIcon(
            Utils.getIcon.apply(GAME_BACKGROUND).getScaledInstance(CANVAS_WIDTH, CANVAS_HEIGHT, 0)
    );
    
    private final HashMap<Integer, Token> players;
    
    private long since;
    private final long sinceD = 10000;
    public Dice dice1, dice2;

    public Game(){
        matrix = new GameMatrix(11, 11, CANVAS_WIDTH, CANVAS_HEIGHT);
        players = new HashMap<>();
        
        players.put(0, new Token(tokens[0]));
        players.put(1, new Token(tokens[1]));
        players.put(2, new Token(tokens[2]));
        players.put(3, new Token(tokens[3]));
        players.put(4, new Token(tokens[4]));
        players.put(5, new Token(tokens[5]));
        players.put(6, new Token(tokens[6]));
        players.put(7, new Token(tokens[7]));

        
        matrix.addPlayer(players.get(0));
        matrix.addPlayer(players.get(1));
        matrix.addPlayer(players.get(2));
        matrix.addPlayer(players.get(3));
        matrix.addPlayer(players.get(4));
        matrix.addPlayer(players.get(5));
        matrix.addPlayer(players.get(6));
        matrix.addPlayer(players.get(7));
        
        handlerGameObjects = new HandlerGameObjects();
        
        handlerGameObjects.addObject(players.get(0));
        handlerGameObjects.addObject(players.get(1));
        handlerGameObjects.addObject(players.get(2));
        handlerGameObjects.addObject(players.get(3));
        handlerGameObjects.addObject(players.get(4));
        handlerGameObjects.addObject(players.get(5));
        handlerGameObjects.addObject(players.get(6));
        handlerGameObjects.addObject(players.get(7));
        
        dice1 = new Dice(400, 400);
        dice2 = new Dice(460, 400);
        
        handlerGameObjects.addObject(dice1);
        handlerGameObjects.addObject(dice2);

        since = new Date().getTime();
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
        double amountOfTicks = 60.0D;
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
                //tick();
                delta--;
                shouldRender = true;
            }

            if(shouldRender) {
             //   render();
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
        
        matrix.drawSquares(g);
        
        handlerGameObjects.render(g);
        handlerGameObjects.tick();
        
        if (since + sinceD - new Date().getTime() < 0){
            for (int i = 0; i < 6; i++){
                Token current = (Token) players.get(i);
                if (!current.isAnimationOver()) continue;
                
                int moveTo = current.getCurrentPos() + new Random().nextInt(12);
                moveTo = (new Random().nextInt(100) < 50) ? moveTo : current.getCurrentPos() - new Random().nextInt(12);
                moveTo = (moveTo < 0) ? 40 + moveTo : moveTo;
                moveTo = (moveTo >= 40) ? 0 : moveTo;
                matrix.movePlayer(current, moveTo);
            }
            since = new Date().getTime();
            
            dice1.setAnimation(new Random().nextInt(5) + 1);
            dice2.setAnimation(new Random().nextInt(5) + 1);
        }
        
        g.dispose();
        bs.show();
    }

    private void tick() {
        // it must be contained by the controller.
    }
}