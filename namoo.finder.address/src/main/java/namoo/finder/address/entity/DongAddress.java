package namoo.finder.address.entity;

public class DongAddress {

	private String dong;
	private String details;
	
	public DongAddress(String dong, String details){
		this.dong = dong;
		this.details = details;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	//--------------------------------------------------
	
	
}
