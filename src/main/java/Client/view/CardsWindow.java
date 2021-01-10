/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.view;

import Client.model.CardFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author fgm_o
 */
public class CardsWindow extends javax.swing.JPanel{
    JFrame window = new JFrame();
    public CardsWindow(int [] cartas ) throws IOException {
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1020, 400);
        window.setLayout(null);
        //window.setResizable(false);
        
        setLocation(0, 0);
        setSize(1004, 350);
        
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setSize(1004, 350);
        scrollPane.setLocation(0, 0);
        
        window.add(scrollPane);
        
        CardFactory cardProperty = new CardFactory();
        
        for (int i = 0; i < cartas.length; i++) {
            this.add(cardProperty.getCard(cartas[i]));
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
        private int x = 0;
        private Container parent;
        
        /*
        public Linelayout(Container parent) {
            this.parent = parent;
        }
        */
        
        @Override
        public void addLayoutComponent(String name, Component comp) {
            System.out.println("ERROR");
//            comp.setLocation(x, 0);
//            components.add(comp);
//            x += comp.getSize().width;
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            System.out.println("ERROR 1");
            components.remove(comp);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            System.out.println("ERROR 2");
            return parent.getSize();
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            System.out.println("ERROR  3");
            return parent.getSize();
        }

        @Override
        public void layoutContainer(Container parent) {
            System.out.println("ERROR 4");
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
