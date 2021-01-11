/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.view;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author fgm_o
 */
public class ExtraCard extends Card {
    
    public int id;
    public ImageIcon image;
    public String text;
    public JLabel lblText;
    private ActionListener BtnLister1;
    private ActionListener BtnLister2;

    public ExtraCard(ImageIcon image, int id) {
        super(image, id);
    }
    public void addText(){
        
    }
}