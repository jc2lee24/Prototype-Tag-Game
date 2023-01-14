import java.io.*;
import java.net.*;

public class Server{
    public static void main(String[] args) {
        
        Manager m = new Manager();
        try {
            boolean run = true;
            int portNumber = 1024;
            ServerSocket serverSocket = new ServerSocket(portNumber);
            //have a while loop waiting for connection
            while(run){
                System.out.println("Waiting for a connection");

                //when make connection, start thread connecting them
                Socket clientSocket = serverSocket.accept();

                //Once a connection is made, run the socket in a ServerThread
                // thread is connected to that specific client
                ServerThread serverThread = new ServerThread(m, clientSocket);
                Thread thread = new Thread(serverThread);
                thread.start();
                //wait for new connection

            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}