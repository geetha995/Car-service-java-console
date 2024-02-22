package car_workshop;

public class PaymentModel {
	int payment_Id;
	int initial_amt;
	String initial_paid;
	int final_amt;
	String final_paid;
	int total;
	int bookedId;
	int bookingDetail_Id;
	public int getPayment_Id() {
		return payment_Id;
	}
	public void setPayment_Id(int payment_Id) {
		this.payment_Id = payment_Id;
	}
	public int getInitial_amt() {
		return initial_amt;
	}
	public void setInitial_amt(int initial_amt) {
		this.initial_amt = initial_amt;
	}
	public String getInitial_paid() {
		return initial_paid;
	}
	public void setInitial_paid(String initial_paid) {
		this.initial_paid = initial_paid;
	}
	public int getFinal_amt() {
		return final_amt;
	}
	public void setFinal_amt(int final_amt) {
		this.final_amt = final_amt;
	}
	public String getFinal_paid() {
		return final_paid;
	}
	public void setFinal_paid(String final_paid) {
		this.final_paid = final_paid;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getBookedId() {
		return bookedId;
	}
	public void setBookedId(int bookedId) {
		this.bookedId = bookedId;
	}
	public int getBookingDetail_Id() {
		return bookingDetail_Id;
	}
	public void setBookingDetail_Id(int bookingDetail_Id) {
		this.bookingDetail_Id = bookingDetail_Id;
	}
}
