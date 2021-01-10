/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *
 * @author fgm_o
 */
public class Card extends javax.swing.JPanel {
    
    private int id;
    private ImageIcon image;
    JLabel imagen;

    public Card(ImageIcon image , int id) {
        this.id = id;
        this.image = image;
        this.setSize(image.getIconWidth(), image.getIconHeight());
        imagen = new JLabel();
        imagen.setSize(image.getIconWidth(), image.getIconHeight());
        imagen.setLocation(0,0);
        imagen.setIcon(image);
        setLayout(null);
        this.add(imagen);
    }
}
