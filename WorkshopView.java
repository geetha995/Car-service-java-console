package car_workshop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WorkshopView {
	Scanner in=new Scanner(System.in);
	private CarDetailModel carDetail;
	private BookingDetailModel bookingDetail;
	private BookedServiceModel bookedService;
	private PaymentModel payment;
	private ServiceModel service;
	
	public WorkshopView(CarDetailModel carDetail, BookingDetailModel bookingDetail,
			BookedServiceModel bookedService, PaymentModel payment, ServiceModel service) {
		this.carDetail=carDetail;
		this.bookingDetail=bookingDetail;
		this.bookedService=bookedService;
		this.payment=payment;
		this.service=service;
	}

	public String customerDashBoard() {
		System.out.println("Choose: 1.Service\n2.Pick Up\n3.Exit");
		return in.nextLine().toLowerCase();
	}

	public void invalid() {
		// TODO Auto-generated method stub
		System.out.println("Invalid input");
	}

	public String serviceMenu() {
		System.out.println("Choose: 1.Register car\n2.Remove car\n3.Exit");
		return in.nextLine();
	}

	public void getNumberPlate() {
		System.out.println("Enter the number plate");
		carDetail.setNumPlate(in.nextLine());
	}

	public void addVehicleDetail(int userId) {
		carDetail.setUserId(userId);
		System.out.println("Enter vehicle name");
		carDetail.setVehicleName(in.nextLine());
		System.out.println("Enter the car owned date (YYYY-MM-DD): ");
		carDetail.setVehicle_ownedDate(in.nextLine());
//		System.out.println("Enter the last free service date (YYYY-MM-DD): If not serviced then enter the vehicle owned date");
//		carDetail.setLast_FreeServiceDate(in.nextLine());
		System.out.println("Enter kms");
		carDetail.setKms(in.nextInt());
		in.nextLine();
		System.out.println("Enter how many no.of free services done.Enter 0 if no free serviced done");
		carDetail.setServiceCount(in.nextInt()); 
		//imp as it again goes to customerDashChoice bcoz of loop retrieve i/p which is nextLine
		in.nextLine();
	}

	public void display(String displayString) {
		System.out.println(displayString);
	}
	
	public void enterKms(int kms) {
		System.out.println("Enter the current kilometers your car ran");
		carDetail.setLastKms(kms);
		carDetail.setKms(in.nextInt());
		in.nextLine();
		
	}

	public void addCarMenu() {
		System.out.println("Choose: 1.Custom service\n2.Free Service\n3.Exit");
		System.out.println();
		bookingDetail.setServiceType(in.nextLine().trim());
		
	}

	public String getServiceTypeMenu() {
		System.out.println("Choose: 1.Display Services\n2.Add service\n3.Remove service\n4.Display my services\n5.Payment\n6.Exit");
		System.out.println();
		return in.nextLine().toLowerCase().trim();
	}

	public void displayServices(ResultSet displayResult) throws Exception{
		
		    while (displayResult.next()) {
		        String serviceId = displayResult.getString("serviceId");
		        String serviceName = displayResult.getString("service_name");
		        double price = displayResult.getDouble("price");
		        int hours = displayResult.getInt("hours");
		        String warranty = displayResult.getString("warranty");
		        String workerAvailability = displayResult.getString("workerAvailability");

		        System.out.println("Service ID: " + serviceId);
		        System.out.println("Service Name: " + serviceName);
		        System.out.println("Price: " + price);
		        System.out.println("Hours: " + hours);
		        System.out.println("Warranty: " + warranty);
		        System.out.println("Worker Availability: " + workerAvailability);
		    }
	}

	public void getAddress() {
		System.out.println("Enter a address[drop and pick up]");
		bookingDetail.setAddress(in.nextLine());
	}
	
	public String addServiceChoice(ResultSet displayResult) throws SQLException {
		while (displayResult.next()) {
	        String serviceId = displayResult.getString("serviceId");
	        String serviceName = displayResult.getString("service_name");
	        double price = displayResult.getDouble("price");
	        int hours = displayResult.getInt("hours");
	        String warranty = displayResult.getString("warranty");
	        String workerAvailability = displayResult.getString("workerAvailability");

	        System.out.println("Service ID: " + serviceId);
	        System.out.println("Service Name: " + serviceName);
	        System.out.println("Price: " + price);
	        System.out.println("Hours: " + hours);
	        System.out.println("Warranty: " + warranty);
	        System.out.println("Worker Availability: " + workerAvailability);
	        System.out.println();
	    }
	    System.out.println("Enter the name of the service you want to add: ");
	    return in.nextLine().trim();

	}

	public String removeServiceChoice() {
		System.out.println("Enter the remove service choice");
		return in.nextLine().trim();
	}
    
	
	public void displayMyServices(ResultSet myservices) throws SQLException {
		while (myservices.next()) {
		    int bookingId = myservices.getInt("bookingDetail_Id");
		    String serviceType = myservices.getString("serviceType");
		    String bookedDate = myservices.getString("booked_date");
		    String serviceName = myservices.getString("service_name");
		    int price = myservices.getInt("price");

		    // Print all details in a single line
		    System.out.println("Booking ID: " + bookingId + ", Service Type: " + serviceType +
		                       ", Booked Date: " + bookedDate + ", Service Name: " + serviceName +
		                       ", Price: " + price);
		}

	}

	public void printPaymentDetails(ResultSet displayResult) throws SQLException {
	    while (displayResult.next()) {
	        System.out.println("Payment ID: " + displayResult.getInt("payment_Id"));
	        System.out.println("Initial Amount: " + displayResult.getInt("initial_amt"));
	        System.out.println("Initial Paid: " + displayResult.getString("initial_paid"));
	        System.out.println("Final Amount: " + displayResult.getInt("final_amt"));
	        System.out.println("Final Paid: " + displayResult.getString("final_paid"));
	        System.out.println("Total: " + displayResult.getInt("total"));
	        System.out.println("Service Status: " + displayResult.getString("serviceStatus"));
	        System.out.println("----------------------------------");
	    }
	}

	
	public String getChoice() {
	    System.out.println("Available choices:");
	    System.out.println("1. Add service");
	    System.out.println("2. Modify Service");
	    System.out.println("3. TakeOut service");
	    System.out.println("4. Edit customer payment");
	    System.out.println("5. Set service status");
	    System.out.println("6. View all bookings");
	    System.out.println("7. Customer services");
	    System.out.println("8. Exit");
	    return in.nextLine().toLowerCase();
	}

	public void getWorkerAvailability() {
		System.out.println("Enter your available or not available");
		service.setWorkerAvailability(in.nextLine());
		System.out.println("Enter service name");
		service.setService_name(in.nextLine());
	}

	public void getServiceDetails() {
		System.out.println("Enter the service name: ");
		service.setService_name(in.nextLine());
		System.out.println("Enter the price: ");
		service.setPrice(in.nextInt());
		System.out.println("Enter the hours: ");
		service.setHours(in.nextInt());
		in.nextLine();
		System.out.println("Enter the Warranty: ");
		service.setWarranty(in.nextLine());
		System.out.println("Enter the workerAvailability: ");
		service.setWorkerAvailability(in.nextLine());
	}

	public String getEditChoice() {
		System.out.println("Enter 1.fill Initial Paid\n2.fill Final Paid\n3.set finalAmt");
		return in.nextLine().toLowerCase();
	}

	public String getNumberPlateadmin() {
		// TODO Auto-generated method stub
		System.out.println("Enter numberPlate");
		return in.nextLine();
	}

	public int getAmt() {
		System.out.println("Enter amt");
		return in.nextInt();
	}

	public String getfinalPaid() {
		System.out.println("Enter paid or not");
		return in.nextLine();
	}
	public String getInitialPaid() {
		System.out.println("Enter paid or not");
		return in.nextLine();
	}

	public String getModifyChoice() {
		 System.out.println("Modify choices:");
		    System.out.println("1. Modify Hours");
		    System.out.println("2. Modify Warranty");
		    System.out.println("3. Modify Price");
		    System.out.println("4. Modify workerAvailability");
		    System.out.println("5. Exit");
		    return in.nextLine().toLowerCase();
	}

	public void getHours() {
		// TODO Auto-generated method stub
		System.out.println("Enter Hours to modify");
		service.setHours(in.nextInt());
	}

	public void getWarranty() {
		// TODO Auto-generated method stub
		System.out.println("Enter Warranty to modify");
		service.setWarranty(in.nextLine());

	}

	public void getPrice() {
		// TODO Auto-generated method stub
		System.out.println("Enter Price to modify");
		service.setPrice(in.nextInt());
	}

	public void modifyServiceName() {
		System.out.println("Enter Service Name to modify");
		service.setService_name(in.nextLine());
	}

	public void getServiceStatus() {
		System.out.println("Enter Service Status to update");
		carDetail.setServiceStatus(in.nextLine());
	}

	public void displayBookings(ResultSet displayResult) throws SQLException {
	    while (displayResult.next()) {
	        System.out.println("Booking Detail ID: " + displayResult.getInt("bookingDetail_Id"));
	        System.out.println("Number Plate: " + displayResult.getString("numberPlate"));
	        System.out.println("Service Type: " + displayResult.getString("serviceType"));
	        System.out.println("Booked Date: " + displayResult.getDate("booked_date"));
	        System.out.println("Deliver Status: " + displayResult.getString("deliverStatus"));
	        System.out.println("Customer ID: " + displayResult.getInt("customer_Id"));
	        System.out.println("----------------------------------");
	    }
	}

	public void printBookingDetailsOfId(ResultSet displayResult,int bookingDetail_Id) throws SQLException {
	    boolean found = false;
	    while (displayResult.next()) {
	        if (displayResult.getInt("bookingDetail_Id") == bookingDetail_Id) {
	            found = true;
	            System.out.println("Booking Detail ID: " + displayResult.getInt("bookingDetail_Id"));
	            System.out.println("Number Plate: " + displayResult.getString("numberPlate"));
	            System.out.println("Service Type: " + displayResult.getString("serviceType"));
	            System.out.println("Booked Date: " + displayResult.getDate("booked_date"));
	            System.out.println("Deliver Status: " + displayResult.getString("deliverStatus"));
	            System.out.println("Customer ID: " + displayResult.getInt("customer_Id"));
	            System.out.println("----------------------------------");
	        }
	    }
	    if (!found) {
	        System.out.println("No booking detail found with ID: " + bookingDetail_Id);
	    }
	}


}
