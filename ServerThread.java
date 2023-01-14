import java.io.*;
import java.net.*;

//connects to manager and one client screen

public class ServerThread implements Runnable{
    private Manager manager;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private int UID;
    
    public ServerThread(Manager manager, Socket clientSocket){
        this.manager = manager;
        this.clientSocket = clientSocket;
        System.out.println("Server: made a connection");
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
       
    }
    public void run(){
        //listen for messages
        //update when new message
        
        try {
            String fromClient = in.readLine();
            int clientUID = Integer.parseInt(fromClient);
            UID = clientUID;
            System.out.println("Thread: recieved UID from " + UID);
            manager.add(this);
            manager.newConnection(clientUID);

            while(true){
                String instruction = in.readLine();
                manager.sendMessage(instruction);
            }

            //outObject.writeObject(list of current objects);
        } catch (IOException e) {
            System.out.println(e);
            //TODO: handle exception
            try{
                clientSocket.close();
            }catch(IOException e1){
                System.out.println(e1);
            }
        }
    }

    public int getUID(){
        return UID;
    }
    

    public void sendMessage(String message){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        System.out.println("Thread: sending message to client");
        System.out.println("Thread: Message is: " + message);
        out.println(message);
        out.flush();        
    }

    public String toString(){
        return UID + " ";
    }
}

