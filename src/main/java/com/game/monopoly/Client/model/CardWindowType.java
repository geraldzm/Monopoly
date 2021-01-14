package com.game.monopoly.Client.model;

import com.game.monopoly.Client.view.PropertyCard;

public enum CardWindowType {
    ENEMY(PropertyCard.Type.NONE), // show the enemy cards
    FRIEND(PropertyCard.Type.SELL), // show the client cards
    BANk(PropertyCard.Type.BUY); // show the cards that the bank is selling

    private final PropertyCard.Type cadsType;

    CardWindowType(PropertyCard.Type cadsType) {
        this.cadsType = cadsType;
    }
    public PropertyCard.Type getCardType(){
        return cadsType;
    }
}
