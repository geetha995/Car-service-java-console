package car_workshop;

import carservice.UserModel;
import java.util.*;
public class UserView {
	Scanner in=new Scanner(System.in);
	private UserDetailModel userDetail;
	
	public UserView(UserDetailModel userDetail) {
		this.userDetail=userDetail;
	}

	public UserDetailModel getUserDetails() {
	 	System.out.println("Enter your name: ");
        userDetail.setUserName(in.nextLine());
        System.out.println("Enter your mobile number: ");
        userDetail.setMobilenum(in.next());
        System.out.println("\"Enter password [minimum 8 characters with at least 1 digit, 1 special character]: ");
        userDetail.setPassword(in.next());
		return userDetail;
	}
	
	public String getRegChoice() {
		System.out.println("Choose: \n SignIn\n SignUp\n Exit");
        return in.nextLine().replaceAll(" ","");

	}

	public void displayWelcome(String string) {
		System.out.println(string);
		
	}

	public void display(String string) {
		System.out.println(string);
	}

	public void invalid() {
		System.out.println("Invalid input");
		
	}

}
