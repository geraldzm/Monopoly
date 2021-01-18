package com.game.monopoly.Client.view;

import com.game.monopoly.Client.controller.*;
import com.game.monopoly.Client.model.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class CardsScrollWindow extends JFrame {

    public CardsScrollWindow(int [] cards, CardWindowType type) {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1004, 410);
        setLocation(430, 600);
        setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLocation(0, 0);
        panel.setSize(1004, 350);
        panel.setLayout(new CardsScrollLayout());
        
        ArrayList<PropertyCardController> controllers = new ArrayList<>();

        for (int cardValue : cards) {
            PropertyCard card = (PropertyCard) CardFactory.getCard(cardValue, type.getCardType());
            PropertyCardController controller = new PropertyCardController(card);
            panel.add(card);
            
            controllers.add(controller);
        }

        try {
            ImageIcon backgroundLogo = Utils.getComponentIcon("Logo_Game_of_Thrones.png", 1004, 320);
            panel.add(new JLabel(backgroundLogo));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el logo Logo_Game_of_Thrones");
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setSize(1004, 370); // same size as panel
        scrollPane.setLocation(0, 0);

        add(scrollPane);
        
        this.pack();
        
        controllers.forEach(controller->controller.init());
    }

}
