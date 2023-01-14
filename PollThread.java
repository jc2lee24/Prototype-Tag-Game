import java.net.*;
import java.io.*;

public class PollThread implements Runnable{
    
    private PrintWriter out;
    private BufferedReader in;
    private PushbackInputStream pin;
    private Socket serverSocket;
    private DLList<Pair<Integer, Player>> connections;
    private ClientScreen sc;
    private Player p;
    private int speed;
    private Map map;

    public PollThread(DLList<Pair<Integer, Player>> connections, ClientScreen sc, Player p, int speed, Map map, PrintWriter out, BufferedReader in, PushbackInputStream pin, Socket serverSocket){
        this.connections = connections;
        this.sc = sc;
        this.p = p;
        this.speed = speed;
        this.map = map;
        this.out = out;
        this.in = in;
        this.pin = pin;
        this.serverSocket = serverSocket;
    }

    public void run(){

        String hostName = "localhost"; 
		int portNumber = 1024;
		try {
            serverSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            pin = new PushbackInputStream(serverSocket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


		sc.repaint();

		
        System.out.println("sending UID to server");
            
        while(true){
            System.out.println("waiting for message");
            
            String input = "";
            try {
                input = in.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("recieved message");
            System.out.println("Message: " + input);
        
            String inputArray[] = input.split(" ");
            String instruction = inputArray[0];
            int recievedUID = Integer.parseInt(inputArray[1]);

            if(instruction.contains("sendUID")){
                Player newPlayer = new Player();
                connections.add(new Pair<Integer, Player>(recievedUID, newPlayer));
                System.out.println("List of connected UIDs: " + connections.printString());
            }

            else if(instruction.contains("sendingAllUIDs")){
                for(int i = 2; i < inputArray.length; i++){
                    System.out.println("adding new UID: " + inputArray[i]);
                    Player newPlayer = new Player();
                    connections.add(new Pair<Integer, Player>(Integer.parseInt(inputArray[i]), newPlayer));
                }
                System.out.println("List of connected UIDs: " + connections.printString());
            }

            else if(instruction.contains("moveDown")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        ((Player) connections.get(i).getPlayer()).moveY(speed);
                    }
                }
            }

            else if(instruction.contains("moveUp")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        ((Player) connections.get(i).getPlayer()).moveY(speed * -1);
                    }
                }
            }

            else if(instruction.contains("moveLeft")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        ((Player) connections.get(i).getPlayer()).moveX(speed * -1);
                    }
                }
            }

            else if(instruction.contains("moveRight")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        ((Player) connections.get(i).getPlayer()).moveX(speed);
                    }
                }
            }

            else if(instruction.contains("taskComplete")){
                sc.completedTask();
                map.getTile(Integer.parseInt(inputArray[2]), Integer.parseInt(inputArray[3])).changeType(-1);
            }
        
            else if(instruction.contains("tag")){
                int tempX = Integer.parseInt(inputArray[2]);
                int tempY = Integer.parseInt(inputArray[3]);
                if(p.getX() == tempX && p.getY() == tempY){
                    sc.tag();
                }
                else if(p.getX() + 1 == tempX && p.getY() == tempY){
                    sc.tag();
                }
                else if(p.getX() - 1 == tempX && p.getY() == tempY){
                    sc.tag();
                }
                else if(p.getX() == tempX && p.getY() + 1 == tempY){
                    sc.tag();
                }
                else if(p.getX() == tempX && p.getY() - 1 == tempY){
                    sc.tag();
                }
                else if(p.getX() + 1 == tempX && p.getY() + 1 == tempY){
                    sc.tag();
                }
                else if(p.getX() + 1 == tempX && p.getY() - 1 == tempY){
                    sc.tag();
                }
                else if(p.getX() - 1 == tempX && p.getY() + 1== tempY){
                    sc.tag();
                }
                else if(p.getX() - 1 == tempX && p.getY() - 1== tempY){
                    sc.tag();
                }
            }
            
            sc.repaint();

        }
    }
}
