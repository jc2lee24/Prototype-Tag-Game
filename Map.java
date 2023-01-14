import java.util.*;
import java.awt.*;
import java.io.*;

public class Map {
    Scanner textMap;
    Tile[][] map;
    int tileWidth;
    int tileHeight;

    public Map(){
        
        map = new Tile[64][36];

        try{
            textMap = new Scanner(new FileReader("gameMap.txt"));

            int counterX = 0;
            int counterY = 0;

            while(textMap.hasNext()){
                int type = textMap.nextInt();
                map[counterX][counterY] = new Tile(type, counterX, counterY);
                //System.out.println(type);

                counterX += 1;
                
                if(counterX >= 64){
                    counterY += 1;
                    counterX = 0;
                }

            } 
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

        tileWidth = map[0][0].getWidth() - 1;
        tileHeight = map[0][0].getHeight() - 1;
    }

    public void drawMe(Graphics g, int playerX, int playerY, int offsetX, int offsetY){

        // for(Object o: map.values()){
        //     ((Tile)o).drawMe(g);
        // }
        int x = playerX;
        int y = playerY;
        int offX = offsetX;
        int offY = offsetY; 
        try{
            for(int i = 0; i < 64; i++){
                for(int j = 0; j < 36; j++){
                    g.setColor(Color.gray);
                    if(map[i][j].getType() < 0){
                        g.setColor(Color.darkGray);
                    }
                    else if(map[i][j].getType() == 0){
                        g.setColor(Color.yellow);
                    }
                    int screenX = i * map[0][0].getWidth() - x + offX;
                    int screenY = j * map[0][0].getHeight() - y + offY;

                    if(map[i][j] != null){
                        g.fillRect(screenX, screenY, map[0][0].getWidth(), map[0][0].getHeight());
                    }
                }
            }
        }catch(Exception e){
            //e.printStackTrace();
            System.out.println(e);
            System.exit(1);
        }

    }

    public Tile getTile(int x, int y){
        if(map[x][y] != null){
            return map[x][y];
        }
        else{
            return null;
        }
    }

    public int getTileWidth(){
        return map[0][0].getWidth();
    }

    public int getTileHeight(){
        return map[0][0].getHeight();
    }
}
