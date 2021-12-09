package Database;

import java.sql.*;

public class Database 
{
    
    // -- objects to be used for database access
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rset = null;

    // -- connect to the world database
    // -- this is the connector to the database, default port is 3306
//    private String url = "jdbc:mysql://localhost:3306/world";
    private String url = "jdbc:mysql://localhost:3306/jeffery";
    
    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private static String username = "root";
    private static String password = "admin";

    public Database() {
    	try {
			// -- make the connection to the database
			conn = DriverManager.getConnection(url, username, password);
	        
			// -- These will be used to send queries to the database
	        stmt = conn.createStatement();
	        rset = stmt.executeQuery("SELECT VERSION()");
	
	        if (rset.next()) {
	            System.out.println("MySQL version: " + rset.getString(1) + "\n=====================\n");
	        }
		} 
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
    	
    }
    
    public void addUser(String username, String Password, String email)
    {
        try
        {
            stmt.executeUpdate("INSERT INTO users VALUE("+ username + ", '"+password+"', '"+email+"', 0);");
        }
        catch (Exception e)
        {

        }
    }

    public String getUserPassword(String username)
    {
        String str = "";
        try
        {
            ResultSet rs = stmt.executeQuery("select password from users where username='"+username+"'");
            str = rs.getString(2);
        }
        catch(Exception e)
        {

        }
        return str;
    }

    public String getUserEmail(String username)
    {
        String str = "";
        try
        {
            ResultSet rs = stmt.executeQuery("select email from users where username='"+username+"'");
            str = rs.getString(3);
        }
        catch(Exception e)
        {

        }
        return str;
    }

    public int getUserLockoutCount(String username)
    {
        int lc = 0;
        try
        {
            ResultSet rs = stmt.executeQuery("select password from users where username='"+username+"'");
            lc = rs.getInt(4);
        }
        catch(Exception e)
        {

        }
        return lc;
    }

    public void updateLockoutCount(String username, int newCount)
    {
        try
        {
            stmt.executeUpdate("UPDATE users SET lockcount="+newCount+" WHERE username='"+username+"';");
        }
        catch(Exception e)
        {
            
        }
    }

    public void updatePassword(String username, String newPassword)
    {
        try
        {
            stmt.executeUpdate("UPDATE users SET password="+newPassword+" WHERE username='"+username+"';");
        }
        catch(Exception e)
        {
            
        }
    }
}
