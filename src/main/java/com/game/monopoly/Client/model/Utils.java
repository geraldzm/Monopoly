package com.game.monopoly.Client.model;

import static com.game.monopoly.Client.model.Constant.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.function.*;
import javax.imageio.*;
import javax.swing.*;

public class Utils {
    public static Function<String, BufferedImage> getIcon = path -> {
        try {
            String fullPath = (isProd) ? prodImgPath + path : devImgPath + path;
            return ImageIO.read(new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de lectura de imagen: " + path);
        }
        return null;
    };
    
    // obtiene imagenes pero listas para componentes de Swing
    public static ImageIcon getComponentIcon(String path, int width, int height) throws FileNotFoundException, IOException{
        String fullPath = (isProd) ? prodImgPath + path : devImgPath + path;
        
        BufferedImage bg = ImageIO.read(new FileInputStream(fullPath));

        Image dimg = bg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }
}
