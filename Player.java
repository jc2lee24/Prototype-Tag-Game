import java.awt.*;

public class Player{
    int x, y, width, height, UID;
    boolean goober, alive;

    public Player(){
        x = 300;
        y = 300;
        width = 30;
        height = 30;
        goober = false;
        alive = true;
    }

    public Player(int x, int y, int width, int height, boolean goober){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.goober = goober;
        this.alive = true;
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

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveX(int movex){
        x += movex;
    }

    public void moveY(int movey){
        y += movey;
    }

    public int getUID(){
        return UID;
    }

    public String sendInfo(){
        return UID + " " + x + " " + y;
    }

    public void setRole(boolean goober){
        this.goober = goober;
    }

    public boolean getRole(){
        return goober;
    }

    public void drawMe(Graphics g, int offsetX, int offsetY){
        if(!goober){
            g.setColor(Color.red);
        }
        else{
            g.setColor(Color.blue);
        }
        if(alive){
            g.fillRect(x + offsetX, y + offsetY, width, height);
        }
        else{
            g.fillOval(x + offsetX, y + offsetY, width, height);
        }
    }

    public void updateUID(int UID){
        this.UID = UID;
    }

    public String toString(){
        return UID + " ";
    }

    public void tag(){
        alive = false;
    }

    public void respawn(){
        alive = true;
    }
}
