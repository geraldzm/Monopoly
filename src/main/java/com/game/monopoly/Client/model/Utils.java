package com.game.monopoly.Client.model;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.function.Function;
import javax.swing.ImageIcon;

public class Utils {
    public static Function<String, BufferedImage> getIcon = s -> {
        try {
            return ImageIO.read(new File("src/main/java/Client/res/Image/" + s));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de lectura de imagen: " + s);
        }
        return null;
    };
    
    // obtiene imagenes pero listas para componentes de Swing
    public static ImageIcon getComponentIcon(String path, int width, int height) throws FileNotFoundException, IOException{
        BufferedImage bg = ImageIO.read(new FileInputStream("/home/gerald/develop/poo/Monopoly/src/main/java/Client/res/Image/" + path));

        Image dimg = bg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }
}
