package com.game.monopoly.Client.model;

import com.game.monopoly.Client.model.Objects.Token;
import java.awt.*;
import java.util.*;

public class GameMatrix {
    private final int cols;
    private final int rows;
    private final int screenWidth;
    private final int screenHeight;
    private static final int objectsPerCard = 8;
    private static final int totalCards = 40;
    
    private final Token[][][] gameMatrix;
    
    private final Rectangle[] positions;
    
    public GameMatrix(int cols, int rows, int screenWidth, int screenHeight){
        this.cols = cols;
        this.rows = rows;
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        gameMatrix = new Token[totalCards][2][objectsPerCard / 2];
        positions = new Rectangle[40];
        
        generateRectangles();
    }
    
    // Dada una posicion x, y en el canvas determina que carta selecciono
    // Retorna -1 si no toco ninguna
    public int getCardClicked(int x, int y){
        for (int i = 0; i < positions.length; i++){
            if (positions[i].contains(x, y)) return i;
        }
        
        return -1;
    }
    
    private void generateRectangles(){
        for (int i = 0; i < positions.length; i++){
            Point pos = getPosition(indexToPos(i).x, indexToPos(i).y);
            Point sizes = getSizes(indexToPos(i).x, indexToPos(i).y);
            
            positions[i] = new Rectangle(pos.x, pos.y, sizes.x, sizes.y);
        }
    }

    // Recibe un index de la posicion de la carta y lo settea en la matriz
    public void addPlayer(Token token){
        int initialPos = 0;

        Point pos = getFreePosition(initialPos);

        // Esto no deberia pasar, pero, para evitar errores
        if (pos == null) return;

        token.setCurrentPos(initialPos);
        token.setMatrixPos(pos);

        gameMatrix[initialPos][pos.y][pos.x] = token;

        token.setPos(getPosition(indexToPos(initialPos), pos.x, pos.y));
    }

    // Recibe un index de la posicion de la carta y lo settea en la matriz
    public void addPlayer(Token token, int initialPos){
        Point pos = getFreePosition(initialPos);

        // Esto no deberia pasar, pero, para evitar errores
        if (pos == null) return;

        token.setCurrentPos(initialPos);
        token.setMatrixPos(pos);

        gameMatrix[initialPos][pos.y][pos.x] = token;

        token.setPos(getPosition(indexToPos(initialPos), pos.x, pos.y));
    }
    
    public void movePlayer(Token token, int card, boolean isBackwards){
        boolean isAtPosition = false;
        int index = token.getCurrentPos();

        while (!isAtPosition){
            System.out.println("Moviendo al jugador a: " + index);

            // Lo redireccionamos
            index = (isBackwards) ? index - 1 : index + 1;

            // Evitamos que salga de los indices
            index = (index < 0) ? 39 : index;
            index = (index >= 40) ? 0 : index;

            isAtPosition = index == card;
            setPlayer(token, index);
        }

        token.addEnd();
    }
    
    public void setPlayer(Token token, int card){
        gameMatrix[token.getCurrentPos()][token.getMatrixPos().y][token.getMatrixPos().x] = null;
        
        Point pos = getFreePosition(card);
        
        // Esto no deberia pasar, pero, para evitar errores
        if (pos == null) return;
        
        token.setCurrentPos(card);
        token.setMatrixPos(pos);
        
        gameMatrix[card][pos.y][pos.x] = token;

        token.setMoveTo(getPosition(indexToPos(card), pos.x, pos.y));
    }
    
    private Point getFreePosition(int card){
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < objectsPerCard / 2; j++){
                if (gameMatrix[card][i][j] == null)
                    return new Point(j, i);
            }
        }
        
        return null;
    }
    
    public Point getPosition(int x, int y){
        ArrayList<Point> points = getPoints(x, y);

        Point pos = points.get(0);
        Point size = points.get(1);
        
        pos = matrixRect(0, 0, pos.x, pos.y, size.x, size.y);
        
        return pos;
    }
    
    public Point getPosition(Point point, int x, int y){
        ArrayList<Point> points = getPoints(point.x, point.y);

        Point pos = points.get(0);
        Point size = points.get(1);
        
        pos = matrixRect(y, x, pos.x, pos.y, size.x, size.y);
        
        return pos;
    }
    
    public Point getSizes(int x, int y){
        ArrayList<Point> points = getPoints(x, y);
        
        return points.get(1);
    }
    
    // Dado un plano determina el punto donde deberia ir colocado un objeto
    public Point matrixRect(int i, int j, int x, int y, int width, int height){
        Point pos = new Point();
        
        int hSize = height / 2;
        int wSize = (width * 2) / objectsPerCard;
        
        if (shouldInvert(x, y, height) || (x == 0 && y == 0)){
            hSize = (height * 2) / objectsPerCard;
            wSize = width / 2;
            
            int tmp = i;
            i = j;
            j = tmp;
        }
        
        pos.x = x + j * wSize;
        pos.y = y + i * hSize;
        
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
        
        for (Rectangle position : positions) {
            g.drawRect(position.x, position.y, position.width, position.height);
        }
        /*for (int y = 0; y < cols; y++){
        for (int x = 0; x < rows; x++){
        ArrayList<Point> points = getPoints(x, y);
        Point pos = points.get(0);
        Point size = points.get(1);
        drawInnerSquares(g, pos.x, pos.y, size.x, size.y);
        // Test para verificar la hitbox
        g.drawRect(pos.x, pos.y, size.x, size.y);
        }
        }*/
        
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
