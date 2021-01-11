/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author fgm_o
 */
public class PropertyCard extends Card {
    
    public int id;
    public ImageIcon image;
    private JButton sell;
    private JButton buy;
    private JButton mortgage;
    private ActionListener BtnLister1;
    private ActionListener BtnLister2;

    public PropertyCard(ImageIcon image, int id) {
        super(image, id);
    }
    public void addButton(boolean canBuy){
           if (canBuy){
            buy = new JButton("Comprar");
            buy.setBounds(15, 310, 85, 20);
            this.add(buy);
        }else{
            sell = new JButton("Vender");
            mortgage = new JButton("Hipotecar");
            
            sell.setBounds(15, 310, 75, 20);
            mortgage.setBounds(140, 310, 90, 20);
            
            this.add(sell);
            this.add(mortgage);
            
        }
    }
}
