/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client.model;

/**
 *
 * @author fgm_o
 */

import Client.view.PropertyCard;
import java.util.Hashtable;
import java.io.*;
import javax.swing.*;


public class CardFactory {
    private Hashtable < Integer, PropertyCard > cards  = new Hashtable<>();
    String ruta = "src\\main\\java\\Client\\res\\Image\\";
    
    public CardFactory() throws IOException{
        for (int i=0; i < 41; i++){
            if (i == 0 || i == 10 || i == 20 || i == 30) continue;
            cards.put(i,new PropertyCard( new ImageIcon (Utils.getIcon.apply(i+".png").getScaledInstance(251, 350, 251)), i));
        }
    }
    
    public PropertyCard getCard(int value,boolean canBuy){
        cards.get(value).addButton(canBuy);
        return cards.get(value);
    }
    
}
