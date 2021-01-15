package com.game.monopoly.Client.model;

import com.game.monopoly.Client.model.Interfaces.Clickable;

import java.awt.event.MouseAdapter;

public class Mouse extends MouseAdapter {
    Clickable clicks;

    public Mouse(Clickable clicks){
        this.clicks = clicks;
    }

    // TODO: Estoy trabajando en esto :)
}
