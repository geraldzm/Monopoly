package Client.model.Handler;

import Client.model.GameObject;
import Client.model.Interfaces.IRenderable;
import Client.model.Interfaces.ITick;
import java.awt.Graphics;

public class HandlerGameObjects extends Handler<GameObject> implements IRenderable, ITick {
    public HandlerGameObjects(){
        super();
    }
    
    @Override
    public void render(Graphics g) {
        objectsList.stream().forEach(object->object.render(g));
    }

    @Override
    public void tick() {
        objectsList.stream().forEach(object->object.tick());
    }
}
