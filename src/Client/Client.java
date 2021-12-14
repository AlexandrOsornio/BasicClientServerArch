package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private final String ip;
    private final int portNumber; // 0-1023 to 65535

    private boolean connectedToServer;
    private Socket socket;
    private BufferedReader br;
    PrintWriter printWriterOut;

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

    public void connectToServer() throws IOException {
        try{
            // Connect to server using Socket obj
            System.out.println("Creating socket to '" + ip + "' on port " + portNumber);
            socket = new Socket(ip, portNumber);

            // Create a BufferReader from socket connection to read data from server
            // BufferReader gets data from InputStreamReader which received it through the socket's input stream
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create PrintWriter from socket to send data to the server
            // PrintWriter sends data to the server using the socket's output stream and flushes the buffer
            printWriterOut = new PrintWriter(socket.getOutputStream(), true);

            connectedToServer = true;
        }
        catch (java.net.SocketException e){
            throw new java.net.SocketException();
        }
    }

    public void disconnectFromServer() throws IOException {
        if(connectedToServer){
            br.close();
            printWriterOut.close();
            socket.close();
            connectedToServer = false;
        }
        else{
            System.out.println("[-] Cannot disconnect when not connected");
        }

    }

    public String fetchDataFromServer() throws IOException {
        return br.readLine();
    }

    public void sendMessage(String strToSend){
        printWriterOut.println(strToSend);
    }
}
