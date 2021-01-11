package com.game.monopoly.Client.model;

import com.game.monopoly.Client.view.PropertyCard;

public enum CardWindowType {
    ENEMY(PropertyCard.Type.NONE), // show the enemy cards
    FRIEND(PropertyCard.Type.SELL); // show the client cards

    private final PropertyCard.Type cadsType;

    CardWindowType(PropertyCard.Type cadsType) {
        this.cadsType = cadsType;
    }
    public PropertyCard.Type getCardType(){
        return cadsType;
    }
}
