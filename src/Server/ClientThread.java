package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
                            String uname = br.readLine();
                            String password = br.readLine();

                            if(checkEmailFormat(email) && checkUsername(uname) && checkPasswordContents(password))
                            {
                                database.addUser(uname, password, email);
                                pw.println("signupsuccess");
                            }
                            else{
                                pw.println("signupfail");
                            }

                        }
                        else if(command.equals("login")){
                            String uname = br.readLine();
                            String password = br.readLine();
                            username = uname;

                            // Get user data from database
                            int lockoutCount = database.getUserLockoutCount(username);
                            String dbPassword = database.getUserPassword(username);

                            if (password.equals(dbPassword) && lockoutCount < 3){
                                pw.println("loginsuccess");
                                database.updateLockoutCount(username,0); // reset lockout count
                            }
                            else{
                                pw.println("loginfail");
                                database.updateLockoutCount(username,lockoutCount + 1); // increase lockout count
                            }

                        }
                        else if(command.equals("logout")){
                            terminateConnection();
                            break;
                        }
                        else if(command.equals("changepass")){

                            String newPassword = br.readLine();
                            String confirmPassword = br.readLine();

                            if(newPassword.equals(confirmPassword) && checkPasswordContents(newPassword)){
                                database.updatePassword(username, newPassword);
                                pw.println("changepasssuccess");
                                database.updateLockoutCount(username,0); // reset lockout count
                            }
                            else {
                                pw.println("changepassfail");
                            }
                        }
                        else if(command.equals("resetpass")){
                            //get username to recover
                            username = br.readLine();
                            genNewPass(); // generate and send new password to the user
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

    public boolean checkEmailFormat(String email) {
        //is email format valid? If yes return true, if not then return false;
        boolean validEmailFormat = false;
        if(email.contains("@")){ //all of the requirements of email format
            validEmailFormat = true;
        }
        return validEmailFormat;
    }

    public boolean checkPasswordContents(String password) {
        boolean validPasswordContents = true;
        if (password.length() < 8 || password.length() > 64) {
            validPasswordContents = false;
        } else if (password.contains("\\")){//what a password cannot have in it
            validPasswordContents = false;
        } else if (password.contains("\'")){//what a password cannot have in it
            validPasswordContents = false;
        } else if (password.contains("`")){//what a password cannot have in it
            validPasswordContents = false;
        } else if (password.contains("(")){//what a password cannot have in it
            validPasswordContents = false;
        } else if (password.contains(")")){//what a password cannot have in it
            validPasswordContents = false;
        }
        return validPasswordContents;
    }

    public boolean checkUsername(String username) {
        boolean validUsername = true;
        //String possibleUsernameChar = "abcdefghijklmnopqrstuvwxyz1234567890.";
        //String usernameChar = "";
        if (username.length() < 3 || username.length() > 64) {
            validUsername = false;
            return validUsername;
        } else if (username.contains("\\")){//what a password cannot have in it
            validUsername = false;
        } else if (username.contains("\'")){//what a password cannot have in it
            validUsername = false;
        } else if (username.contains("`")){//what a password cannot have in it
            validUsername = false;
        } else if (username.contains("(")){//what a password cannot have in it
            validUsername = false;
        } else if (username.contains(")")){//what a password cannot have in it
            validUsername = false;
        }

        /*for (int i = 0; i < username.length(); i++) { //checks each individual char of username for valid characters
            if (username.length()-1 > i) {
                usernameChar = username.substring(i, i+1);
            } else if (username.length()-1 == i) {
                usernameChar = username.substring(i);
            }
            if (!possibleUsernameChar.contains(usernameChar)) {
                validUsername = false;
                return validUsername;
            }
        }*/

        return validUsername;
    }

    public void genNewPass(){
        int newPass = ThreadLocalRandom.current().nextInt(10000000, 99999999 + 1);

        String userEmail = database.getUserEmail(username);
        database.updatePassword(username, Integer.toString(newPass));
        em.sendEmail(userEmail, "Account recovery","New password: " + Integer.toString(newPass));

        database.updateLockoutCount(username,0); // reset lockout count
    }

    public boolean getThreadStatus(){return threadActive;}
    public String getUsername(){return username;}
}
