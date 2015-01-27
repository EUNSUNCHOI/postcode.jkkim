package namoo.finder.address.entity;

public class Address {

	private String si;
	private String gu;
	
	private DongAddress dongAddress;
	private StreetAddress streetAddress;

	private String postcode;
	
	//-------------------------------------------------------------
	
	public Address(){
		
	}
	
	public Address(String si, String gu, DongAddress dongAddress, 
			StreetAddress streetAddress, String postcode){
		
		this.si = si;
		this.gu = gu;
		this.dongAddress = dongAddress;
		this.streetAddress = streetAddress;
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

	public DongAddress getDongAddress() {
		return dongAddress;
	}

	public void setDongAddress(DongAddress dongAddress) {
		this.dongAddress = dongAddress;
	}

	public StreetAddress getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(StreetAddress streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	//--------------------------------------------------------------
	
	
}
