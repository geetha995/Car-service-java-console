package car_workshop;

public class CarDetailModel {
	String numPlate;
	String vehicleName;
	int userId;
	String vehicle_ownedDate;
	String last_FreeServiceDate;
	int kms;
	int lastKms;
	int serviceCount;
	String deliverStatus;
	String serviceStatus;
	public String getNumPlate() {
		return numPlate;
	}
	public void setNumPlate(String numPlate) {
		this.numPlate = numPlate;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getVehicle_ownedDate() {
		return vehicle_ownedDate;
	}
	public void setVehicle_ownedDate(String vehicle_ownedDate) {
		this.vehicle_ownedDate = vehicle_ownedDate;
	}
	public String getLast_FreeServiceDate() {
		return last_FreeServiceDate;
	}
	public void setLast_FreeServiceDate(String last_FreeServiceDate) {
		this.last_FreeServiceDate = last_FreeServiceDate;
	}
	public int getKms() {
		return kms;
	}
	public void setKms(int kms) {
		this.kms = kms;
	}
	public int getLastKms() {
		return lastKms;
	}
	public void setLastKms(int lastKms) {
		this.lastKms = lastKms;
	}
	public int getServiceCount() {
		return serviceCount;
	}
	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}
	public String getDeliverStatus() {
		return deliverStatus;
	}
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
