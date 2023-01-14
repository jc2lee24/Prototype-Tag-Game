import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ClientScreen extends JPanel implements ActionListener{
    public static final Toolkit tool = Toolkit.getDefaultToolkit();

    private JButton gameStart;
    private JButton instructions;

    private PrintWriter out;
    private BufferedReader in;
    private PushbackInputStream pin;
    private Socket serverSocket;

    private DLList<Pair<Integer, Player>> connections;
    
    private int UID;

    private Map map;
    private Player p;

    private Thread movementThread;

    private final int speed = 3;

    private int tasksLeft = 10;
    private int lives = 3;

    private boolean menu = false;
    private boolean instruction = false;

    private MovementThread mt;

    public ClientScreen() {
        new Input(this);
        this.setLayout(null);
        UID = (int)(Math.random() * 90000 + 10000);
        System.out.println("my UID: " + UID);
        connections = new DLList<Pair<Integer, Player>>();
        map = new Map();
        p = new Player();
        

        gameStart = new JButton();
        gameStart.setFont(new Font("Arial", Font.BOLD, 20));
        gameStart.setHorizontalAlignment(SwingConstants.CENTER);
        gameStart.setBounds(555, 305, 200, 50);
        gameStart.setText("Join Game");
        this.add(gameStart);
        gameStart.addActionListener(this);

        instructions = new JButton();
        instructions.setFont(new Font("Arial", Font.BOLD, 20));
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        instructions.setBounds(555, 455, 200, 50);
        instructions.setText("Instructions");
        this.add(instructions);
        instructions.addActionListener(this);

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

        mt = new MovementThread(map, p, this, speed);
        movementThread = new Thread(mt);
    }

    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return tool.getScreenSize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        instructions.setVisible(menu);
        gameStart.setVisible(menu && !instruction);
        if(!menu){
            map.drawMe(g, p.getX(), p.getY(), 625, 375);
            g.setColor(Color.red);
            if(p.getRole()){
                g.setColor(Color.blue);
            }
            g.fillRect(600, 350, 30, 30);  
            for(int i = 0; i < connections.size(); i++){
                g.setColor(Color.red);
                if(connections.get(i).getPlayer().getRole()){
                    g.setColor(Color.blue);
                }
                connections.get(i).getPlayer().drawMe(g, 600 - p.getX(), 350 - p.getY()); 
            }      
            g.setColor(Color.black);
            g.drawString("tasks remaining: " + tasksLeft, 1100, 15);
            g.drawString("Press \"I\" for Instructions", 575, 15);
            g.drawString("Lives left: " + lives, 15, 15);

            if(tasksLeft == 0){
                g.setColor(Color.red);
                g.setFont(new Font("Arial", 10, 18));
                if(!p.getRole()){
                    g.drawString("YOU WIN", 600, 300);
                }
                else{
                    g.drawString("YOU LOSE", 600, 300);
                }
                movementThread.interrupt();
            }

            if(lives == 0){
                g.setColor(Color.red);
                g.setFont(new Font("Arial", 10, 18));
                if(p.getRole()){
                    g.drawString("YOU WIN", 600, 300);
                }
                else{
                    g.drawString("YOU LOSE", 600, 300);
                }
                movementThread.interrupt();
            }
        }
        if(instruction){
            g.setFont(new Font("Arial", 10, 18));
            g.setColor(Color.black);
            g.drawString("Use WASD to move.", 300, 300);
            g.drawString("Scoobers are red, Goobers are blue", 300, 320);
            g.drawString("Scoobers: Complete 10 tasks without loosing your lives to win!", 300, 340);
            g.drawString("Press the spacebar next to the task (yellow square) to complete the task", 300, 360);
            g.drawString("Goobers: tag all the Scoobers and stop them from completing their tasks", 300, 380);
            g.drawString("Press the spacebar to tag the Scoobers", 300, 400);


        }
    }

    public void animate(){
        movementThread.start();
    }


    public void poll() throws IOException{
        //pollThread.start();
        //connect to serverThread

		String hostName = "localhost"; 
		int portNumber = 1024;
		serverSocket = new Socket(hostName, portNumber);
        out = new PrintWriter(serverSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        pin = new PushbackInputStream(serverSocket.getInputStream());

		repaint();

		
        System.out.println("sending UID to server");
        out.println(UID + "");
            
        while(!menu){
            System.out.println("waiting for message");
            
            String input = in.readLine();
            System.out.println("recieved message");
            System.out.println("Message: " + input);
        
            String inputArray[] = input.split(" ");
            String instruction = inputArray[0];
            int recievedUID = Integer.parseInt(inputArray[1]);

            if(instruction.contains("sendUID")){
                Player newPlayer = new Player();
                connections.add(new Pair<Integer, Player>(recievedUID, newPlayer));
                this.sendMessage("sendingPosToNewPlayer", p.getX() + "", p.getY() + " " + p.getRole());
                this.sendMessage("sendingTasksLeft", recievedUID + "", tasksLeft + "");
                System.out.println("List of connected UIDs: " + connections.printString());
            }

            if(instruction.contains("sendingPosToNewPlayer")){
                System.out.println("updating player position");
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        System.out.println("got new position");
                        connections.get(i).getPlayer().setPos(Integer.parseInt(inputArray[2]), Integer.parseInt(inputArray[3]));
                        connections.get(i).getPlayer().setRole(Boolean.parseBoolean(inputArray[4]));
                    }
                }
            }

            else if(instruction.contains("sendingRole")){
                System.out.println("Goober: " + inputArray[2]);
                p.setRole(Boolean.parseBoolean(inputArray[2]));
            }

            else if(instruction.contains("sendingTasksLeft")){
                if(Integer.parseInt(inputArray[2]) == this.UID){
                    this.setRemainingTask(Integer.parseInt(inputArray[3]));
                }
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
                        connections.get(i).getPlayer().moveY(speed);
                    }
                }
            }

            else if(instruction.contains("moveUp")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        connections.get(i).getPlayer().moveY(speed * -1);
                    }
                }
            }

            else if(instruction.contains("moveLeft")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        connections.get(i).getPlayer().moveX(speed * -1);
                    }
                }
            }

            else if(instruction.contains("moveRight")){
                for(int i = 0; i < connections.size(); i++){
                    if(recievedUID == connections.get(i).getUID()){
                        connections.get(i).getPlayer().moveX(speed);
                    }
                }
            }

            else if(instruction.contains("taskComplete")){
                tasksLeft--;
                map.getTile(Integer.parseInt(inputArray[2]), Integer.parseInt(inputArray[3])).changeType(-1);
            }
        

            else if(instruction.contains("tag")){
                //if we are within one tile range then call tag
                System.out.println("checking if tagged");
                int gooberX = Integer.parseInt(inputArray[2]) + 290;
                int gooberY = Integer.parseInt(inputArray[3]) + 290;

                System.out.println("x diff: " + (Math.abs(p.getX() - gooberX)));
                System.out.println("y diff: " + (Math.abs(p.getY() - gooberY)));

                if(Math.abs(p.getX() - gooberX) < 300 && Math.abs(p.getY() - gooberY) < 300){
                    this.tag();
                }
            }

            else if(instruction.contains("playerTagged")){
                lives--;
            }

            
            
            repaint();

        }

        serverSocket.close();
	}

    public void closeSockets(){
        try{
            in.close();
            out.close();
            serverSocket.close();
            pin.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void sendMessage(String message){
        out.println(message + " " + UID);  
        out.flush(); 
    }

    public void sendMessage(String message, String message2, String message3){
        out.println(message + " " + UID + " " + message2 + " " + message3);
        out.flush();
    }

    public void completedTask(){
        tasksLeft--;
    }

    public void tag(){
        System.out.println("I've been tagged!");
        movementThread.suspend();
        p.tag();
        for(int i = 0; i < 5; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        lives--;
        this.sendMessage("playerTagged");
        if(lives > 0){
            p.setPos(300, 300);
            p.respawn();
            movementThread.resume();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == instructions){
            if(instruction){
                instruction = false;
            }
            else{
                instruction = true;
            }
        }
        else if(e.getSource() == gameStart){
            menu = false;
            this.animate();
            try {
                this.poll();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }        
        }
        repaint();
    }

    public void taskComplete(){
        tasksLeft--;
    }

    public void toggleInstructions(){
        if(instruction){
            instruction = false;
        }
        else{
            instruction = true;
        }
    }

    public void setRemainingTask(int tasksLeft){
        this.tasksLeft = tasksLeft;
    }
    
}