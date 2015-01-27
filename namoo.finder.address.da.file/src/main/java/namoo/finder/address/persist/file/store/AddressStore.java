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

public class AddressStore {

	private static AddressStore addressStore;
	
	private static final String CSV_SEPERATOR = ",";
	private static final String TXT_SEPERATOR = "	";
	private static final String STREET_FILE_NAME = "C:/address_update2.csv";
	private static final String DONG_FILE_NAME = "C:/address_dong.txt";
	
	private AddressStore(){
	}
	
	public static AddressStore getInstance(){
		if(addressStore == null){
			return new AddressStore();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
	public List<Address> findAddressByStreet(String tmpStreet){
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

	public List<Address> findAddressByDong(String tmpDong){
		//파일에서 찾아옴
		
		BufferedReader br = null;
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			FileInputStream fis = new FileInputStream(DONG_FILE_NAME);
			br = new BufferedReader(new InputStreamReader(fis));
			
			while ((tmpAddress = br.readLine()) != null) {
				
				String[] data = tmpAddress.split(TXT_SEPERATOR);
				//읍면동명으로 검색
				if(data[3].contains(tmpDong)){
					Address address = createAddressFromTXT(tmpAddress);
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
	
	private Address createAddressFromTXT(String tmpAddress){
		
		String[] data = tmpAddress.split(TXT_SEPERATOR);
		
		DongAddress dongAddress = new DongAddress(data[3], data[4]);
		Address address = new Address(data[1], data[2], dongAddress, null, data[0]); 
		
		return address;
	}
}
