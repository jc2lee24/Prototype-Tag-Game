import java.io.Serializable;

public class Tile implements Serializable{

    int type; //>0 for background <=0 for wall
    int drawPriority; //lower numbers draw first
    int x, y;
    int width = 30;
    int height = 30;

    public Tile(int type, int x, int y){

        this.type = type;
        this.x = x;
        this.y = y;

        if(type == 1){
            drawPriority = 0;
        }
        else if(type == 2){
            drawPriority = 100;
        }
    }

    public int getType(){
        return type;
    }

    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getPriority(){
        return drawPriority;
    }

    public void changeType(int type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type + "";
    }
}
