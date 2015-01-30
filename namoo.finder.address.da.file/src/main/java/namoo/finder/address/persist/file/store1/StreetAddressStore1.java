package namoo.finder.address.persist.file.store1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.file.creator.CreateCSVorStreetAddress;

// index 파일 추가 전
public class StreetAddressStore1 {

	private static StreetAddressStore1 addressStore;
	
	private CreateCSVorStreetAddress creator;
	
	private static final String CSV_SEPERATOR = ",";
	
	private static final String STREET_FILE_NAME = "address_street.csv";
	
	private StreetAddressStore1(){
		creator = new CreateCSVorStreetAddress();
	}
	
	public static StreetAddressStore1 getInstance(){
		if(addressStore == null){
			return new StreetAddressStore1();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
public boolean registerAddress(Address address){
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(STREET_FILE_NAME, true);
			
			fw.write(creator.createCSVFromStreetAddress(address));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public List<Address> findAddress(String tmpStreet){
		//
		long start = System.currentTimeMillis();
		
		BufferedReader br = null;
		
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			//본래 파일에서 address를 가져옴
			br = new BufferedReader(new FileReader(STREET_FILE_NAME));
			
			while((tmpAddress = br.readLine()) != null){
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//tmpDong으로 검색
				if(data[3].contains(tmpStreet)){
					addressList.add(creator.createStreetAddressFromCSV(tmpAddress));
				}
			}
			
			long end = System.currentTimeMillis();
			System.out.println( "findAddress 실행 시간 : " + ( end - start )/1000.0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return addressList;
	}
	
	public List<Address> findAddressByStreetPostcode(String postcode) {
		// 
		BufferedReader br = null;
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			//본래파일에서 가져옴
			br = new BufferedReader(new FileReader(STREET_FILE_NAME));
			
			while((tmpAddress = br.readLine()) != null){
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//postcode로 검색
				if(data[0].contains(postcode)){
					addressList.add(creator.createStreetAddressFromCSV(tmpAddress));
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return addressList;
	}
	
	public String returnFile(String tmpFileName){
		//
		long start = System.currentTimeMillis();
		
		BufferedReader br = null;
		FileWriter fw = null;
		
		String txtAddress = null;
		
		try {
			br = new BufferedReader(new FileReader(tmpFileName));
			fw = new FileWriter(STREET_FILE_NAME, false);
			
			while((txtAddress = br.readLine()) != null){
				Address address = creator.createStreetAddressFromTXT(txtAddress);
				String csvAddress = creator.createCSVFromStreetAddress(address);
				fw.write(csvAddress);
			}
			
			long end = System.currentTimeMillis();
			System.out.println( "returnFile 실행 시간 : " + ( end - start )/1000.0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw != null)
					fw.close();
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		return STREET_FILE_NAME;
	}
}
