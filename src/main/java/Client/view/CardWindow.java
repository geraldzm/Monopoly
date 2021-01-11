/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.view;

import Client.model.CardFactory;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author fgm_o
 */
public class CardWindow extends javax.swing.JPanel{
    JFrame window = new JFrame();
    public CardWindow(CardFactory property,int carta){
        
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(265, 390);
        window.setLocation(750, 350);
        window.setLayout(null);
        window.setResizable(false);
        
        window.add(property.getCard(carta,true));

    }
    public void Init(){
       window.setVisible(true); 
    }
    
}
