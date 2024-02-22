package car_workshop;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

	private UserDAO userDao;
	private UserView userView;
	ResultSet checkResult;
	private String displayReturned;
	private WorkshopController workshopController;
	
	public UserController(UserDAO userDao, UserView userView,WorkshopController workshopController) {
		this.userDao=userDao;
		this.userView=userView;
		this.workshopController=workshopController;
	}

	public void register() throws Exception {
	    boolean continueCustomerDashBoard=true;;
		boolean validInput = false;
	    do {
		    String regChoice = userView.getRegChoice();
		    UserDetailModel userMl = userView.getUserDetails();
	
		    if (regChoice.equalsIgnoreCase("signin")) {
		        signIn(userMl);
		        validInput = true;
		    
		    } else if (regChoice.equalsIgnoreCase("signup")) {
		        signUp(userMl);
		        validInput = true;
		    } 
		    else if (regChoice.equalsIgnoreCase("exit")) {
		        userView.display("Thanks For visiting");
		        return;
		    } 
		    else {
		        userView.invalid();
		    }
		    String role=userDao.getRole(userMl.getMobilenum());
		    if(role.equalsIgnoreCase("admin")) {
		    	workshopController.adminDashBoard();
		    }
		    else {
		    	do {
		    		continueCustomerDashBoard=workshopController.customerDashBoard(userDao.getUserId(userMl.getMobilenum()));
		    	}while(continueCustomerDashBoard);
		    }
	}while (!validInput);
}
	public void signIn(UserDetailModel userMl) throws SQLException {
	   
	        checkResult = userDao.searchUser(userMl.getMobilenum(),userMl.getPassword());
	        if (checkResult.next()) {
	            String role = checkResult.getString("role");
	            if ("admin".equals(role)) {
	                userView.displayWelcome("Welcome Back Admin " + checkResult.getString("username"));
	            } else {
	                userView.displayWelcome("Welcome Back Customer " + checkResult.getString("username"));
	            }
	        } else {
	            userView.display("Mobile number doesn't exist, proceeding to sign up.");
	            signUp(userMl);
	        }
	   
	}

	public void signUp(UserDetailModel userMl) throws SQLException {
	    checkResult = userDao.searchUser(userMl.getMobilenum(),userMl.getPassword());
	    if (checkResult.next()) {
	    	userView.display("Already exist");
	       signIn(userMl);
	    } else {
	        userMl.setRole("customer");
	        displayReturned = userDao.insertCustomer(userMl.getUserName(), userMl.getMobilenum(), userMl.getRole(), userMl.getPassword());
	        userView.displayWelcome(displayReturned);
	    }
	}
	

    // Validate mobile number
    public boolean isValidMobileNumber(String mobileNumber) {
        String regex = "^[6-9]\\d{9}$";
        return mobileNumber.matches(regex);
    }

    // Validate password
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(regex);
    }
}