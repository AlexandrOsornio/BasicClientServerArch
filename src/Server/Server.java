package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Database.Database;

public class Server extends Thread{

    private final int portNumber;
    private int numOfConnectedClients;
    private final ArrayList<ClientThread> threadList;
    private Database db = new Database();

    public Server(){
        portNumber = 2500;
        numOfConnectedClients = 0;
        threadList = new ArrayList<>();
        //db = new Database();
    }

    public int getNumOfConnectedClients(){
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

            return activeThreadCounter;
/*

            try {
                Thread.sleep(2500);
            }
            catch (InterruptedException e){}
*/

            //System.out.println(numOfConnectedClients);
        }
    }
    public String[] getUsernamesOfLoggedInUsers(){
        String[] fullList;
        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i<threadList.size(); i++){
            String usr = threadList.get(i).getUsername();
            if(!usr.equals("notLoggedIn"))
                list.add(usr);
        }

        fullList = new String[list.size()];

        for(int i = 0; i<list.size(); i++){
            fullList[i] = list.get(i);
        }

        return fullList;

    }
    public String[] getUsernamesOfConnectedUsers(){
        return getUsernamesOfLoggedInUsers();
    }
    public int getNumOfLoggedInUsers(){
        return getUsernamesOfLoggedInUsers().length;
    }
    public int getNumOfRegisteredUsers(){
        //TODO: Get string array from database and get its length
        return db.getUserList().length;
    }
    public String[] getLockedOutUsers(){
        //TODO: Get string array from database
        ArrayList t = new ArrayList<String>();
        String[] lockedOutUsers = {""};

        String [] users = db.getUserList();

        for (int i = 0; i <users.length; i++)
        {
            if (db.getUserLockoutCount(users[i]) >= 3)
            {
                t.add(users[i]);
            }
        }
        lockedOutUsers = new String[t.size()];
        for (int i = 0; i < lockedOutUsers.length; i++)
        {
            lockedOutUsers[i] =(String) t.get(i);
        }
        
        return lockedOutUsers;
    }
    

    // -- Threaded Methods
    @Override
    public void run(){
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
                System.out.println("Size after adding: " + threadList.size());

                //serverSocket.close();
            }
        }catch (IOException e){
            System.out.println("[-] Error: Socket error, cannot listen for incoming connections");
        }
    }

    /*public void StartServer(){
        // Create the_server obj
        Server the_server = new Server();

        // Create threads for establishing and monitoring connections
        Thread connectionsThread = new Thread(the_server::listenForIncomingConnections);
        Thread monitorThread = new Thread(the_server::monitorConnectedClientThreads);

        // Start threads
        connectionsThread.start();
        monitorThread.start();
    }*/

    //SchwarzHole637#%
}
