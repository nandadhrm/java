package connect;

import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlConnection {
	private static Connection koneksi;
	
public static Connection GetConnection()throws SQLException{
	if(koneksi==null) {
		new Driver();
		String url ="jdbc:mysql://localhost/persewaan";
		String user ="root";
		String pass ="";
		koneksi = DriverManager.getConnection(url,user,pass);	
	}
	
	return koneksi;
}
}
