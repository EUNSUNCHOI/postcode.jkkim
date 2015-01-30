package namoo.finder.address.persist.file.creator;

import namoo.finder.address.entity.Address;
import namoo.finder.address.entity.DongAddress;

public class CreateCSVorDongAddress {

	private static final String TXT_SEPERATOR = "	";	
	private static final String CSV_SEPERATOR = ",";
	
	public Address createDongAddressFromTXT(String tmpAddress){
		
		String[] data = tmpAddress.split(TXT_SEPERATOR);
		
		String postcode = null;
		
		String[] tmpPostcode = data[0].split("-");
		if(tmpPostcode.length < 2){
			postcode = tmpPostcode[0];
		}
		else{
			postcode = tmpPostcode[0] + tmpPostcode[1];			
		}
		
		DongAddress dongAddress = null;
		if(data.length < 5){
			dongAddress = new DongAddress(data[3], "");
		}
		else{
			dongAddress = new DongAddress(data[3], data[4]);
		}
		Address address = new Address(data[1], data[2], dongAddress, null, postcode); 

		return address;
	}
	
	public Address createDongAddressFromCSV(String tmpAddress){
		
		String[] data = tmpAddress.split(CSV_SEPERATOR);
		
		DongAddress dongAddress;
		
		if(data.length < 5){
			dongAddress = new DongAddress(data[3], "");
		}
		else{
			dongAddress = new DongAddress(data[3], data[4]);
		}
		Address address = new Address(data[1], data[2], dongAddress, null, data[0]); 
		
		return address;
	}
	
	public String createCSVFromDongAddress(Address address){
		//	
		StringBuilder builder = new StringBuilder();
		
		builder.append(address.getPostcode());
		builder.append(CSV_SEPERATOR);
		builder.append(address.getSi());
		builder.append(CSV_SEPERATOR);
		builder.append(address.getGu());
		builder.append(CSV_SEPERATOR);
		builder.append(address.getDongAddress().getDong());
		builder.append(CSV_SEPERATOR);
		builder.append(address.getDongAddress().getDetails());
		builder.append("\n");
				
		return builder.toString();
	}
	
	public String createDongIndexCSVFromAddress(Address address, long pointer){
		//
		StringBuilder builder = new StringBuilder();
		
		builder.append(address.getPostcode());
		builder.append(CSV_SEPERATOR);
		builder.append(address.getDongAddress().getDong());
		builder.append(CSV_SEPERATOR);
		builder.append(pointer);  // 위치
		builder.append("\n");
		
		return builder.toString();
	}
	
/*	public String createTXTFromAddress(Address address){
		
		StringBuilder builder = new StringBuilder();
		builder.append(address.getPostcode());
		builder.append(TXT_SEPERATOR);
		builder.append(address.getSi());
		builder.append(TXT_SEPERATOR);
		builder.append(address.getGu());
		builder.append(TXT_SEPERATOR);
		builder.append(address.getDongAddress().getDong());
		builder.append(TXT_SEPERATOR);
		builder.append(address.getDongAddress().getDetails());
		builder.append("\n");
		
		return builder.toString();
	}*/
}
