package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Integer numOfConnectedClients;

    Server(){
        numOfConnectedClients = 0;
    }

    public int getNumOfConnectedClients(){
        return numOfConnectedClients;
    }


    public static void main(String args[]) throws IOException {
        final int portNumber = 2500;
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);


        while (true) {
            System.out.println("Server waiting for client connection...");
            Socket socket = serverSocket.accept();
            System.out.println("[+] Client has connected");

            System.out.println("[+] Creating new ClientThread");
            ClientThread ct = new ClientThread(socket);
            System.out.println("[+] Starting ClientThread");
            ct.start();

            //serverSocket.close();
        }
    }
}
