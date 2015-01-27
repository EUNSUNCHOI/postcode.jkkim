package namoo.finder.address.entity;

public class Address {

	private String si;
	private String gu;
	private String street;
	private String details;
	private int postcode;
	
	//-------------------------------------------------------------
	
	public Address(){
		
	}
	
	public Address(String si, String gu, String street, String details, int postcode){
		
		this.si = si;
		this.gu = gu;
		this.street = street;
		this.details = details;
		this.postcode = postcode;
	}

	public String getSi() {
		return si;
	}

	public void setSi(String si) {
		this.si = si;
	}

	public String getGu() {
		return gu;
	}

	public void setGu(String gu) {
		this.gu = gu;
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

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	//--------------------------------------------------------------
	
	
}
