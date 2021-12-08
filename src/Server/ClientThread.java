package Server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    // Class is instantiated in a new thread every time a client connects

    private Socket socket;

    ClientThread(Socket clientSocket){
        socket = clientSocket;
    }

    public void run(){
        //while (true) {
        try {
            // Lines required to send data
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("Provide username and password for log in"); // Send str to client

            // Lines required to receive data
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String username = br.readLine(); // Get str from client
            String password = br.readLine(); // Get str from client

            try {
                if (username.equals("reinhart") && password.equals("1234"))
                    pw.println("Login successful");
                else
                    pw.println("Incorrect username or password");
            } catch (NullPointerException e) {
                System.out.println("[-] Error: Client disconnected");
            }

            //pw.close();
            //socket.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        //}
    }
}
