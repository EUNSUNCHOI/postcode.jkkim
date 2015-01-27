package namoo.finder.address.persist.file.store;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;

public class AddressStore {

	private static AddressStore addressStore;
	
	private static final String CSV_SEPERATOR = ",";
	private static final String ADDRESS_FILE_NAME = "address_update2.csv";
	
	private AddressStore(){
	}
	
	public static AddressStore getInstance(){
		if(addressStore == null){
			return new AddressStore();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
	public List<Address> findAddress(String tmpStreet){
		//파일에서 찾아옴
		
		BufferedReader br = null;
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			FileInputStream fis = new FileInputStream(ADDRESS_FILE_NAME);
			br = new BufferedReader(new InputStreamReader(fis));
			
			while ((tmpAddress = br.readLine()) != null) {
				
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//도로명으로 검색하여 추림
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
		// TODO Auto-generated method stub
		String[] data = tmpAddress.split(CSV_SEPERATOR);
		
		Address address = new Address(data[2], data[4], data[9], data[12], Integer.parseInt(data[0]));
		
		return address;
	}
}
