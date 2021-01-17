package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.model.Objects.Token;
import com.game.monopoly.Client.model.*;
import static com.game.monopoly.Client.model.Constant.*;
import java.awt.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

public class Houses extends Token{
    private int amountHouse;
    
    public Houses(boolean isHouse){
        try {
            ImageIcon hImg = Utils.getComponentIcon(houseImg, TOKEN_WIDTH, TOKEN_HEIGHT);
            
            if (!isHouse){
                hImg = Utils.getComponentIcon(hotelImg, TOKEN_WIDTH, TOKEN_HEIGHT);
            }
            
            super.setImg(hImg);
        } catch (IOException ex) {
            Logger.getLogger(Houses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        amountHouse = 1;
    }
    
    // Metodo para renderizar
    @Override
    public void render(Graphics g){
        if (canStart()){
            Color color = g.getColor();
            g.setColor(Color.white);
            g.fillOval(pos.x + 10, pos.y - 10, 10, 10);
            
            g.setColor(color);
            
            g.drawImage(super.getImg().getImage(), pos.x, pos.y, null);
        }
    }
    
    public void addHouse(){
        amountHouse++;
    }
}
