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
public class CardsWindow extends javax.swing.JPanel{
    JFrame window = new JFrame();
    public CardsWindow(CardFactory property,int [] cartas ) throws IOException {
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1020, 410);
        window.setLocation(430, 600);
        window.setLayout(null);
        window.setResizable(false);
        
        setLocation(0, 0);
        setSize(1004, 350);
        
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setSize(1004, 370);
        scrollPane.setLocation(0, 0);
        
        window.add(scrollPane);
        for (int i = 0; i < cartas.length; i++) {
            this.add(property.getCard(cartas[i],false));
        }
        
        Linelayout lineaL = new Linelayout();
        lineaL.layoutContainer(this);
        setLayout(lineaL);
    }
    
    public void Init(){
       window.setVisible(true); 
    }
    private class Linelayout implements LayoutManager{
        private ArrayList<Component> components = new ArrayList<>();
        private int x;
        private Container parent;
        
        /*
        public Linelayout(Container parent) {
            this.parent = parent;
        }
        */
        
        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            components.remove(comp);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getSize();
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getSize();
        }

        @Override
        public void layoutContainer(Container parent) {
            x = 0;
            for (int i = 0; i < parent.getComponentCount(); i++) {
                System.out.println("Cartas: "+ i);
                Component comp = parent.getComponent(i);
                comp.setBounds(x, 0, comp.getWidth(), comp.getHeight());
                x += comp.getWidth();
                if(x>parent.getWidth()){
                    parent.setSize(x, parent.getHeight());
                
                }
            }
            this.parent = parent;
        }
        
    }
    
}
