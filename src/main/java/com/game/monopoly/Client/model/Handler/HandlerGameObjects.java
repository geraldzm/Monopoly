package com.game.monopoly.Client.model.Handler;

import com.game.monopoly.Client.model.Objects.GameObject;
import com.game.monopoly.Client.model.Interfaces.IRenderable;
import com.game.monopoly.Client.model.Interfaces.ITick;

import java.awt.Graphics;

public class HandlerGameObjects extends Handler<GameObject> implements IRenderable, ITick {
    public HandlerGameObjects(){
        super();
    }
    
    @Override
    public void render(Graphics g) {
        for (int i = 0; i < objectsList.size(); i++){
            objectsList.get(i).render(g);
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < objectsList.size(); i++){
            objectsList.get(i).tick();
        }
    }
}
