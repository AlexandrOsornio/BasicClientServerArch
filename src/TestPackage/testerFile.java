package TestPackage;

//import BootCampFiles.EmailManager;
import Client.Client;

import java.io.IOException;

public class testerFile {


    public static void main(String[] args) throws IOException {

        //EmailManager emailManager = new EmailManager("aosornio@callutheran.edu", "<<password>>");
        //emailManager.sendEmail("aosornio@callutheran.edu","TestEmail2", "LeContents");

        Client client = new Client();

        try{
            client.connectToServer();

            String dataFromServer;
            //dataFromServer = client.fetchDataFromServer();
            //System.out.println(dataFromServer);

            client.sendMessage("signup\nusername@sample.com\nusername123\npassword123\nend");
            //client.sendMessage("1234");

            dataFromServer = client.fetchDataFromServer();
            System.out.println(dataFromServer);
        }
        catch (java.net.SocketException e){
            System.out.println("[-] Could not connect to server");
        }
    }
}
