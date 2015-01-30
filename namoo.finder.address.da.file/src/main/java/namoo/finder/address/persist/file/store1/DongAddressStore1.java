package namoo.finder.address.persist.file.store1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.file.creator.CreateCSVorDongAddress;

//index 파일 추가 전
public class DongAddressStore1 {

	private static DongAddressStore1 addressStore;
	
	private CreateCSVorDongAddress creator;
	
	private static final String CSV_SEPERATOR = ",";
	
	private static final String DONG_FILE_NAME = "address_dong.csv";
	
	private DongAddressStore1(){
		
		creator = new CreateCSVorDongAddress();
	}
	
	public static DongAddressStore1 getInstance(){
		if(addressStore == null){
			return new DongAddressStore1();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
	public boolean registerAddress(Address address){
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(DONG_FILE_NAME, true);
			
			fw.write(creator.createCSVFromDongAddress(address));
			
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
	
	public List<Address> findAddress(String tmpDong){
		//
		long start = System.currentTimeMillis();
		
		BufferedReader br = null;
		
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			//본래 파일에서 address를 가져옴
			br = new BufferedReader(new FileReader(DONG_FILE_NAME));
			
			while((tmpAddress = br.readLine()) != null){
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//tmpDong으로 검색
				if(data[3].contains(tmpDong)){
					addressList.add(creator.createDongAddressFromCSV(tmpAddress));
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
	
	public List<Address> findAddressByDongPostcode(String postcode) {
		// 
		BufferedReader br = null;
		String tmpAddress = null;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			//본래파일에서 가져옴
			br = new BufferedReader(new FileReader(DONG_FILE_NAME));
			
			while((tmpAddress = br.readLine()) != null){
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//postcode로 검색
				if(data[0].contains(postcode)){
					addressList.add(creator.createDongAddressFromCSV(tmpAddress));
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
			fw = new FileWriter(DONG_FILE_NAME, false);
			
			while((txtAddress = br.readLine()) != null){
				Address address = creator.createDongAddressFromTXT(txtAddress);
				String csvAddress = creator.createCSVFromDongAddress(address);
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
				
		return DONG_FILE_NAME;
	}
}
