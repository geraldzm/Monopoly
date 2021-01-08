package Client.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class GameMatrix {
    private int cols, rows;
    private int screenWidth, screenHeight;
    private static final int objectsPerCard = 8;
    
    public GameMatrix(int cols, int rows, int screenWidth, int screenHeight){
        this.cols = cols;
        this.rows = rows;
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public Point getPosition(int x, int y){
        ArrayList<Point> points = getPoints(x, y);

        Point pos = points.get(0);
        Point size = points.get(1);
        
        pos = matrixRect(2, 1, pos.x, pos.y, size.x, size.y);
        
        return pos;
    }
    
    // Dado un plano determina el punto donde deberia ir colocado un objeto
    public Point matrixRect(int i, int j, int x, int y, int width, int height){
        Point pos = new Point();
        
        int hSize = height / 2;
        int wSize = (width * 2) / objectsPerCard;
        
        if (shouldInvert(x, y, height)){
            hSize = (height * 2) / objectsPerCard;
            wSize = width / 2;
        }
        
        pos.x = x + j * wSize;
        pos.y = y + i * hSize;
        
        return pos;
    }
    
    // Sirve para obtener "la hitbox" de cada una de las propiedades
    private ArrayList<Point> getPoints(int x, int y){
        int cornerWidth = 100;
        int cornerHeight = 100;
        
        int sidesWidth = 56;
        int sidesHeight = 100;
        
        int sideOffset = 45;
        
        Point pos = new Point(0, 0);
        Point size = new Point(0, 0);
        
        ArrayList<Point> points = new ArrayList<>();
        
        if (isCorner(y, x, cornerWidth) != null){
            pos = isCorner(y, x, cornerWidth);
            size = new Point(cornerWidth, cornerHeight);

        } else if (y == 0){
            pos = new Point(x * sidesWidth + sideOffset, y);
            size = new Point(sidesWidth, sidesHeight);

        } else if (y == cols-1){
            pos = new Point(x * sidesWidth + sideOffset, screenHeight - sidesHeight);
            size = new Point(sidesWidth, sidesHeight);

        } else if (x == 0){
            pos = new Point(x, (y - 1) * sidesWidth + cornerHeight);
            size = new Point(sidesHeight, sidesWidth);

        } else if (x == rows - 1){
            pos = new Point(screenWidth - sidesHeight, (y - 1) * sidesWidth + cornerHeight);
            size = new Point(sidesHeight, sidesWidth);
        }
        
        points.add(pos);
        points.add(size);
        
        return points;
    }
    
    private boolean shouldInvert(int j, int i, int height){
        return isCorner(i, j, 0) == null &&  (i == screenHeight - height || i == 0);
    }
    
    private Point isCorner(int i, int j, int width){
        int maxX = screenWidth - width;
        int maxY = screenHeight - width;
        
        if (i == 0 && j == 0) return new Point(0, 0);
        
        if (i == 0 && j == rows-1) return new Point(maxX, 0);
        
        if (i == cols-1 && j == 0) return new Point(0, maxY);
        
        if (i == cols-1 && j == rows-1) return new Point(maxX, maxY);
        
        return null;
    }
    
    // Funcion de test
    public void drawSquares(Graphics g){
        Color color = g.getColor();
        
        g.setColor(Color.red);
        for (int y = 0; y < cols; y++){
            for (int x = 0; x < rows; x++){
                ArrayList<Point> points = getPoints(x, y);
                
                Point pos = points.get(0);
                Point size = points.get(1);
                
                drawInnerSquares(g, pos.x, pos.y, size.x, size.y);
                
                // Test para verificar la hitbox
                g.drawRect(pos.x, pos.y, size.x, size.y);
            }
        }
        
        g.setColor(color);
    }
    
    // Funcion de test
    public void drawInnerSquares(Graphics g, int x, int y, int width, int height){
        Point pos = new Point();
        
        int hSize = height / 2;
        int wSize = (width * 2) / objectsPerCard;
        
        int kLimit = 2;
        int jLimit = objectsPerCard / 2;
        
        if (shouldInvert(x, y, height)){
            kLimit = objectsPerCard / 2;
            jLimit = 2;
            
            hSize = (height * 2) / objectsPerCard;
            wSize = width / 2;
        }
        
        for (int k = 0; k < kLimit; k++){
            for (int j = 0; j < jLimit; j++){
                pos.x = x + j * wSize;
                pos.y = y + k * hSize;
                
                g.drawRect(pos.x, pos.y, wSize, hSize);
            }
        }
    }
}
