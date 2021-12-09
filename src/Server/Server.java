package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

    private final int portNumber;
    private Integer numOfConnectedClients;
    private ArrayList<ClientThread> threadList;

    Server(){
        portNumber = 2500;
        numOfConnectedClients = 0;
        threadList = new ArrayList<ClientThread>();
    }

    public int getNumOfConnectedClients(){
        return numOfConnectedClients;
    }


    private void listenForIncomingConnections(){
        // -- Method is constantly running listening for incoming client
        // connections. When a client connects it creates a thread for
        // that individual connection and goes back to listening again

        try{
            // Create socket to listen for incoming connections
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (true) {
                // Accept any connection
                Socket socket = serverSocket.accept();

                // Create client handler obj
                ClientThread ct = new ClientThread(socket);
                // Start thread of client handler
                ct.start();
                // Add thread to list of connected client threads
                threadList.add(ct);

                //serverSocket.close();
            }
        }catch (IOException e){
            System.out.println("[-] Error: Socket error, cannot listen for incoming connections");
        }


    }

    private void monitorConnectedClientThreads(){
        // -- Method checks ArrayList of connected client threads and
        // updates params for use in serverGUI

        while(true){
            int activeThreadCounter = 0;

            for(int i = 0; i<threadList.size(); i++){
                if(threadList.get(i).getThreadStatus())
                    activeThreadCounter++;
                else
                    threadList.remove(i);
            }

            numOfConnectedClients = activeThreadCounter;

            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e){}

            System.out.println(numOfConnectedClients);
        }
    }

    public static void main(String[] args){
        // Create server obj
        Server server = new Server();

        // Create threads for establishing and monitoring connections
        Thread connectionsThread = new Thread(() -> server.listenForIncomingConnections());
        Thread monitorThread = new Thread(() -> server.monitorConnectedClientThreads());

        // Start threads
        connectionsThread.start();
        monitorThread.start();
    }
}
