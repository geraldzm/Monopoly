package com.game.monopoly.Client.view;


import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.model.CardWindowType;

import javax.swing.*;

public class CardWindow extends JFrame{

    public CardWindow(int cardValue, CardWindowType type){
        Card card = CardFactory.getCard(cardValue, type.getCardType());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(750, 350);
        setSize(card.getWidth()+17, card.getHeight() + 40);
        if (cardValue > 41) setSize(card.getWidth()+17, card.getHeight()+40);
        setLayout(null);
        setResizable(false);
        add(card);
    }

}