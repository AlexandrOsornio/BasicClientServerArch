package Server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    // Class is instantiated in a new thread every time a client connects

    private final Socket socket;
    private final PrintWriter pw;
    private final BufferedReader br;
    private boolean threadActive;

    ClientThread(Socket clientSocket) throws IOException {
        socket = clientSocket;
        OutputStream os = socket.getOutputStream();
        pw = new PrintWriter(os, true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        threadActive = true;
    }

    public void run(){
        //while (true) {
        try {
            // Sending data
            pw.println("Provide username and password for log in"); // Send str to client

            // Lines required to receive data

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
        }
        catch (IOException e){
            System.out.println(e);
            terminateConnection();
        }
        //}
    }

    private void terminateConnection(){
        threadActive = false;
        try{
            br.close();
            pw.close();
            socket.close();
        }
        catch (IOException ignore){}
    }

    public boolean getThreadStatus(){return threadActive;}
}
