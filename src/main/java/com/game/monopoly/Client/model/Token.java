package com.game.monopoly.Client.model;

import java.awt.Point;
import javax.swing.ImageIcon;

import static com.game.monopoly.Client.model.Constant.*;

public class Token extends GameObject{
    private Point moveTo;
    
    public Token(ImageIcon img) {
        super(img);
        
        pos = GameMatrix.indexToPos(0);
    }
    
    @Override
    public void tick(){
        if (moveTo != null)
            moveAnimation();
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
        
        if (moveTo.x == pos.x && moveTo.y == pos.y) 
            moveTo = null;
    }
    
    public boolean isAnimationOver(){
        return moveTo == null;
    }
    
    public void move(int position){
        moveTo = new GameMatrix(11, 11, CANVAS_WIDTH, CANVAS_HEIGHT).getPosition(GameMatrix.indexToPos(position));
    }
}
