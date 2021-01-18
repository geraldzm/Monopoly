package com.game.monopoly.Client.view;

import javax.swing.*;

public class Card extends JLayeredPane {

    private JLabel imageLabel;
    private final int id;
    private ImageIcon image;

    public Card(ImageIcon image, int id) {
        this.id = id;
        this.image = image;

        setLayout(null);

        if(image == null){
            setSize(400, 400);
        } else{
            setImage(image);
        }
    }


    // ---- gettters ---------

    public int getId() {
        return id;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        if(imageLabel != null) remove(imageLabel);
        this.image = image;
        imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        setSize(image.getIconWidth(), image.getIconHeight());
        add(imageLabel, 1,0);
    }
}