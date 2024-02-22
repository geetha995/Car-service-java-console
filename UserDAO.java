package car_workshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDAO implements UserDAOOverview {

	private Connection conn;
	private Statement st;
	private String checkQuery;
	private ResultSet checkResult;
	private String insertQuery;
	private String selectQuery;
	private PreparedStatement preparedStatement;
	
	public UserDAO(Connection conn, Statement st) {
		this.conn=conn;
		this.st=st;
	}

	public ResultSet searchUser(String mobilenum, String password) throws SQLException {
		checkQuery = "SELECT * FROM userDetail WHERE mobilenum='" + mobilenum + "'"+"AND password='"+password+"'";
        checkResult = st.executeQuery(checkQuery);

		return checkResult;
	}

	public String insertCustomer(String username, String mobilenum, String role, String password) throws SQLException {
		 insertQuery = "INSERT INTO userDetail(username,mobilenum,role,password) VALUES (?, ?, ?, ?)";

		 		preparedStatement = conn.prepareStatement(insertQuery);
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, mobilenum);
	            preparedStatement.setString(3,role);
	            preparedStatement.setString(4,password);

	    
	        int rowsAffected=preparedStatement.executeUpdate();
	        if(rowsAffected>0)
	      	  return "Welcome customer "+username;
	      return "User not added";
	}

	public String getRole(String mobilenum) throws Exception{
		selectQuery="Select role from userDetail where mobilenum='"+mobilenum+"'";
		preparedStatement=conn.prepareStatement(selectQuery);
		checkResult=preparedStatement.executeQuery();
		checkResult.next();
		return checkResult.getString("role");
	}

	public int getUserId(String mobilenum) throws Exception {
		selectQuery= "SELECT userId FROM userDetail WHERE mobilenum = ?";
        preparedStatement = conn.prepareStatement(selectQuery);
        preparedStatement.setString(1, mobilenum);
        checkResult = preparedStatement.executeQuery();
        if(checkResult.next()) 
         return checkResult.getInt("userId");
        return 0;
	}

}
