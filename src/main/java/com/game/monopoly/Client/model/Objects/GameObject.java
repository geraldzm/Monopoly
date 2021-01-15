package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.model.Interfaces.*;
import java.awt.*;
import javax.swing.*;

public class GameObject implements IRenderable, ITick {
    protected Point pos, vel;

    private ImageIcon img;
    
    // TODO: Agregarlos
    protected int width;
    protected int height;

    public GameObject(ImageIcon img){
        this.pos = new Point(0, 0);
        this.vel = new Point(0, 0);
        this.img = img;
    }

    public GameObject(ImageIcon img, Point pos){
        this.pos = pos;
        this.vel = new Point(1, 1);
        this.img = img;
    }
    
    public GameObject(ImageIcon img, Point pos, Point vel){
        this.pos = pos;
        this.vel = vel;
        this.img = img;
    }
    
    // Metodo para renderizar
    @Override
    public void render(Graphics g){
        if (canStart())
            g.drawImage(img.getImage(), pos.x, pos.y, null);
    }
    
    // Metodo para realizar acciones
    @Override
    public void tick() {
        if (!canStart()) return;
        
        velocity();    
    }
    
    // Aplica el vector velocidad
    private void velocity(){
        pos.x += vel.x;
        pos.y += vel.y;
        
        vel.x = 0;
        vel.y = 0;
    }
    
    // Determina si el objeto se puede renderizar o hacer algo
    private boolean canStart(){
        return img != null && pos != null && vel != null;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Point getVel() {
        return vel;
    }

    public void setVel(Point vel) {
        this.vel = vel;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
}
