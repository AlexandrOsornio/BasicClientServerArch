package Client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class ClientGUI extends JFrame {
    
    //creates final variables for the size of the JFrame
    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    String serverMessageContent = "bottom text";

    public boolean lockedOut = false;

    //create all the components
    //these will later be initalized inside the methods that define the JPanels
    //they will also be added to the below JPanels
    JTextArea password;
    JTextArea password2;
    JTextArea password3;
    JTextArea username;
    JTextArea username2;
    JLabel p;
    JLabel u;
    JButton submit;
    JButton submitLogIn;
    JButton signUp;
    JButton logOutButton;
    JButton changePasswordButton;
    JButton resetPasswordSubmit;
    JButton recoverPass;
    JScrollPane pass;
    JScrollPane pass2;
    JScrollPane pass3;
    JScrollPane user;
    JScrollPane user2;
    JTextArea confirm;
    JTextArea email;
    JLabel c;
    JLabel e;
    JScrollPane con;
    JScrollPane male;
    JScrollPane contentPane;
    JLabel serverMessages;
    //Timer UpdateClock;
    

    //create JFrame that will be used as a value to refrence itself
    //used for accessing the JFrame outside of its constructor
    JFrame frame;

    //Creates the 4 JPanels that hold the UI
    //the visibility of the panels is toggled to switch between them
    JPanel LogInUser = logIn();
    JPanel SignUpUser = signUp();
    JPanel DisplayContent = loggedIn();
    JPanel newPassword = changePassword();

    public final String IP = "localhost";
    public final int PORT = 2500;

    Client client = new Client();

    //constructor for the JFrame
    public ClientGUI()
	{
		// -- construct the base JFrame first
		super();
        
        //initalizes the self refirencial JFrame
        frame = this;
		
		// -- set the application title
		this.setTitle("PerfectNumberGui");
				
		// -- initial size of the frame: width, height
		this.setSize(WIDTH, HEIGHT);
		
		// -- center the frame on the screen
		this.setLocationRelativeTo(null);
		
		// -- shut down the entire application when the frame is closed
		//    if you don't include this the application will continue to		
		//    run in the background and you'll have to kill it by pressing
		//    the red square in eclipse
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -- can make it so the user cannot resize the frame
		this.setResizable(false);
        
		// -- show the frame on the screen
		this.setVisible(true);

        //displays the starting state of the client which is the log in screen
        this.add(LogInUser);

        try 
        {
            System.out.println("1");
            client.connectToServer();
            System.out.println("2");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
	}

    //following are the methods that are used to define The 4 JPanels
    //each will initalize all of the previously created objects inside of themselves

    public JPanel logIn()
    {
        //Defines the log in screen's JPanel

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(28, 28, 28, 28));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//add the components to the pannel and set their location
		//-------------------------------------------------------
        
        u = new JLabel("username");
		u.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(u);


        username2 = new JTextArea("",1, 500);
        user2 = new JScrollPane(username2);
        user2.setAutoscrolls(true);
        user2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		user2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(user2);

        p = new JLabel("password");
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p);

        password = new JTextArea("",1, 500);
        pass = new JScrollPane(password);
        pass.setAutoscrolls(true);
        pass.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pass.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pass);

        submitLogIn = new JButton("submit");
		submitLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitLogIn.addActionListener(new LogInSubmit());
        panel.add(submitLogIn);

        signUp = new JButton("sign up");
		signUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUp.addActionListener(new signUpListener());
        panel.add(signUp);
        recoverPass = new JButton("recover password");
        recoverPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        recoverPass.addActionListener(new newPassord());
        panel.add(recoverPass);

		//---------------------------------------------------------

        return panel;
    }

    public JPanel signUp()
    {
        //defines the sign Up screen's JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(28, 28, 28, 28));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//add the components to the pannel and set their location
		//-------------------------------------------------------
        u = new JLabel("username");
		u.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(u);


        username = new JTextArea("",1, 500);
        user = new JScrollPane(username);
        user.setAutoscrolls(true);
        user.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		user.setAlignmentX(Component.CENTER_ALIGNMENT);

        p = new JLabel("password");
		p.setAlignmentX(Component.CENTER_ALIGNMENT);

        password2 = new JTextArea("",1, 500);
        pass2 = new JScrollPane(password2);
        pass2.setAutoscrolls(true);
        pass2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pass2.setAlignmentX(Component.CENTER_ALIGNMENT);

        submit = new JButton("submit");
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.addActionListener(new SubmitNewUserCredentials());
        

        e = new JLabel("email");

        email = new JTextArea("",1, 5);
        male = new JScrollPane(email);
        male.setAutoscrolls(true);
        male.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        e.setAlignmentX(Component.CENTER_ALIGNMENT);
        male.setAlignmentX(Component.CENTER_ALIGNMENT);

        signUp = new JButton("sign up");
		signUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUp.addActionListener(new signUpListener());

		//---------------------------------------------------------

        panel.add(u);
        panel.add(user);
        panel.add(e);
        panel.add(male);
        panel.add(p);
        panel.add(pass2);
        panel.add(submit);


        return panel;
    }

    public JPanel changePassword()
    {
        //defines the change password screen's JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(28, 28, 28, 28));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//add the components to the pannel and set their location
		//-------------------------------------------------------

        c = new JLabel("confirm password");
        confirm = new JTextArea("",1, 5);
        con = new JScrollPane(confirm);
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        con.setAutoscrolls(true);
        con.setAlignmentX(Component.CENTER_ALIGNMENT);
        con.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        p = new JLabel("password");
		p.setAlignmentX(Component.CENTER_ALIGNMENT);

        password3 = new JTextArea("",1, 500);
        pass3 = new JScrollPane(password3);
        pass3.setAutoscrolls(true);
        pass3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pass3.setAlignmentX(Component.CENTER_ALIGNMENT);

        resetPasswordSubmit = new JButton("submit");
        resetPasswordSubmit.addActionListener(new ResetPassword());
        resetPasswordSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(p);
        panel.add(pass3);
        panel.add(c);
        panel.add(con);
        panel.add(resetPasswordSubmit);

        return panel;
    }

    public JPanel loggedIn()
    {
        //Defines the logged in screen's JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(28, 28, 28, 28));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//add the components to the pannel and set their location
		//-------------------------------------------------------

        logOutButton = new JButton("log out");
        logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOutButton.addActionListener(new LogOutUser());
        panel.add(logOutButton);

        changePasswordButton = new JButton("change password");
        changePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordButton.addActionListener(new DisplayResetPassword());
        panel.add(changePasswordButton);

        serverMessages = new JLabel(serverMessageContent);
        contentPane = new JScrollPane(serverMessages);
        contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(contentPane);

        //UpdateClock = new Timer(500,new RefreshContent());
        //UpdateClock.start();

        return panel;
    }


    //these will define the action Listeners
    //these will define what happens upon certain events, most notably when buttons are pushed
    // they maily call methods that will pass info to the server, switch between JPanels, and clear fields

    class signUpListener implements ActionListener
    {
        //user is sent to the sign up screen
        @Override
        public void actionPerformed(ActionEvent e) {
            //switches between the log in and sign up screens
            //adds the sign up
            //changes the visibility of panels
            //calls repaint

            frame.add(SignUpUser);
            SignUpUser.setVisible(true);
            LogInUser.setVisible(false);
            LogInUser.repaint();
            SignUpUser.repaint();         
            frame.repaint();

        }
        
    }

    class LogInSubmit implements ActionListener //throws IOException
    {
        //if user is valid user is sent to the logged in screen
        //if not fields are cleared and they remain there
        @Override
        public void actionPerformed(ActionEvent e) {
            try
            {
                if (authenticateUser(username2.getText(), password.getText()))
                {
                    username2.setText("");
                    password.setText("");
                    frame.add(DisplayContent);
                    DisplayContent.setVisible(true);
                    LogInUser.setVisible(false);
                    LogInUser.repaint();
                    DisplayContent.repaint();
                    frame.repaint();
                }
                else
                {
                    username2.setText("");
                    password.setText("");
                }
            }
            catch (IOException exc)
            {

            }

            
        }

    }

    class DisplayResetPassword implements ActionListener
    {
        //user is sent from logged in screen to the change password screen
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.add(newPassword);
            newPassword.setVisible(true);
            DisplayContent.setVisible(false);
            DisplayContent.repaint();
            newPassword.repaint();
            frame.repaint();
            
        }

    }

    class ResetPassword implements ActionListener
    {
        //if passwords match and are valid the user is sent to the log in screen and logged out
        @Override
        public void actionPerformed(ActionEvent e) {
            try 
            {
                if (resetPassword(password3.getText(), confirm.getText()))
                {
                    password3.setText("");
                    confirm.setText("");
                    LogInUser.setVisible(true);
                    newPassword.setVisible(false);
                    newPassword.repaint();
                    LogInUser.repaint();
                    frame.repaint();
                    logOutUser();
                }
                else
                {
                    password3.setText("");
                    confirm.setText("");
                }
            }
            catch (IOException exc)
            {

            }
            
        }
        
    }

    class newPassord implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            client.sendMessage("resetpass\n" + username2.getText());
            password.setText("a new password has been sent to yuor email");
            LogInUser.repaint();
            frame.repaint();
        }
        
    }

    class LogOutUser implements ActionListener
    {
        //user is sent to the log in screen ad logged out
        @Override
        public void actionPerformed(ActionEvent e) {
            try
            {
                LogInUser.setVisible(true);
                DisplayContent.setVisible(false);
                DisplayContent.repaint();
                LogInUser.repaint();
                frame.repaint();
                logOutUser();
            }
            catch (IOException exc)
            {

            }
        }

    } 

    class SubmitNewUserCredentials implements ActionListener
    {
        //if a submitted credentals are valid and not already in use then user is sent to the log in screen
        @Override
        public void actionPerformed(ActionEvent e) {
            try 
            {
                SignUpUser.repaint();
                pass.repaint();
                System.out.println(password2.getText());
                if (createNewUser(username.getText(), password2.getText(), email.getText()))
                {
                    username.setText("");
                    password.setText("");
                    email.setText("");
                    LogInUser.setVisible(true);
                    SignUpUser.setVisible(false);
                    SignUpUser.repaint();
                    LogInUser.repaint();
                    frame.repaint();
                }
                else
                {
                    username.setText("");
                    password.setText("");
                    email.setText("");
                }
            }
            catch (IOException exc)
            {

            }
        }
        
    }
/*
    class RefreshContent implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            try
            {
                updateContent();
            }
            catch (IOException exc)
            {

            }
            
        }
        
    }
*/

    //other methods
    //mainly for comunicating with the server

    public boolean authenticateUser(String username, String password) throws IOException
    {
        //send over info to server to check for correctness
        //true if valid false if invalid
        client.sendMessage("login\n" + username + "\n" + password);
        String str = client.fetchDataFromServer();
        
        if(str.equals("loginsuccess"))
        {
            return true;
        }
        else if (str.equals("loginfail"))
        {
            return false;
        }
        else 
        {
            lockedOut = true;
            return false;
        }
    }

    public boolean createNewUser(String username, String password, String email) throws IOException
    {
        //send info to server
        //server attempts to create a new user
        //if it works return true
        //if it does not return false
        System.out.println(password);
        client.sendMessage("signup\n" + email + "\n" + username + "\n" + password);
        String str = client.fetchDataFromServer();
        /*
        if(str.equals("signupsuccess"))
        {
            return true;
        }
        else //signupfail
        {
            return false;
        }
        */
        return true;
    }

    public boolean resetPassword(String password, String confirmPassword) throws IOException
    {
        //send over password and confrim password
        //server resets password if it is valid
        //if true password was reset
        //if false password could not be reset
        client.sendMessage("changepass\n" + password + "\n" + confirmPassword);
        String str = client.fetchDataFromServer();
        
        if(str.equals("changepasssuccess"))
        {
            return true;
        }
        else //changepassfail
        {
            return false;
        }
    }

    public void logOutUser() throws IOException
    {
        //sends message to server to log out the user
        //server attempts to log out the user
        client.sendMessage("logout");
        String str = client.fetchDataFromServer();
    }
/*
    public void updateContent() throws IOException
    {
        client.sendMessage("updatecontent");
        serverMessageContent = client.fetchDataFromServer();
        serverMessages.setText(serverMessageContent);
        DisplayContent.repaint();
        frame.repaint();
    }
*/
	//main method used to run the client
    public static void main(String [] args)
    {
        new ClientGUI();
    }
}
