public class MovementThread implements Runnable{

    Map map;
    Player p;
    ClientScreen s;
    int speed;
    
    public MovementThread(Map map, Player p, ClientScreen s, int speed){
        this.map = map;
        this.p = p;
        this.s = s;
        this.speed = speed;
    }


    public void run(){
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (Input.keyboard[83]){
                try{
                    if((map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight() + 1)) != null){
                        if((map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight() + 1).getType()) > 0){
                            p.moveY(speed);
                            s.sendMessage("moveDown");
                        }
                    }
                }catch(IndexOutOfBoundsException e){

                }
            }

            if(Input.keyboard[87]){
                try{
                    if((map.getTile(p.getX()/map.getTileWidth(), p.getY()/map.getTileHeight() - 1)) != null){
                        if((map.getTile(p.getX()/map.getTileWidth(), p.getY()/map.getTileHeight() - 1).getType()) > 0){
                            p.moveY(speed * -1);
                            s.sendMessage("moveUp");

                        }
                    }
                }catch(IndexOutOfBoundsException e){

                }            
            }

            if(Input.keyboard[65]){
                try{
                    if((map.getTile(p.getX()/map.getTileWidth() - 1, p.getY()/map.getTileHeight())) != null){
                        if((map.getTile(p.getX()/map.getTileWidth() - 1, p.getY()/map.getTileHeight()).getType()) > 0){
                            p.moveX(speed * -1);
                            s.sendMessage("moveLeft");

                        }
                    }
                }catch(IndexOutOfBoundsException e){

                }
            }

            if(Input.keyboard[68]){
                try{
                    if((map.getTile((p.getX() - p.getWidth())/map.getTileWidth() + 1, p.getY()/map.getTileHeight())) != null){
                        if((map.getTile((p.getX() - p.getWidth())/map.getTileWidth() + 1, p.getY()/map.getTileHeight()).getType()) > 0){
                            p.moveX(speed);
                            s.sendMessage("moveRight");

                        }
                    }
                }catch(IndexOutOfBoundsException e){

                }
            }
            if(Input.keyboard[73]){
                try{
                    s.toggleInstructions();
                }catch(IndexOutOfBoundsException e){
                    System.out.println(e);
                }
            }

            if(Input.keyboard[32]){
                try{
                    if(!p.goober){
                        //checks that the tile isn't null so error isn't thrown
                        if((map.getTile((p.getX() - p.getWidth())/map.getTileWidth() + 1, p.getY()/map.getTileHeight())) != null){
                            if((map.getTile(p.getX()/map.getTileWidth() - 1, p.getY()/map.getTileHeight())) != null){
                                if((map.getTile(p.getX()/map.getTileWidth(), p.getY()/map.getTileHeight() - 1)) != null){
                                    if((map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight() + 1)) != null){
                                        
                                        if((map.getTile((p.getX() - p.getWidth())/map.getTileWidth() + 1, p.getY()/map.getTileHeight()).getType()) == 0){
                                            map.getTile((p.getX() - p.getWidth())/map.getTileWidth() + 1, p.getY()/map.getTileHeight()).changeType(-1);
                                            int tileX = (p.getX() - p.getWidth())/map.getTileWidth() + 1;
                                            int tileY = p.getY()/map.getTileHeight();
                                            s.sendMessage("taskComplete", tileX + "", tileY + "");
                                            s.completedTask();

                                        }
                                        else if((map.getTile(p.getX()/map.getTileWidth() - 1, p.getY()/map.getTileHeight()).getType()) == 0){
                                            map.getTile(p.getX()/map.getTileWidth() - 1, p.getY()/map.getTileHeight()).changeType(-1);
                                            int tileX = p.getX()/map.getTileWidth() - 1;
                                            int tileY = p.getY()/map.getTileHeight();
                                            s.sendMessage("taskComplete", tileX + "", tileY + "");
                                            s.completedTask();

                                        } 
                                        else if((map.getTile(p.getX()/map.getTileWidth(), p.getY()/map.getTileHeight() - 1).getType()) == 0){
                                            map.getTile(p.getX()/map.getTileWidth(), p.getY()/map.getTileHeight() - 1).changeType(-1);
                                            int tileX = p.getX()/map.getTileWidth();
                                            int tileY = p.getY()/map.getTileHeight() -1;
                                            s.sendMessage("taskComplete", tileX + "", tileY + "");
                                            s.completedTask();
                                        }
                                        else if((map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight() + 1).getType()) == 0){
                                            map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight() + 1).changeType(-1);
                                            int tileX = (p.getX())/map.getTileWidth();
                                            int tileY = (p.getY() - p.getHeight())/map.getTileHeight() + 1;
                                            s.sendMessage("taskComplete", tileX + "", tileY + "");
                                            s.completedTask();

                                        }
                                        else if((map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight()).getType()) == 0){
                                            map.getTile((p.getX())/map.getTileWidth(), (p.getY() - p.getHeight())/map.getTileHeight()).changeType(-1);
                                            int tileX = p.getX()/map.getTileWidth();
                                            int tileY = p.getY()/map.getTileHeight();
                                            s.sendMessage("taskComplete", tileX + "", tileY + "");
                                            s.completedTask();

                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        //if near other player "tag" them
                        int tileX = p.getX()/map.getTileWidth();
                        int tileY = p.getY()/map.getTileHeight();
                        s.sendMessage("tag", tileX + "", tileY + "");
                        
                    }
                }catch(IndexOutOfBoundsException e){

                }
            }

            if (Input.clicked && Input.mouseButtons[0]) {//mouse pressed left click code
            
            }
            else if (!Input.clicked && Input.mouseButtons[0] == false) {// mouse released left click code
                
            }

            else if (Input.clicked && Input.mouseButtons[1] == false) {// mouse released right click code

            }

            else if (!Input.clicked && Input.mouseButtons[1] == false) {// mouse released right click code

            }

            s.repaint();
        }
    }
    
}
