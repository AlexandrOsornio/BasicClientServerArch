package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Client {

    private final String ip;
    private final int portNumber; // 0-1023 to 65535

    private boolean connectedToServer;
    private Socket socket;
    private BufferedReader br;
    PrintWriter out;

    // Default constructor for testing on local host
    public Client(){
        ip = "localhost";
        portNumber = 2500;
        connectedToServer = false;
    }
    // Constructor must receive IP and PortNumber of the server
    public Client(String IP, int PortNumber){
        ip = IP;
        portNumber = PortNumber;
        connectedToServer = false;
    }

    public void connectToServer() throws IOException, java.net.ConnectException {
        try{
            // Connect to server using Socket obj
            System.out.println("Creating socket to '" + ip + "' on port " + portNumber);
            socket = new Socket(ip, portNumber);

            // Create a BufferReader from socket connection to read data from server
            // BufferReader gets data from InputStreamReader which received it through the socket's input stream
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create PrintWriter from socket to send data to the server
            // PrintWriter sends data to the server using the socket's output stream and flushes the buffer
            out = new PrintWriter(socket.getOutputStream(), true);

            connectedToServer = true;
        }
        catch (java.net.ConnectException e){
            throw new ConnectException();
        }

    }


    public static void main(String args[]) throws IOException, InterruptedException {




        /*String serverStr = br.readLine(); // Get str from server

        // Send user input to server and display it on screen
        out.println(username);
        out.println(password);

        // Get reply from server
        String serverReply = br.readLine();
        System.out.println("server says:" + serverReply);



        while (true) {
            try {

            }
            catch (java.net.ConnectException e){
                System.out.println("[-] Error: Could not connect to server, connection refused");
                System.out.println("Waiting 5 seconds to retry connection");
                Thread.sleep(5000);
            }
        }*/
    }
}
