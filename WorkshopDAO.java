package car_workshop;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class WorkshopDAO implements WorkShopDAOOverview{
	private ResultSet checkResult;
	private Statement st;
	private String selectQuery;
	private PreparedStatement preparedStatement;
	private Connection conn;
	private String insertQuery;
	private String updateQuery;
	private String deleteQuery;
	private int rowsAffected;
	public WorkshopDAO(Connection conn, Statement st) {
		this.conn=conn;
		this.st=st;
	}
	public boolean searchNumPlate(String numPlate) throws SQLException {
		selectQuery="Select *from carDetail where numberPlate="+"'"+numPlate+"'";
		checkResult=st.executeQuery(selectQuery);
		if(checkResult.next())
			return true;
		return false;
	}
	
	public String addVehicle(String numberPlate, String vehicleName,String vehicleOwnedString, String lastFreeServiceString, int kms,int serviceCount,int userId) throws SQLException {
		 
		 Date vehicleOwnedDate = Date.valueOf(vehicleOwnedString);
		 Date lastFreeServiceDate = Date.valueOf(lastFreeServiceString);
		
		 insertQuery = "INSERT INTO carDetail (numberPlate, vehicleName,userId, vehicle_ownedDate, last_FreeServiceDate, kms,serviceCount) VALUES(?,?,?,?,?,?,?)";
		 preparedStatement = conn.prepareStatement(insertQuery);
       	
            preparedStatement.setString(1,numberPlate);
            preparedStatement.setString(2,vehicleName);
            preparedStatement.setInt(3,userId);
            preparedStatement.setDate(4,vehicleOwnedDate);
            preparedStatement.setDate(5,lastFreeServiceDate);
            preparedStatement.setInt(6,kms);
            preparedStatement.setInt(7,serviceCount);
           int rowUpdate=preparedStatement.executeUpdate();
           if(rowUpdate>0)
		 		 return "Car added";
           else
           	return "Car not added";
	

	}
	
	public void insertRegistered(String numberPlate) throws SQLException {
	    insertQuery = "INSERT INTO carDetail (numberPlate, deliverStatus) VALUES (?, 'registered')";
	    preparedStatement = conn.prepareStatement(insertQuery);
	    preparedStatement.setString(1, numberPlate);
	    preparedStatement.executeUpdate();
	}
	
	public String getDeliverStatus(String numPlate) throws SQLException {
		insertQuery= "SELECT deliverStatus FROM carDetail WHERE numberPlate = ?";
        preparedStatement = conn.prepareStatement(insertQuery);
        preparedStatement.setString(1,numPlate);
        checkResult= preparedStatement.executeQuery();
        if(checkResult.next())
         return checkResult.getString("deliverStatus");
        return "not exist";
	}
	
	public String updateKms(String numPlate,int Lastkms,int kms) throws SQLException {
		
		updateQuery = "UPDATE carDetail SET kms = ? WHERE numberPlate = ?";
		preparedStatement = conn.prepareStatement(updateQuery);
		preparedStatement.setInt(1, kms);
	    preparedStatement.setString(2, numPlate);
	    int rowsAffected = preparedStatement.executeUpdate();
       if (rowsAffected > 0) 
           return "Kilometers updated";
       return "Kilometers not updated";   
	}
	
	
	public int getKms(String numPlate) throws SQLException {
		 int kms=0;
		 selectQuery = "SELECT kms FROM carDetail WHERE numberPlate = ?";
	     preparedStatement = conn.prepareStatement(selectQuery);
	     preparedStatement.setString(1, numPlate);
	     checkResult = preparedStatement.executeQuery();
        if (checkResult.next()) {
           kms= checkResult.getInt("kms");
        }
        return kms;
	}
	
	public int getBookingDetail_Id(String numPlate) throws SQLException {
		int bookingDetailId = -1; 
		selectQuery = "SELECT bd.bookingDetail_Id " +
                "FROM bookingDetail bd " +
                "JOIN carDetail cd ON bd.numberPlate = cd.numberPlate " +
                "WHERE bd.numberPlate = ? " +
                "AND cd.deliverStatus <> 'delivered' " + 
                "ORDER BY bd.booked_date DESC " +
                "LIMIT 1";
  
	    
	     preparedStatement = conn.prepareStatement(selectQuery);
	     preparedStatement.setString(1, numPlate);
	     checkResult = preparedStatement.executeQuery();
	     if (checkResult.next()) {
            bookingDetailId = checkResult.getInt("bookingDetail_Id");
	     }
	    return bookingDetailId;
	}

	public ResultSet displayServices() throws SQLException {
		selectQuery="Select *from service where workerAvailability='available'";
		preparedStatement=conn.prepareStatement(selectQuery);
		checkResult=preparedStatement.executeQuery();
			return checkResult;
	}
	
	public void updateToPending(String numPlate) throws SQLException {
		String updateQuery = "UPDATE carDetail SET deliverStatus = 'pending' WHERE numberPlate = ?";
		preparedStatement = conn.prepareStatement(updateQuery);
		preparedStatement.setString(1, numPlate);
		preparedStatement.executeUpdate();
	}
	
	public void addService(String serviceName, int price, int hours, String warranty, String workerAvailability) throws SQLException {
         insertQuery = "INSERT INTO service (service_name, price, hours, warranty, workerAvailability) VALUES (?, ?, ?, ?, ?)";
        
         	preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, serviceName);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, hours);
            preparedStatement.setString(4, warranty);
            preparedStatement.setString(5, workerAvailability);
            
            preparedStatement.executeUpdate();
       }

	

	public void bookingDetail(String numPlate, String serviceType, String address) throws SQLException {
		insertQuery = "INSERT INTO bookingDetail (numberPlate, serviceType, booked_date, address) " +
                "VALUES (?, ?, NOW(), ?)";

		preparedStatement = conn.prepareStatement(insertQuery);
		preparedStatement.setString(1, numPlate);
		preparedStatement.setString(2, serviceType);
		preparedStatement.setString(3,address);
		preparedStatement.executeUpdate();

	}
	
	public int getServiceId(String serviceName) throws SQLException {
	    selectQuery = "SELECT serviceId FROM service WHERE service_name = ?";
	    preparedStatement = conn.prepareStatement(selectQuery);
	        preparedStatement.setString(1, serviceName);
	         checkResult = preparedStatement.executeQuery(); 
	            if (checkResult.next()) {
	                return checkResult.getInt("serviceId");
	            } 
	            else {
	                return -1;
	            }
    }
	
	public void bookedService(int bookingDetail_Id, int serviceId) throws SQLException {
		insertQuery="Insert into bookedService(bookingDetail_Id,serviceId)values(?,?)";
		preparedStatement=conn.prepareStatement(insertQuery);
		preparedStatement.setInt(1,bookingDetail_Id);
		preparedStatement.setInt(2,serviceId);
		preparedStatement.executeUpdate();
	}
	
	public Date getLastFreeServiceDate(String numPlate) throws SQLException {
	    String query = "SELECT last_FreeServiceDate FROM carDetail WHERE numberPlate = ?";
	    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
	        preparedStatement.setString(1, numPlate);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getDate("last_FreeServiceDate");
	            }
	        }
	    }
	    return null; // Handle case where no record found for the given numberPlate
	}

	public void setLast_FreeServiceDate(String numPlate) throws SQLException {
		
		    String updateQuery = "UPDATE carDetail SET last_FreeServiceDate = NOW() WHERE numberPlate = ?";
		    preparedStatement = conn.prepareStatement(updateQuery);
	        preparedStatement.setString(1, numPlate);
	        preparedStatement.executeUpdate();
	    
		}

	
	
	public String removeService(String numberPlate, String serviceName, int bookingId) throws SQLException {
	    deleteQuery = "DELETE FROM bookedService WHERE bookingDetail_Id = ? AND serviceId IN (SELECT serviceId FROM service WHERE service_name = ?)";
	    preparedStatement = conn.prepareStatement(deleteQuery);
	    preparedStatement.setInt(1, bookingId);
	    preparedStatement.setString(2, serviceName);
	    int rowsAffected = preparedStatement.executeUpdate();
	    if (rowsAffected > 0) {
	       return "Service removed successfully.";
	    } 
	    return "No matching service found for removal.";
	    
	}
	 
	public int hasBookedServices(String numPlate, int bookingId) throws SQLException {
	    selectQuery = "SELECT COUNT(*) AS serviceCount " +
	                  "FROM bookedService " +
	                  "WHERE bookingDetail_Id = ?";
	    preparedStatement = conn.prepareStatement(selectQuery);
	    preparedStatement.setInt(1, bookingId);
	    checkResult = preparedStatement.executeQuery();
	    if (checkResult.next()) {
	        return checkResult.getInt("serviceCount");
	    } else {
	        return 0; 
	    }
	}

	
	public void updateToRegistered(String numPlate,int bookingId) throws SQLException {
		// TODO Auto-generated method stub
		updateQuery="Update bookingDetail set deliverStatus='registered' where numberPlate=? AND ";
		preparedStatement = conn.prepareStatement(updateQuery);
        preparedStatement.setString(1, numPlate);
        preparedStatement.setInt(2, bookingId);
        preparedStatement.executeUpdate();
	}
	
	public String removeCar(String numPlate) throws SQLException {
		deleteQuery="DELETE FROM bookingDetail WHERE bookingDetail_Id IN (SELECT bd.bookingDetail_Id FROM "
				+ "(SELECT bookingDetail_Id FROM bookingDetail  WHERE numberPlate = ? ORDER BY booked_date DESC LIMIT 1) AS bd)"
				+ "AND (deliverStatus = 'registered' OR deliverStatus = 'pending')";
		preparedStatement = conn.prepareStatement(deleteQuery);
	    preparedStatement.setString(1, numPlate);
	    int rowsAffected = preparedStatement.executeUpdate();
	    if (rowsAffected > 0) {
	       return "Service removed successfully.";
	    } 
	    return "No matching service found for removal.";
	}
	
	public int getPendingBookingDetailId(String numPlate) throws SQLException {
	    int bookingDetailId = -1;
	    selectQuery = "SELECT bd.bookingDetail_Id " +
	                  "FROM bookingDetail bd " +
	                  "JOIN carDetail cd ON bd.numberPlate = cd.numberPlate " +
	                  "WHERE cd.numberPlate = ? AND cd.deliverStatus = 'pending' " +
	                  "ORDER BY bd.booked_date DESC LIMIT 1";
	    preparedStatement = conn.prepareStatement(selectQuery);
	    preparedStatement.setString(1, numPlate);
	    checkResult = preparedStatement.executeQuery();
	    if (checkResult.next()) {
	        bookingDetailId = checkResult.getInt("bookingDetail_Id");
	    }
	    return bookingDetailId;
	}

	public ResultSet viewAllMyservices(int bookingId) throws SQLException {
	    selectQuery = "SELECT b.bookingDetail_Id, b.serviceType, b.booked_date, s.service_name, s.price " +
                "FROM bookingDetail b " +
                "INNER JOIN bookedService bs ON b.bookingDetail_Id = bs.bookingDetail_Id " +
                "INNER JOIN service s ON bs.serviceId = s.serviceId " +
                "INNER JOIN carDetail cd ON b.numberPlate = cd.numberPlate " +
                "WHERE b.bookingDetail_Id = ? AND cd.deliverStatus = 'pending'";

	    preparedStatement = conn.prepareStatement(selectQuery);
	    preparedStatement.setInt(1, bookingId);
	    checkResult = preparedStatement.executeQuery();
	    return checkResult;
	}
	public int calculatePayment(int booking_Id) throws SQLException {
		selectQuery= "SELECT SUM(s.price) AS total_price FROM service s " +
                "INNER JOIN bookedService bs ON s.serviceId = bs.serviceId " +
                "WHERE bs.bookingDetail_Id = ?";

		preparedStatement = conn.prepareStatement(selectQuery);
		preparedStatement.setInt(1, booking_Id);
		checkResult = preparedStatement.executeQuery();
         if (checkResult.next()) {
             return checkResult.getInt("total_price");
         }
     
         return 0;
	}
	public void insertPayment(int initialAmt,int booking_Id) throws SQLException {
		insertQuery = "INSERT INTO payment (initial_amt,bookingDetail_Id) VALUES (?, ?)";
    	preparedStatement = conn.prepareStatement(insertQuery);
        preparedStatement.setInt(1, initialAmt);
        preparedStatement.setInt(2, booking_Id);
        preparedStatement.executeUpdate();
	        
	}
	public ResultSet getPaymentDetails(int booking_Id) throws SQLException {
		 selectQuery = "SELECT p.payment_Id, p.initial_amt, p.initial_paid, p.final_amt, p.final_paid, p.total, cd.serviceStatus " +
                 "FROM payment p " +
                 "INNER JOIN bookingDetail bd ON p.bookingDetail_Id = bd.bookingDetail_Id " +
                 "INNER JOIN bookedService bs ON bd.bookingDetail_Id = bs.bookingDetail_Id " +
                 "INNER JOIN service s ON bs.serviceId = s.serviceId " +
                 "INNER JOIN carDetail cd ON bd.numberPlate = cd.numberPlate " +
                 "WHERE p.bookingDetail_Id = ?";
        preparedStatement = conn.prepareStatement(selectQuery);
        preparedStatement.setInt(1, booking_Id);
        return preparedStatement.executeQuery();
	}

	public int getPaymentIdByBookingId(int booking_Id) throws SQLException {
	    int paymentId = -1;
	    String query = "SELECT payment_Id FROM payment WHERE bookingDetail_Id = ?";
	    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
	        preparedStatement.setInt(1, booking_Id);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                paymentId = resultSet.getInt("payment_Id");
	            }
	        }
	    }
	    return paymentId;
	}
	
	public void updatePayment(int initialAmt, int booking_Id) throws SQLException {
	    	updateQuery = "UPDATE payment SET initial_amt = ? WHERE bookingDetail_Id = ?";
	    	preparedStatement = conn.prepareStatement(updateQuery);
	        preparedStatement.setInt(1, initialAmt);
	        preparedStatement.setInt(2, booking_Id);
	        preparedStatement.executeUpdate();
	        
	}
	
	public void setFinalAmt(int amt,int bookingDetail_Id) throws SQLException {
		 updateQuery = "UPDATE payment SET final_amt = ? WHERE bookingDetail_Id = ?";
	        preparedStatement = conn.prepareStatement(updateQuery);
	        preparedStatement.setInt(1, amt);
	        preparedStatement.setInt(2, bookingDetail_Id);
	        preparedStatement.executeUpdate();
	       
	}
	
	public void fillInitialPaid(String string,int bookingDetail_Id) throws SQLException {
	updateQuery = "UPDATE payment SET final_paid = ? WHERE bookingDetail_Id = ?";
    preparedStatement = conn.prepareStatement(updateQuery);
    preparedStatement.setString(1, string);
    preparedStatement.setInt(2, bookingDetail_Id);
    preparedStatement.executeUpdate();
   
	}
	
	
	public void fillFinalPaid(String getfinalPaid, int booking_Id) throws SQLException {
		// TODO Auto-generated method stub
		    updateQuery = "UPDATE payment SET final_paid = ? WHERE bookingDetail_Id = ?";
	        preparedStatement = conn.prepareStatement(updateQuery);
	        preparedStatement.setString(1, getfinalPaid);
	        preparedStatement.setInt(2, booking_Id);
	        preparedStatement.executeUpdate();
	       
	}
	public String setDelivered(String numPlate) throws SQLException {
		// TODO Auto-generated method stub
		 	updateQuery = "UPDATE carDetail SET deliverStatus = 'delivered' WHERE numberPlate = ?";
		    preparedStatement = conn.prepareStatement(updateQuery);
		    preparedStatement.setString(1, numPlate);
		    int rowsAffected = preparedStatement.executeUpdate();
		    if (rowsAffected > 0) {
	            return"set delivered.";
	        } 
	            return "set not delivered";
		    
	}
	
	private boolean serviceExists(int serviceId) throws SQLException {
	    String query = "SELECT COUNT(*) AS count FROM service WHERE serviceId = ?";
	    preparedStatement = conn.prepareStatement(query);
	    preparedStatement.setInt(1, serviceId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    resultSet.next();
	    int count = resultSet.getInt("count");
	    return count > 0;
	}
	
	public String addNewService(String service_name, int price, int hours, String warranty, String workerAvailability) throws SQLException {
        	insertQuery = "INSERT INTO service (service_name, price, hours, warranty, workerAvailability) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = conn.prepareStatement(insertQuery);
	        preparedStatement.setString(1, service_name);
	        preparedStatement.setInt(2, price);
	        preparedStatement.setInt(3, hours);
	        preparedStatement.setString(4, warranty);
	        preparedStatement.setString(5,workerAvailability);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            return"Service added successfully.";
	        } 
	            return "Failed to add service.";
	        
	    
		
	}
	public String updateHours(int hours,int serviceId) throws SQLException {
		 if (!serviceExists(serviceId)) {
		        return "Invalid input: No service found with the provided service name";
		    }
	    updateQuery = "UPDATE service SET hours = ? WHERE serviceId = ?";
	    preparedStatement = conn.prepareStatement(updateQuery);
	    preparedStatement.setInt(1, hours);
	    preparedStatement.setInt(2, serviceId);
	    rowsAffected= preparedStatement.executeUpdate();
	    if (rowsAffected == 0) {
	        return "Invalid input";
	    } else {
	        return "Hours updated successfully.";
	    }
	}

	public String updateWarranty(String warranty,int serviceId) throws SQLException {
		
		 if (!serviceExists(serviceId)) {
		        return "Invalid input: No service found with the provided service name";
		    }
		 
	    updateQuery = "UPDATE service SET warranty = ? WHERE serviceId = ?";
	    preparedStatement = conn.prepareStatement(updateQuery);
	    preparedStatement.setString(1, warranty);
	    preparedStatement.setInt(2, serviceId);
	    rowsAffected=preparedStatement.executeUpdate();
	    if (rowsAffected == 0) {
	        return "Invalid input: No service found with the provided serviceId.";
	    } else {
	        return "Warranty updated successfully.";
	    }
	}

	public String updatePrice(int price,int serviceId) throws SQLException {
		
		 if (!serviceExists(serviceId)) {
		        return "Invalid input: No service found with the provided service name";
		    }
		 
	    updateQuery = "UPDATE service SET price = ? WHERE serviceId = ?";
	    preparedStatement = conn.prepareStatement(updateQuery);
	    preparedStatement.setInt(1, price);
	    preparedStatement.setInt(2, serviceId);
	    rowsAffected=preparedStatement.executeUpdate();
	    if (rowsAffected == 0) {
	        return "Invalid input";
	    } else {
	        return "Price updated successfully.";
	    }
	}

	
	public String updateWorkerAvailability(String availability,int serviceId) throws SQLException {
		 if (!serviceExists(serviceId)) {
		        return "Invalid input: No service found with the provided service name";
		    }
		updateQuery = "UPDATE service SET workerAvailability = ? where serviceId=?";
    	preparedStatement = conn.prepareStatement(updateQuery);
        preparedStatement.setString(1, availability);
        preparedStatement.setInt(2,serviceId);
        rowsAffected=preparedStatement.executeUpdate();
        if (rowsAffected == 0) {
	        return "Invalid input";
	    } else {
	        return "WorkerAvailability updated successfully.";
	    }
	}
	
	public String takeOutService(int serviceId) throws SQLException {
		 if (!serviceExists(serviceId)) {
		        return "Invalid input: No service found with the provided service name";
		    }
		deleteQuery = "DELETE FROM service WHERE serviceId = ?";
	    preparedStatement = conn.prepareStatement(deleteQuery);
	    preparedStatement.setInt(1, serviceId);
	    int rowsAffected = preparedStatement.executeUpdate();
	    if (rowsAffected == 0) {
	        return "invalid input";
	    } else {
	        return "Service removed successfully.";
	    }
	}
	
	public String updateServiceStatus(String serviceStatus, String numPlate) throws SQLException {
		updateQuery = "UPDATE carDetail SET serviceStatus = ? WHERE numberPlate = ?";
	    preparedStatement = conn.prepareStatement(updateQuery);
	    preparedStatement.setString(1, serviceStatus);
	    preparedStatement.setString(2, numPlate);
	    int rowsAffected = preparedStatement.executeUpdate();
	    if (rowsAffected == 0) {
	        return "invalid input";
	    } else {
	        return "Service status updated successfully.";
	    }
	}
	
	public ResultSet getAllBookingDetails() throws SQLException {
	    String query = "SELECT * FROM bookingDetail";
	    preparedStatement = conn.prepareStatement(query);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (!resultSet.next()) {

	        return null;
	    } else {
	        resultSet.beforeFirst();
	        return resultSet;
	    }
	}

	public String customerBookings(int bookingDetail_Id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getBookingDetailsById(int bookingDetail_Id) throws SQLException {
	    String query = "SELECT * FROM bookingDetail WHERE bookingDetail_Id = ?";
	    preparedStatement = conn.prepareStatement(query);
	    preparedStatement.setInt(1, bookingDetail_Id);
	    return preparedStatement.executeQuery();
	}
	
	public ResultSet getInitialAndFinalAmt(int booking_Id) throws SQLException {
	    String query = "SELECT initial_amt, final_amt FROM payment WHERE bookingDetail_Id = ?";
	    preparedStatement = conn.prepareStatement(query);
	    preparedStatement.setInt(1, booking_Id);
	    return preparedStatement.executeQuery();
	}

	public void setTotal(int initialAmt, int finalAmt, int booking_Id) throws SQLException {
	    int total = initialAmt + finalAmt;
	    String updateQuery = "UPDATE payment SET total = ? WHERE bookingDetail_Id = ?";
	    preparedStatement = conn.prepareStatement(updateQuery);
	    preparedStatement.setInt(1, total);
	    preparedStatement.setInt(2, booking_Id);
	    preparedStatement.executeUpdate();
	}


}
    


