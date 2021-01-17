package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.controller.ServerCommunication;
import com.game.monopoly.Client.model.GameMatrix;
import com.game.monopoly.Client.model.Utils;

import static com.game.monopoly.Client.model.Constant.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.Queue;
import javax.swing.*;

public class Token extends GameObject{
    private Queue<Point> movesQueue;
    private Point moveTo;
    
    private int cardPos;
    private Point matrixPos;
    
    public Token(String token) {
        super(new ImageIcon(Utils.getIcon.apply(token).getScaledInstance(TOKEN_WIDTH, TOKEN_HEIGHT, 0)));
        movesQueue = new LinkedList<>();
        pos = GameMatrix.indexToPos(0);
    }

    // Constructor para los players
    protected Token(){
        super(null);

        movesQueue = new LinkedList<>();
        pos = GameMatrix.indexToPos(0);
    }

    public void addEnd(){
        movesQueue.add(new Point(-1, -1));
    }

    @Override
    public void tick(){
        if (moveTo != null)
            moveAnimation();
        else if (movesQueue.peek() != null){
            moveTo = movesQueue.poll();
        }
    }
    
    private void moveAnimation(){
        if(moveTo.getX()  == -1 && moveTo.getY() == -1){
            moveTo = null;

            try {
                System.out.println("Se retorna DONE movimiento");
                ServerCommunication.getServerCommunication().sendDone();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        Point target = moveTo;
        Point hitBox = pos;
        
        int offset = 0;
        
        double distance = Math.sqrt(Math.pow(hitBox.getX()-(target.getX()+offset), 2) + Math.pow(hitBox.getY()-(target.getY()+offset), 2));

        // Claramente si la distancia es 0... ya llego xD
        if(distance == 0){
            moveTo = null;

            return;
        }
        
        double velX = (-2/distance)*(hitBox.getX()-(target.getX()+offset));
        double velY = (-2/distance)*(hitBox.getY()-(target.getY()+offset));
        
        pos.setLocation((int) (pos.x + Math.round(velX)), (int) (pos.y + Math.round(velY)));
        
        int sigmaX = Math.abs(pos.x - moveTo.x);
        int sigmaY = Math.abs(pos.y - moveTo.y);


        if (sigmaX < 3 && sigmaY < 3){
            moveTo = null;
        }
    }

    public void setTokenImg(int token){
        super.setImg(new ImageIcon(Utils.getIcon.apply(tokens[token]).getScaledInstance(TOKEN_WIDTH, TOKEN_HEIGHT, 0)));
    }
    
    public boolean isAnimationOver(){
        return moveTo == null;
    }

    public Point getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(Point moveTo) {
        movesQueue.add(moveTo);
    }

    public int getCurrentPos() {
        return cardPos;
    }

    public void setCurrentPos(int currentPos) {
        this.cardPos = currentPos;
    }

    public Point getMatrixPos() {
        return matrixPos;
    }

    public void setMatrixPos(Point matrixPos) {
        this.matrixPos = matrixPos;
    }  
    
    public Queue<Point> getQueue(){
        return this.movesQueue;
    }
}
