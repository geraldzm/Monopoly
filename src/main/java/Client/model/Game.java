package Client.model;

import static Client.model.Constant.*;
import Client.model.Handler.HandlerGameObjects;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable {
    
    public static final int WIDTH = 900, HEIGHT = 900;
    private Thread thread;
    private boolean running = false;
    
    private GameMatrix matrix;
    private HandlerGameObjects handlerGameObjects;
    
    private ImageIcon background = new ImageIcon(
            Utils.getIcon.apply(GAME_BACKGROUND).getScaledInstance(WIDTH, HEIGHT, WIDTH)
    );

    public Game(){
        matrix = new GameMatrix(11, 11, WIDTH, HEIGHT);
        
        handlerGameObjects = new HandlerGameObjects();
        
        Point pos = matrix.getPosition(10, 0);
        
        System.out.println(pos.x + " " + pos.y);
        
        handlerGameObjects.addObject(new GameObject(
                new ImageIcon(Utils.getIcon.apply(PLAYERS[0]).getScaledInstance(25, 25, 25)
                ), pos));
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

        g.dispose();
        bs.show();
    }

    private void tick() {
        // it must be contained by the controller.
    }
}