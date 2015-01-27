package namoo.finder.address.persist.file.store;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.entity.DongAddress;
import namoo.finder.address.entity.StreetAddress;

public class StreetAddressStore {

private static StreetAddressStore addressStore;
	
	private static final String CSV_SEPERATOR = ",";
	private static final String STREET_FILE_NAME = "address_update2.csv";
	
	private StreetAddressStore(){
	}
	
	public static StreetAddressStore getInstance(){
		if(addressStore == null){
			return new StreetAddressStore();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
	public void registerAddress(Address address){
		
	}
	
	public List<Address> findAddress(String tmpStreet){
		//파일에서 찾아옴
		
		BufferedReader br = null;
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			FileInputStream fis = new FileInputStream(STREET_FILE_NAME);
			br = new BufferedReader(new InputStreamReader(fis));
			
			while ((tmpAddress = br.readLine()) != null) {
				
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//도로명으로 검색
				if(data[9].contains(tmpStreet)){
					Address address = createAddressFromCSV(tmpAddress);
					addressList.add(address);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return addressList;
	}
	
	private Address createAddressFromCSV(String tmpAddress) {
		
		String[] data = tmpAddress.split(CSV_SEPERATOR);
		
		StreetAddress streetAddress = new StreetAddress(data[9], data[12]);
		Address address = new Address(data[2], data[4], null, streetAddress, data[0]);
		
		return address;
	}

}
