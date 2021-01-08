package Client.model;

import java.awt.Point;
import javax.swing.ImageIcon;

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
    }
    
    public void move(int position){
        moveTo = new GameMatrix(11, 11, 700, 700).getPosition(GameMatrix.indexToPos(position));
    }
}
