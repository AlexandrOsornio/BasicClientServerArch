package Database;

import java.sql.*;

public class Database 
{
    
    public Database() {

    }
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeffery","root","passwordTeam6");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select password from usercredentials where username=\"tom\"");
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        
    }

}
