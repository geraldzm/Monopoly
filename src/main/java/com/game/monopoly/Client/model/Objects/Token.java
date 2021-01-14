package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.model.GameMatrix;
import com.game.monopoly.Client.model.Objects.GameObject;
import com.game.monopoly.Client.model.Utils;
import static com.game.monopoly.Client.model.Constant.*;
import java.awt.*;
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
    
    @Override
    public void tick(){
        if (moveTo != null)
            moveAnimation();
        else if (movesQueue.peek() != null && moveTo == null){
            moveTo = movesQueue.poll();
        }
    }
    
    private void moveAnimation(){
        Point target = moveTo;
        Point hitBox = pos;
        
        int offset = 0;
        
        double distance = Math.sqrt(Math.pow(hitBox.getX()-(target.getX()+offset), 2) + Math.pow(hitBox.getY()-(target.getY()+offset), 2));

        if(distance == 0) return;
        
        double velX = (-1/distance)*(hitBox.getX()-(target.getX()+offset));
        double velY = (-1/distance)*(hitBox.getY()-(target.getY()+offset));
        
        pos.setLocation((int) (pos.x + Math.round(velX)), (int) (pos.y + Math.round(velY)));
        
        int sigmaX = Math.abs(pos.x - moveTo.x);
        int sigmaY = Math.abs(pos.y - moveTo.y);
        
        if (sigmaX < 3 && sigmaY < 3){
            moveTo = null;
        }
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