package car_workshop;

public class BookingDetailModel {
	int bookingDetail_Id;
	String numberPlate;
	String serviceType;
	String booked_date;
	String address;
	public int getBookingDetail_Id() {
		return bookingDetail_Id;
	}
	public void setBookingDetail_Id(int bookingDetail_Id) {
		this.bookingDetail_Id = bookingDetail_Id;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getBooked_date() {
		return booked_date;
	}
	public void setBooked_date(String booked_date) {
		this.booked_date = booked_date;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
