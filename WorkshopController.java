package car_workshop;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;

import carservice.ServiceModel;
import carservice.ServiceView;

public class WorkshopController {
	WorkshopDAO workshopDao;
	WorkshopView workshopView;
	private CarDetailModel carDetail;
	private BookingDetailModel bookingDetail;
	private BookedServiceModel bookedService;
	private PaymentModel payment;
	private car_workshop.ServiceModel service;
	private ResultSet displayResult;
	private String numPlate;
	private int booking_Id;
	public WorkshopController(WorkshopDAO workshopDao,WorkshopView workshopView, CarDetailModel carDetail, BookingDetailModel bookingDetail, BookedServiceModel bookedService, PaymentModel payment, car_workshop.ServiceModel service) {
		this.workshopDao=workshopDao;
		this.workshopView=workshopView;
		this.carDetail=carDetail;
		this.bookingDetail=bookingDetail;
		this.bookedService=bookedService;
		this.payment=payment;
		this.service=service;
	}
	

	public void adminDashBoard() throws Exception {
	    String displayString="";
	    boolean choiceContinue=true;
	    do {
	    	String choice = workshopView.getChoice();
	        switch (choice) {
	            
	            case "add service":
	                workshopView.getServiceDetails();
	                displayString=workshopDao.addNewService(service.getService_name(),service.getPrice(),service.getHours(),service.getWarranty(),service.getWorkerAvailability());
	                workshopView.display(displayString);
	                break;
	
	            case "modify service":
	               boolean modifyContinue=true;
	               do{
	            	   String modifyChoice=workshopView.getModifyChoice();
	            	  
		               switch (modifyChoice) {
			               case "modify hours":
			            	   workshopView.modifyServiceName();
			                   workshopView.getHours();
			                   displayString=workshopDao.updateHours(service.getHours(),workshopDao.getServiceId(service.getService_name()));
			                   workshopView.display(displayString);
			                   break;
			               case "modify warranty":
			            	   workshopView.modifyServiceName();
			            	   workshopView.getWarranty();
			            	   displayString=workshopDao.updateWarranty(service.getWarranty(),workshopDao.getServiceId(service.getService_name()));
			            	   workshopView.display(displayString);
			                   break;
			               case "modify price":
			            	   workshopView.modifyServiceName();
			            	   workshopView.getPrice();
			            	   displayString=workshopDao.updatePrice(service.getPrice(),workshopDao.getServiceId(service.getService_name()));
			            	   workshopView.display(displayString);
			                   break;
			               case "modify workeravailability":
			            	   workshopView.modifyServiceName();
			            	   workshopView.getWorkerAvailability();
			            	   displayString=workshopDao.updateWorkerAvailability(service.getWorkerAvailability(),workshopDao.getServiceId(service.getService_name()));
			            	   workshopView.display(displayString);
			                   break;
			               case "exit":
			            	   modifyContinue= false;
			                   break;
			               default:
			                   workshopView.invalid();
		               }    
	               }while(modifyContinue);
	                break;
	
	            case "takeout service":
	            	workshopView.modifyServiceName();
	            	displayString=workshopDao.takeOutService(workshopDao.getServiceId(service.getService_name()));
	            	workshopView.display(displayString);
	                break;
	
	            case "edit customer payment":
	            	 boolean editContinue= true;
	            	 do {
	            		 String editChoice = workshopView.getEditChoice();
		                 switch (editChoice) {
		                 case "set finalamt":
		                	    numPlate = workshopView.getNumberPlateadmin();
		                	    booking_Id = workshopDao.getBookingDetail_Id(numPlate);
		                	    workshopDao.setFinalAmt(workshopView.getAmt(), booking_Id);
		                	    
		                	    // Fetch initial and final amounts
		                	    ResultSet initialAndFinalAmtResultSet = workshopDao.getInitialAndFinalAmt(booking_Id);
		                	    if (initialAndFinalAmtResultSet.next()) {
		                	        int initialAmt = initialAndFinalAmtResultSet.getInt("initial_amt");
		                	        int finalAmt = initialAndFinalAmtResultSet.getInt("final_amt");
		                	        
		                	        // Set total amount
		                	        workshopDao.setTotal(initialAmt, finalAmt, booking_Id);
		                	    } else {
		                	        // Handle case where no initial and final amounts are found
		                	        workshopView.display("No initial or final amounts found for the booking.");
		                	    }
		                	    break;

		                     case "fill initial paid":
		                     	 numPlate=workshopView.getNumberPlateadmin();
		                 		 booking_Id = workshopDao.getBookingDetail_Id(numPlate);
		                 		 workshopDao.fillInitialPaid(workshopView.getfinalPaid(),booking_Id);
		                 		
		                         break;
		
		                     case "fill final paid":
		                     	 numPlate=workshopView.getNumberPlateadmin();
		                 		 booking_Id = workshopDao.getBookingDetail_Id(numPlate);
		                 		 workshopDao.fillFinalPaid(workshopView.getfinalPaid(),booking_Id);
		                 		 displayString=workshopDao.setDelivered( numPlate );
		                 		 workshopView.display(displayString);
		                         break;
		                         
		                     case "exit":
				            	   editContinue= false;
				                   break;
		                     default:
		                         workshopView.invalid();
		                         break;
		                 }
	            	 }while(editContinue);
	                break;
	
	            case "set service status":
	            	 numPlate=workshopView.getNumberPlateadmin();
	            	 displayString=workshopDao.updateServiceStatus(carDetail.getServiceStatus(),numPlate);
	            	 workshopView.display(displayString);
	                break;
	
	            case "view all bookings":
	                ResultSet bookingDetailsResultSet = workshopDao.getAllBookingDetails();
	                if (bookingDetailsResultSet == null) {
	                    workshopView.display("No bookings available.");
	                } else {
	                    workshopView.displayBookings(bookingDetailsResultSet);
	                }
	                break;

	
	            case "customer services":
	            	 numPlate=workshopView.getNumberPlateadmin();
	            	 workshopView.printBookingDetailsOfId(workshopDao.getBookingDetailsById(workshopDao.getBookingDetail_Id(numPlate)),workshopDao.getBookingDetail_Id(numPlate));
	            	 
	                break;
	            case "exit":
	            	choiceContinue=false;
	            	break;
	            default:
	                workshopView.invalid();
	                break;
	        }
	    }while(choiceContinue);
    }
	    
	    
	
	public boolean customerDashBoard(int userId) throws Exception {
		String customerChoice=workshopView.customerDashBoard();
		boolean customerDashBoardContinue=true;
		int booking_Id;
		String numPlate="";
			switch(customerChoice) {
				
				case "service":
					String serviceMenu=workshopView.serviceMenu();
					boolean serviceContinue = true;
					//do {
						if(serviceMenu.equalsIgnoreCase("register car")) {
						
							workshopView.getNumberPlate();
							numPlate=carDetail.getNumPlate();
							String displayString;
							if(workshopDao.searchNumPlate(numPlate)) {
								workshopView.display("NumberPlate already registered");
								if(workshopDao.getDeliverStatus(numPlate).equalsIgnoreCase("delivered")||workshopDao.getDeliverStatus(numPlate).equalsIgnoreCase("registered")) {
									int kms=workshopDao.getKms(numPlate);
									workshopView.enterKms(kms);
									displayString=workshopDao.updateKms(numPlate,carDetail.getLastKms(),carDetail.getKms());
									workshopView.display(displayString);
								}
								else
									workshopView.display("Add car failed!\nCar not yet delivered");
							}
							else {
								workshopView.addVehicleDetail(userId);
								if(carDetail.getServiceCount()==0) {
									carDetail.setLast_FreeServiceDate(carDetail.getVehicle_ownedDate());
								}
								int pendingBookingDetailId = workshopDao.getPendingBookingDetailId(numPlate);
				                if (pendingBookingDetailId != -1) {
				                    // If there's a pending bookingDetail, set the current bookingDetail_Id to the existing one
				                    bookingDetail.setBookingDetail_Id(pendingBookingDetailId);
				                }
				                else {
				                    // Otherwise, create a new bookingDetail
				                    displayString = workshopDao.addVehicle(carDetail.getNumPlate(), carDetail.getVehicleName(), carDetail.getVehicle_ownedDate(), carDetail.getLast_FreeServiceDate(), carDetail.getKms(), carDetail.getServiceCount(), carDetail.getUserId());
				                    // Update the current bookingDetail_Id with the newly created one
				                    bookingDetail.setBookingDetail_Id(workshopDao.getBookingDetail_Id(numPlate));
				                    workshopView.display(displayString);
				                }
							}
							
							
							LocalDate firstDate = workshopDao.getLastFreeServiceDate(numPlate).toLocalDate();
							LocalDate currentDate = LocalDate.now();
							Period difference = Period.between(firstDate,currentDate); 
							boolean available=false;
							if(difference.getYears()>=1||difference.getMonths()>=4)
								available=true;
							if((carDetail.getKms()-carDetail.getLastKms()==1000) || (available==true)&& carDetail.getServiceCount()<=3) {
								workshopView.display("Free service available");
							}
							else
								available=false;
							
							boolean addCarContinue = true;
							do {
								workshopView.addCarMenu();
								String serviceType=bookingDetail.getServiceType();
								if(serviceType.equalsIgnoreCase("free service")) {
									if(available==false) {
										workshopView.display("Free service not available");
									}
									else {
										workshopDao.setLast_FreeServiceDate(numPlate);
									}
								}
								
	//							if(serviceType.equalsIgnoreCase("sos")) {
	//								workshopView.dropTypeMenu();
	//							}
								
								
								if(serviceType.equalsIgnoreCase("custom service")||(serviceType.equalsIgnoreCase("free service")&&available==true)) {
									 int pendingBookingDetailId = workshopDao.getPendingBookingDetailId(numPlate);
									    if (pendingBookingDetailId != -1) { 
									        bookingDetail.setBookingDetail_Id(pendingBookingDetailId);
									    } else {
									        // Otherwise, create a new bookingDetail
									        workshopView.getAddress();
									        workshopDao.bookingDetail(carDetail.getNumPlate(), bookingDetail.getServiceType(), bookingDetail.getAddress());
									        bookingDetail.setBookingDetail_Id(workshopDao.getBookingDetail_Id(numPlate));
									    }	boolean serviceTypeMenuContinue=true;
									do {
										String serviceTypeMenu = workshopView.getServiceTypeMenu();
										String serviceName=null;
										switch(serviceTypeMenu) {
										
											case "display services":
												 displayResult = workshopDao.displayServices();
									             workshopView.displayServices(displayResult);
												break;
												
											case "add service":
												workshopDao.updateToPending(numPlate);
												displayResult=workshopDao.displayServices();
												serviceName=workshopView.addServiceChoice(displayResult);
												int serviceId=workshopDao.getServiceId(serviceName);
												workshopDao.bookedService(bookingDetail.getBookingDetail_Id(),serviceId);
												break;
												
											case "remove service":
												String removeServiceName=workshopView.removeServiceChoice();
												 booking_Id = workshopDao.getBookingDetail_Id(numPlate);
												workshopView.display(workshopDao.removeService(numPlate,removeServiceName,booking_Id));
											    if (workshopDao.hasBookedServices(numPlate,booking_Id)<0) {
											        workshopDao.updateToRegistered(numPlate,booking_Id);
											    }
												break;
												
											case "display my services":
												booking_Id = workshopDao.getBookingDetail_Id(numPlate);
												ResultSet myservices=workshopDao.viewAllMyservices(booking_Id);
												workshopView.displayMyServices(myservices);
												break;
											case "payment":
											    booking_Id = workshopDao.getBookingDetail_Id(numPlate);
											    if (workshopDao.hasBookedServices(numPlate, booking_Id) > 0) {
											        int existingPaymentId = workshopDao.getPaymentIdByBookingId(booking_Id);
											        if (existingPaymentId != -1) {
											            int initialAmt = workshopDao.calculatePayment(booking_Id);
											            workshopDao.updatePayment(initialAmt,booking_Id);
											        } else {
											            int initialAmt = workshopDao.calculatePayment(booking_Id);
											            workshopDao.insertPayment(initialAmt, booking_Id);
											        }
											        displayResult = workshopDao.getPaymentDetails(booking_Id);
											        workshopView.printPaymentDetails(displayResult);
											        serviceTypeMenuContinue = false;
											    } else {
											        workshopView.display("No services added for this booking detail.");
											    }
											    break;


											case "exit":
												serviceTypeMenuContinue=false;
												break;
											default:
												workshopView.invalid();
										}
									}while(serviceTypeMenuContinue);
								}
								else if(serviceType.equalsIgnoreCase("exit"))
									addCarContinue=false;
								else
									workshopView.invalid();
							}while(addCarContinue);
						}
						
						
						else if(serviceMenu.equalsIgnoreCase("remove car")) {
							workshopView.display(workshopDao.removeCar(carDetail.getNumPlate()));
						}
						
						else if(serviceMenu.equalsIgnoreCase("exit")) {
							serviceContinue= false;
						}
						else
							workshopView.invalid();
				//	}while(serviceContinue);
					break;
					
				case "pick up":
					booking_Id = workshopDao.getBookingDetail_Id(numPlate);
			        displayResult = workshopDao.getPaymentDetails(booking_Id);
			        workshopView.printPaymentDetails(displayResult);
					   
					break;
//				case "view booked services by date":
//				    String date = workshopView.getDate(); // Ask for the date
//				    ResultSet bookedServicesByDate = workshopDao.getBookedServicesByDate(date);
//				    workshopView.displayBookedServices(bookedServicesByDate);
//				    break;
//
//				case "view all services":
//				    ResultSet allServices = workshopDao.getAllServices();
//				    workshopView.displayAllServices(allServices);
//				    break;
//
//				case "view booked details":
//				    String numberPlate = workshopView.getNumberPlate(); // Ask for the number plate
//				    ResultSet bookedDetails = workshopDao.getBookedDetails(numberPlate);
//				    workshopView.displayBookedDetails(bookedDetails);
//				    break;

				case "exit":
					customerDashBoardContinue=false;
					return customerDashBoardContinue;
					
				default:
					workshopView.invalid();
	
		}
		return customerDashBoardContinue;
   }
}
