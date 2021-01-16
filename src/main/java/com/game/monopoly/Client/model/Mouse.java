package com.game.monopoly.Client.model;

import com.game.monopoly.Client.model.Interfaces.*;
import java.awt.event.*;

public class Mouse extends MouseAdapter {
    Clickable clicks;

    public Mouse(Clickable clicks){
        this.clicks = clicks;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicks.clickReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicks.clicked(e);
    }
}
