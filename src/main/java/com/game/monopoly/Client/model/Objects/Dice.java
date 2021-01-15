package com.game.monopoly.Client.model.Objects;

import com.game.monopoly.Client.controller.ServerCommunication;
import com.game.monopoly.Client.model.*;
import com.game.monopoly.Server.ServerConnection;

import static com.game.monopoly.Client.model.Constant.*;
import static com.game.monopoly.common.Comunication.IDMessage.DONE;

import java.awt.*;
import java.io.IOException;
import java.util.Queue;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Dice extends GameObject{
    private final Queue<Integer> animation;
    private final long animationDelay = 500;
    private long since;

    public Dice(int x, int y) {
        super(new ImageIcon(Utils.getIcon.apply(dices[0]).getScaledInstance(DICE_SIZE, DICE_SIZE, 0)));
    
        pos = new Point(x, y);
        animation = new LinkedList<>();
    }
    
    @Override
    public void tick() {
        if (animation.peek() != null){
            animation();
        }
    }

    public void addEnd(){
        animation.add(-1);
    }

    // Permite settear la animacion de los dados (dice debe estar entre 1-6)
    public void setAnimation(int dice){
        for (int i = 0; i < 4; i++){
            animation.add(new Random().nextInt(5));
        }
        
        animation.add(dice - 1);
    }
    
    // Activa la animacion de cambio de dados
    private void animation() {

        if (since + animationDelay - new Date().getTime() < 0) {
            int nextDice = animation.poll();

            if(nextDice == -1){
                try {
                    ServerCommunication.getServerCommunication().sendMessage(DONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            super.setImg(new ImageIcon(Utils.getIcon.apply(dices[nextDice]).getScaledInstance(DICE_SIZE, DICE_SIZE, 0)));
            
            since = new Date().getTime();
        }
    }
}
