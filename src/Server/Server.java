package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String args[]) throws IOException {
        final int portNumber = 2500;
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);


        while (true) {
            System.out.println("Server waiting for client connection");
            Socket socket = serverSocket.accept();
            System.out.println("Client has connected");

            // Lines required to send data
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("Provide username and password for log in"); // Send str to client

            // Lines required to receive data
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String username = br.readLine(); // Get str from client
            String password = br.readLine(); // Get str from client

            try{
                if(username.equals("reinhart") && password.equals("1234"))
                    pw.write("Login successful");
                else
                    pw.write("Incorrect username or password");
            }catch (NullPointerException e){
                System.out.println("[-] Error: Client disconnected");
            }

            pw.close();
            socket.close();
        }
    }
}
