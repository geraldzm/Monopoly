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
        
        pos = matrixRect(1, 1, pos.x, pos.y, size.x, size.y);
        
        return pos;
    }
    
    public Point getPosition(Point point){
        ArrayList<Point> points = getPoints(point.x, point.y);

        Point pos = points.get(0);
        Point size = points.get(1);
        
        pos = matrixRect(2, 1, pos.x, pos.y, size.x, size.y);
        
        return pos;
    }
    
    // Dado un plano determina el punto donde deberia ir colocado un objeto
    public Point matrixRect(int i, int j, int x, int y, int width, int height){
        Point pos = new Point();
        
        System.out.println("Pos bef: " + new Point(x, y));
        
        int hSize = height / 2;
        int wSize = (width * 2) / objectsPerCard;
        
        if (shouldInvert(x, y, height) || (x == 0 && y == 0)){
            hSize = (height * 2) / objectsPerCard;
            wSize = width / 2;
        }
        
        if (isCorner(new Point(x, y), height)){
            System.out.println("hSize: " + hSize + ", wSize: " + wSize);
        }
        
        pos.x = x + j * wSize;
        pos.y = y + i * hSize;
        
        System.out.println("Pos after: " + pos);
        
        return pos;
    }
    
    // Sirve para obtener "la hitbox" de cada una de las propiedades
    private ArrayList<Point> getPoints(int x, int y){
        int cornerWidth = 128;
        int cornerHeight = 128;
        
        int sidesWidth = 72;
        int sidesHeight = 128;
        
        int sideOffset = 57;
        
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
        return !isCorner(new Point(j, i), 0) && (i == screenHeight - height || i == 0);
    }
    
    private boolean isCorner(Point pos, int width){
        int maxX = screenWidth - width;
        int maxY = screenHeight - width;
        
        int i = pos.y;
        int j = pos.x;
        
        if (i == 0 && j == 0) return true;
        
        if (i == 0 && j == maxX) return true;
        
        if (i == maxY && j == 0) return true;
        
        return i == maxY && j == maxX;
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
    
    public static Point indexToPos(int i){
        Point point = new Point();
        
        if (i < 11){
            point.x = 0;
            point.y = 10 - i;
        
        } else if (i < 21){
            point.y = 0;
            point.x = i - 10;
        
        } else if (i < 31){
            point.x = 10;
            point.y = i - 20;
        
        } else {
            point.x = 40 - i;
            point.y = 10;
        }
        
        return point;
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
