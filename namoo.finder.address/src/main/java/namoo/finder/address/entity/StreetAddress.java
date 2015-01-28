package namoo.finder.address.entity;

public class StreetAddress {

	private String street;
	private String details;
	
	public StreetAddress(String street, String details){
		this.street = street;
		this.details = details;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}
