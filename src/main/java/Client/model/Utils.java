package main.java.Client.model;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.function.Function;
import javax.swing.ImageIcon;
import static main.java.Client.model.Constant.*;

public class Utils {
    public static Function<String, BufferedImage> getIcon = s -> {
        String fullPath = (production) ? imgPathProd + s : imgPathDev + s;
        
        try {
            return ImageIO.read(new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de lectura de imagen: " + s);
        }
        return null;
    };
    
    // obtiene imagenes pero listas para componentes de Swing
    public static ImageIcon getComponentIcon(String path, int width, int height) throws FileNotFoundException, IOException{
        String fullPath = (production) ? imgPathProd + path : imgPathDev + path;
        
        BufferedImage bg = ImageIO.read(new FileInputStream(fullPath));

        Image dimg = bg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }
}
