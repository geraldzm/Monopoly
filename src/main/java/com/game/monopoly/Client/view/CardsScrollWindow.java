package com.game.monopoly.Client.view;

import com.game.monopoly.Client.controller.CardsScrollLayout;
import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.model.CardWindowType;
import com.game.monopoly.Client.model.Utils;

import javax.swing.*;
import java.io.IOException;


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

        for (int cardValue : cards) {
            panel.add(CardFactory.getCard(cardValue, type.getCardType()));
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
    }

}
