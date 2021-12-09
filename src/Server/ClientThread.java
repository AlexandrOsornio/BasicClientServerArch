package Server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    // Class is instantiated in a new thread every time a client connects

    private final Socket socket;
    private final PrintWriter pw;
    private final BufferedReader br;
    private boolean threadActive;
    private String username;

    ClientThread(Socket clientSocket) throws IOException {
        socket = clientSocket;
        OutputStream os = socket.getOutputStream();
        pw = new PrintWriter(os, true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        threadActive = true;
        username = "notLoggedIn";
    }

    public void run(){
        while (true) {
            try {
                // Sending data
                pw.println("connected");

                // Lines required to receive data
                String command = br.readLine();

                if(command.equals("signup")){

                    String email = br.readLine();
                    String username = br.readLine();
                    String password = br.readLine();

                    /*System.out.println("Signup data");
                    System.out.println(email);
                    System.out.println(username);
                    System.out.println(password);*/

                }
                else if(command.equals("login")){
                    String username = br.readLine();
                    String password = br.readLine();

                    //TODO: Get credentials from database

                    if (username.equals("<<username>>") && password.equals("<<password>>"))
                        pw.println("Login successful");
                    else
                        pw.println("Incorrect username or password");
                }
                else if(command.equals("logout")){
                    terminateConnection();
                }
                else if(command.equals("changepass")){

                    String newPassword = br.readLine();
                    String confirmPassword = br.readLine();

                    if(newPassword.equals(confirmPassword)){
                        //TODO: Update database with new passwords
                        pw.println("password updated successfully");
                    }
                    else {
                        pw.println("passwords do not match");
                    }
                }
            }
            catch (IOException e){
                System.out.println(e);
                terminateConnection();
            }
        }
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
    public String getUsername(){return username;}
}
