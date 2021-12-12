package Server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;
import Database.Database;
import BootCampFiles.EmailManager;

public class ClientThread extends Thread{
    // Class is instantiated in a new thread every time a client connects

    private final Socket socket;
    private final PrintWriter pw;
    private final BufferedReader br;
    private boolean threadActive;
    private String username;
    private EmailManager em;
    private Database database = new Database();

    ClientThread(Socket clientSocket) throws IOException {
        socket = clientSocket;
        OutputStream os = socket.getOutputStream();
        pw = new PrintWriter(os, true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        threadActive = true;
        username = "notLoggedIn";
        em = new EmailManager("335Team6@gmail.com", "Team62021");

    }

    public void run(){
        // Sending data
        //pw.println("connected");
        while (true) {
            try {

                // Lines required to receive data
                String command = br.readLine();
                if(command!= null){
                    if(!command.equals("")){
                        if(command.equals("signup")){


                            String email = br.readLine();
                            String username = br.readLine();
                            String password = br.readLine();
                            System.out.println(password);

                            database.addUser(username, password, email);

                            //TODO: Insert user data into database
                            /*System.out.println("Signup data");
                            System.out.println(email);
                            System.out.println(username);
                            System.out.println(password);*/

                        }
                        else if(command.equals("login")){
                            String uname = br.readLine();
                            String password = br.readLine();
                            username = uname;                            

                            //TODO: Get credentials from database
                            String str = database.getUserPassword(uname);
                            System.out.println(str);
                            if (password.equals(str))
                                pw.println("loginsuccess");
                            else
                                pw.println("loginfail");
                        }
                        else if(command.equals("logout")){
                            terminateConnection();
                            break;
                        }
                        else if(command.equals("changepass")){

                            String newPassword = br.readLine();
                            String confirmPassword = br.readLine();

                            if(newPassword.equals(confirmPassword)){
                                //TODO: Update database with new passwords
                                database.updatePassword(username, newPassword);
                                pw.println("changepasssuccess");
                            }
                            else {
                                pw.println("changepassfail");
                            }
                        }
                        else if(command.equals("resetpass")){

                            int newPass = ThreadLocalRandom.current().nextInt(8, 16 + 1);

                            //TODO: Get user's email from database
                            //TODO: Update password in database

                            String userEmail = database.getUserEmail(username);
                            database.updatePassword(username, Integer.toString(newPass));
                            em.sendEmail(userEmail, "Account recovery", Integer.toString(newPass));
                        }
                    }
                }

                Thread.sleep(200);
            }
            catch (IOException | InterruptedException e){
                System.out.println(e);
                terminateConnection();
                break;
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
