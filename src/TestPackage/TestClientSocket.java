package TestPackage;

import java.io.*;
import java.net.Socket;

public class TestClientSocket {

    public static void main(String args[]) throws IOException, InterruptedException {
        final String ip = "localhost";
        final int portNumber = 2500; // 0-1023 to 65535
        System.out.println("Creating socket to '" + ip + "' on port " + portNumber);

        while (true) {
            try {
                // Connect to server using Socket obj
                Socket socket = new Socket(ip, portNumber);

                // BufferReader gets data from InputStreamReader which received it through the socket's input stream
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String serverStr = br.readLine(); // Get str from server

                System.out.println("Server says: " + serverStr);

                // PrintWriter sends data to the server using the socket's output stream and flushes the buffer
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Capture user input
                BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Username: ");
                String username = userInputBR.readLine();
                System.out.print("Password: ");
                String password = userInputBR.readLine();

                if ("exit".equalsIgnoreCase(username)) {
                    socket.close();
                    break;
                }

                // Send user input to server and display it on screen
                out.println(username);
                out.println(password);

                // Get reply from server
                String serverReply = br.readLine();
                System.out.println("server says:" + serverReply);


            }
            catch (java.net.ConnectException e){
                System.out.println("[-] Error: Could not connect to server, connection refused");
                System.out.println("Waiting 5 seconds to retry connection");
                Thread.sleep(5000);
            }
        }
    }
}