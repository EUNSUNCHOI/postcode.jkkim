package namoo.finder.address.persist.file.store_skip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.file.creator.CreateCSVorStreetAddress;

public class StreetAddressStore2 {

	private static StreetAddressStore2 addressStore;
	
	private CreateCSVorStreetAddress creator;
	
	private static final String CSV_SEPERATOR = ",";
	
	private static final String STREET_FILE_NAME = "address_street.csv";
	private static final String STREET_INDEX_FILE_NAME = "address_street_index.csv";
	
	private StreetAddressStore2(){
		creator = new CreateCSVorStreetAddress();
	}
	
	public static StreetAddressStore2 getInstance(){
		if(addressStore == null){
			return new StreetAddressStore2();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
public boolean registerAddress(Address address){
		
		FileWriter fw = null;
		FileWriter fw_index = null;
		
		try {
			fw = new FileWriter(STREET_FILE_NAME, true);
			fw_index = new FileWriter(STREET_INDEX_FILE_NAME, true);
			
			String csvAddress = creator.createCSVFromStreetAddress(address);
			long pointer = csvAddress.getBytes().length;
			
			fw.write(csvAddress);
			fw_index.write(creator.createStreetIndexCSVFromAddress(address, pointer));

		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw != null)
					fw.close();
				if(fw_index != null)
					fw_index.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Address> findAddress(String tmpStreet){
		//
		long start = System.currentTimeMillis();
		
		BufferedReader br = null;
		BufferedReader br_index = null;
		
		String tmpAddress = null;
		
		List<Address> addressList = new ArrayList<Address>();
		try {
			br_index = new BufferedReader(new FileReader(STREET_INDEX_FILE_NAME));
			br = new BufferedReader(new FileReader(STREET_FILE_NAME));
			
			//br.mark(0);
			//br.reset();
			
			while ((tmpAddress = br_index.readLine()) != null) {
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//읍면동명으로 index에서 검색
				if(data[1].contains(tmpStreet)){
					String line = br.readLine();
					addressList.add(creator.createStreetAddressFromCSV(line));
				}
				else{
					br.skip(Integer.parseInt(data[2])); // 글자수대로 스킵 => 위치가 바뀌면 안됨 ㅜㅜ
				}
			}			
			
			long end = System.currentTimeMillis();
			System.out.println( "returnFile 실행 시간 : " + ( end - start )/1000.0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(br != null)
					br.close();
				if(br_index != null)
					br_index.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return addressList;
	}
	
	public List<Address> findAddressByDongPostcode(String postcode) {
		// 
		BufferedReader br = null;
		BufferedReader br_index = null;
		
		String tmpAddress = null;
		
		List<Address> addressList = new ArrayList<Address>();
		try {
			br_index = new BufferedReader(new FileReader(STREET_INDEX_FILE_NAME));
			br = new BufferedReader(new FileReader(STREET_FILE_NAME));
			
			//br.mark(0);
			//br.reset();
			
			while ((tmpAddress = br_index.readLine()) != null) {
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//우편번호로 index에서 검색
				if(data[0].contains(postcode)){
					String line = br.readLine();
					addressList.add(creator.createStreetAddressFromCSV(line));
				}
				else{
					br.skip(Integer.parseInt(data[2])); // 글자수대로 스킵 => 위치가 바뀌면 안됨 ㅜㅜ
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(br != null)
					br.close();
				if(br_index != null)
					br_index.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return addressList;
	}
	
	public String returnFile(String tmpFileName){
		//
		long start = System.currentTimeMillis();
		
		BufferedReader br = null;
		FileWriter fw = null;
		FileWriter fw_index = null;
		
		String txtAddress = null;
		long pointer = 0;
		
		try {
			br = new BufferedReader(new FileReader(tmpFileName));
			fw = new FileWriter(STREET_FILE_NAME, false);
			fw_index = new FileWriter(STREET_INDEX_FILE_NAME, false);
			
			while((txtAddress = br.readLine()) != null){
				Address address = creator.createStreetAddressFromTXT(txtAddress);
				String csvAddress = creator.createCSVFromStreetAddress(address);
				fw.write(csvAddress);
				
				pointer = csvAddress.getBytes().length;

				String csvIndex = creator.createStreetIndexCSVFromAddress(address, pointer);
				fw_index.write(csvIndex);
			}
			
			long end = System.currentTimeMillis();
			System.out.println( "returnFile 실행 시간 : " + ( end - start )/1000.0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw != null)
					fw.close();
				if(fw_index != null)
					fw_index.close();
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		return STREET_FILE_NAME;
	}
}
