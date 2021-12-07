package TestPackage;

import BootCampFiles.EmailManager;

public class testerFile {


    public static void main(String[] args){


        EmailManager emailManager = new EmailManager("aosornio@callutheran.edu", "<<password>>");

        emailManager.sendEmail("aosornio@callutheran.edu","TestEmail2", "LeContents");

    }
}
