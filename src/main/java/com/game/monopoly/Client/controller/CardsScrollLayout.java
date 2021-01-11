package com.game.monopoly.Client.controller;

import javax.swing.*;
import java.awt.*;

public class CardsScrollLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public void layoutContainer(Container parent) {

        int x = 0;
        for(Component component: parent.getComponents()){

            if(component instanceof JLabel){ // if is the JLabel background
                component.setBounds(0,0, 1004, 350);
                continue;
            }

            component.setBounds(x, 0, component.getWidth(), component.getHeight());

            x += component.getWidth();
            if( x > parent.getWidth()) parent.setSize(x, parent.getHeight());

        }

    }

}
