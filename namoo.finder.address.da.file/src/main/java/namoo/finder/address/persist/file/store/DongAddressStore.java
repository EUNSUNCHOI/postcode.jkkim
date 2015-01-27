package namoo.finder.address.persist.file.store;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.entity.DongAddress;

public class DongAddressStore {

	private static DongAddressStore addressStore;
	
	private static final String TXT_SEPERATOR = "	";
	private static final String DONG_FILE_NAME = "address_dong.txt";
	
	private DongAddressStore(){
	}
	
	public static DongAddressStore getInstance(){
		if(addressStore == null){
			return new DongAddressStore();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
	public boolean registerAddress(Address address){
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(DONG_FILE_NAME, true);
			
			fw.write(createTXTFromAddress(address));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public List<Address> findAddress(String tmpDong){
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
	
	private Address createAddressFromTXT(String tmpAddress){
		
		String[] data = tmpAddress.split(TXT_SEPERATOR);
		
		DongAddress dongAddress = new DongAddress(data[3], data[4]);
		Address address = new Address(data[1], data[2], dongAddress, null, data[0]); 
		
		return address;
	}
	
	private String createTXTFromAddress(Address address){
		
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
	}
}
