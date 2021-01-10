package com.game.monopoly.Client.model;


import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Date;
import javax.swing.ImageIcon;

import static com.game.monopoly.Client.model.Constant.*;
import com.game.monopoly.Client.model.Handler.HandlerGameObjects;

public class Game extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;
    
    private GameMatrix matrix;
    private HandlerGameObjects handlerGameObjects;
    
    private ImageIcon background = new ImageIcon(
            Utils.getIcon.apply(GAME_BACKGROUND).getScaledInstance(CANVAS_WIDTH, CANVAS_HEIGHT, CANVAS_WIDTH)
    );
    
    int counter = 5;
    long since;

    public Game(){
        matrix = new GameMatrix(11, 11, CANVAS_WIDTH, CANVAS_HEIGHT);
        
        handlerGameObjects = new HandlerGameObjects();
        
        Point pos = matrix.getPosition(matrix.indexToPos(counter));
        
        System.out.println(pos.x + " " + pos.y);
        
        handlerGameObjects.addObject(new Token(
                new ImageIcon(Utils.getIcon.apply(PLAYERS[0]).getScaledInstance(TOKEN_WIDTH, TOKEN_HEIGHT, 0)
                )));
        
        handlerGameObjects.getList().get(0).pos = pos;
        
        since = new Date().getTime() + 2000;
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
        
        matrix.drawSquares(g);
        
        handlerGameObjects.render(g);
        handlerGameObjects.tick();
        
        Token token = ((Token) handlerGameObjects.getList().get(0));
        
        if (since - new Date().getTime() < 0 && token.isAnimationOver()){
            counter++;
            token.move(counter);
            
            Point pos = token.pos;
            
            System.out.println(pos);
            
            since = new Date().getTime() + 2000;
        }

        g.dispose();
        bs.show();
    }

    private void tick() {
        // it must be contained by the controller.
    }
}