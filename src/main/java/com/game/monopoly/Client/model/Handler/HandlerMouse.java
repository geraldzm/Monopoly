package com.game.monopoly.Client.model.Handler;

import com.game.monopoly.Client.model.Interfaces.Clickable;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HandlerMouse extends Handler<Clickable> implements Clickable {

    public  HandlerMouse(ArrayList<Clickable> clicks){
        super(clicks);
    }

    @Override
    public void clicked(MouseEvent e) {
        for (int i = 0; i < getList().size(); i++){
            getList().get(i).clicked(e);
        }
    }

    @Override
    public void clickReleased(MouseEvent e) {
        for (int i = 0; i < getList().size(); i++){
            getList().get(i).clickReleased(e);
        }
    }
}
