package car_workshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
public class Main {

	public static void main(String[]args) throws Exception {

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/carservice","root","geeths_99");
        Statement st = conn.createStatement();
        
		UserDetailModel userDetail=new UserDetailModel();
		UserDAO userDao=new UserDAO(conn,st);
		UserView userView=new UserView(userDetail);
		
		ServiceModel service=new ServiceModel();
        CarDetailModel carDetail =new CarDetailModel();
        BookingDetailModel bookingDetail=new BookingDetailModel();
		BookedServiceModel bookedService=new BookedServiceModel();
		PaymentModel payment =new PaymentModel();
		
		WorkshopDAO workshopDao = new WorkshopDAO(conn,st);
		WorkshopView workshopView = new  WorkshopView(carDetail ,bookingDetail,bookedService,payment ,service);
		WorkshopController workshopController = new WorkshopController(workshopDao,workshopView,carDetail ,bookingDetail,bookedService,payment ,service);
        UserController userController=new UserController(userDao,userView,workshopController);
        userController.register();
	}
}
