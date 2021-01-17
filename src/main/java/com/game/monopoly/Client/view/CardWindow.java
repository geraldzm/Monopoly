package com.game.monopoly.Client.view;


import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.model.CardWindowType;

import javax.swing.*;

public class CardWindow extends JFrame{

    public CardWindow(int cardValue, CardWindowType type){

        Card card = CardFactory.getCard(cardValue, type.getCardType());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(card.getWidth(), card.getHeight() + 40);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        add(card);
    }

}