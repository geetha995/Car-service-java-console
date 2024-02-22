package car_workshop;

public class ServiceModel {

	int serviceId;
	String service_name;
	int price;
	int hours;
	String warranty;
	String workerAvailability;
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	public String getWorkerAvailability() {
		return workerAvailability;
	}
	public void setWorkerAvailability(String workerAvailability) {
		this.workerAvailability = workerAvailability;
	}
}
