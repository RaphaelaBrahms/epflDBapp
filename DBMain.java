package interfacepack;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBMain {
	public static Connection connection;	//made it global
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainWindowFrame main = new MainWindowFrame();
		//main.add(cp);
		main.validate();
				
		//connectDB();	
	}

	
	public static void connectDB(){
		// oracle database connection testing, download JAR file first from oracle website
		System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            //eclipse => project - build path - configure build path - libraries - add external jar
            e.printStackTrace();
            return;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        //Connection connection = null;
        connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@diassrv2.epfl.ch:1521:orcldias", "DB2017_G27", "DB2017_G27");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
	}
	

}



