package CommandProcessor;

import java.util.ArrayList;

public class CommandProcessor {
    
    public CommandProcessor() {

    }

    public boolean resetGUI() { //reset the GUI text fields
        return true;
    }

    public boolean messageUser(String message) {
        //popup display with message on it
        return true;
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
        ArrayList<String> blockList = new ArrayList<String>(); //if we have words that we do not want to be part of a password, we can put it into here as a String
        if (8 > password.length() && password.length() > 64) {
            validPasswordContents = false;
        } else if (password.contains("\\")){//what a password cannot have in it
            validPasswordContents = false;
            System.out.println("contains \" \\ \"");
        } else if (validPasswordContents) {
            for (int i = 0; i < blockList.size(); i++) {
                if (password.contains(blockList.get(i))) {
                    validPasswordContents = false;
                }
            }
        }
        return validPasswordContents;
    }

    public boolean checkUsername(String username) {
        boolean validUsername = true;
        String possibleUsernameChar = "abcdefghijklmnopqrstuvwxyz1234567890.";
        String usernameChar = "";
        if (username.length() < 3 && username.length() > 64) {
            validUsername = false;
            return validUsername;
        } else if (validUsername) {
            for (int i = 0; i < username.length(); i++) { //checks each individual char of username for valid characters
                if (username.length()-1 > i) {
                    usernameChar = username.substring(i, i+1);
                } else if (username.length()-1 == i) {
                    usernameChar = username.substring(i);
                }
                if (!possibleUsernameChar.contains(usernameChar)) {
                    validUsername = false;
                    return validUsername;
                }
            }
        }
        return validUsername;
    }
}
