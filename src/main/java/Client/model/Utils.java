package Client.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.function.Function;

public class Utils {
    public static Function<String, BufferedImage> getIcon = s -> {
        try {
            return ImageIO.read(new File("src/main/java/Client/res/image/" + s));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de lectura de imagen: " + s);
        }
        return null;
    };
}
