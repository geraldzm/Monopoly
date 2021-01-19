package com.game.monopoly.Client.model;

import com.game.monopoly.Client.view.PropertyCard;

import static com.game.monopoly.Client.model.Constant.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;
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

    // Retorna true si un entero esta un array
    public static boolean isIn(int[] arr, int element){
        for (int value : arr) if (value == element) return true;

        return false;
    }

    // Retorna true si un array contiene a otro
    public static boolean contains(int[] outer, int[] inner){
        if (outer.length < inner.length) return false;

        int same = 0;

        for (int value : inner) same = (isIn(outer, value)) ? same + 1 : same;

        return same == inner.length;
    }

    public static boolean isUselessCard(int position){
        for (int uselessCard : uselessCards) if (position == uselessCard) return true;

        return false;
    }
}
