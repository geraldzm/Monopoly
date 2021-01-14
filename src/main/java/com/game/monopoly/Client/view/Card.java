package com.game.monopoly.Client.view;

import javax.swing.*;

public class Card extends JLayeredPane {

    private JLabel imageLabel;
    private final int id;
    private final ImageIcon image;

    public Card(ImageIcon image, int id) {
        this.id = id;
        this.image = image;

        imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());

        setSize(image.getIconWidth(), image.getIconHeight());
        setLayout(null);
        add(imageLabel, 1,0);
    }


    // ---- gettters ---------

    public int getId() {
        return id;
    }

    public ImageIcon getImage() {
        return image;
    }
}