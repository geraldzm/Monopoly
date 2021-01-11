package com.game.monopoly.Client.model;

import com.game.monopoly.Client.view.Card;
import com.game.monopoly.Client.view.PropertyCard;

import javax.swing.*;
import java.util.Hashtable;

public class CardFactory {

    private static Hashtable <Integer, PropertyCard> cards;
    private static String imagePath = "src/main/java/com/game/monopoly/Client/res/Image/";

    private CardFactory() {}

    private static void initCardFactory() {
        cards = new Hashtable<>();

        for (int i = 0; i < 41; i++) {
            if (i == 0 || i == 10 || i == 20 || i == 30) continue;

            cards.put(i, new PropertyCard(new ImageIcon(Utils.getIcon.apply(i+".png").getScaledInstance(251, 350, 251)), i, PropertyCard.Type.NONE));
        }

    }

    public static PropertyCard getCard(int value, PropertyCard.Type type){

        if(cards == null) initCardFactory();

        Card c = cards.get(value);
        return new PropertyCard(c.getImage(), c.getId(), type);
    }

}