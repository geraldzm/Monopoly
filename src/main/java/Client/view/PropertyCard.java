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
    private JButton mortgage;
    private ActionListener BtnLister1;
    private ActionListener BtnLister2;

    public PropertyCard(ImageIcon image, int id) {
        super(image, id);
        //setBackground(Color.blue);
        sell = new JButton("Vender");
        sell.setBounds(20, 310, 10, 40);
        this.add(sell);
    }
}
