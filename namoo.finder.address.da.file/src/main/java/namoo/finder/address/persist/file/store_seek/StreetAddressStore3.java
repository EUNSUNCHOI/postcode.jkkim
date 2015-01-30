package namoo.finder.address.persist.file.store_seek;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.file.creator.CreateCSVorStreetAddress;

public class StreetAddressStore3 {

	private static StreetAddressStore3 addressStore;
	
	private CreateCSVorStreetAddress creator;
	
	private static final String CSV_SEPERATOR = ",";
	
	private static final String STREET_FILE_NAME = "address_street.csv";
	private static final String STREET_INDEX_FILE_NAME = "address_street_index.csv";
	
	private StreetAddressStore3(){
		creator = new CreateCSVorStreetAddress();
	}
	
	public static StreetAddressStore3 getInstance(){
		if(addressStore == null){
			return new StreetAddressStore3();
		}
		return addressStore;
	}

	//----------------------------------------------------
	
public boolean registerAddress(Address address){
		
		RandomAccessFile raf = null;
		FileWriter fw_index = null;
		
		try {
			raf = new RandomAccessFile(STREET_FILE_NAME, "rw");
			fw_index = new FileWriter(STREET_INDEX_FILE_NAME, true);
			
			String csvAddress = creator.createCSVFromStreetAddress(address);
			
			raf.skipBytes((int)raf.length());
			long pointer = raf.getFilePointer();
			raf.write(csvAddress.getBytes());

			fw_index.write(creator.createStreetIndexCSVFromAddress(address, pointer));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw_index != null)
					fw_index.close();
				if(raf != null)
					raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Address> findAddress(String tmpStreet){
		//
		long start = System.currentTimeMillis();
		
		RandomAccessFile raf = null;
		BufferedReader br_index = null;
		
		String tmpAddress = null;
		
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			br_index = new BufferedReader(new FileReader(STREET_INDEX_FILE_NAME));
			raf = new RandomAccessFile(STREET_FILE_NAME, "r");
			
			while ((tmpAddress = br_index.readLine()) != null) {
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//읍면동명으로 index에서 검색
				if(data[1].contains(tmpStreet)){
					raf.seek(Integer.parseInt(data[2]));
					String line = new String(raf.readLine().getBytes("ISO-8859-1"), "EUC-KR");
					addressList.add(creator.createStreetAddressFromCSV(line));
				}
			}			
			
			long end = System.currentTimeMillis();
			System.out.println( "findAddress 실행 시간 : " + ( end - start )/1000.0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(br_index != null)
					br_index.close();
				if(raf != null)
					raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return addressList;
	}
	
	public List<Address> findAddressByDongPostcode(String postcode) {
		// 
		RandomAccessFile raf = null;
		BufferedReader br_index = null;
		
		String tmpAddress = null;
		
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			br_index = new BufferedReader(new FileReader(STREET_INDEX_FILE_NAME));
			raf = new RandomAccessFile(STREET_FILE_NAME, "r");
			
			while ((tmpAddress = br_index.readLine()) != null) {
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//우편번호로 index에서 검색
				if(data[0].contains(postcode)){
					raf.seek(Integer.parseInt(data[2]));
					String line = new String(raf.readLine().getBytes("ISO-8859-1"), "EUC-KR");
					addressList.add(creator.createStreetAddressFromCSV(line));
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(br_index != null)
					br_index.close();
				if(raf != null)
					raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return addressList;
	}
	
	public String returnFile(String tmpFileName){
		//
		long start = System.currentTimeMillis();
		
		RandomAccessFile raf = null;
		BufferedReader br = null;
		FileWriter fw_index = null;
		String txtAddress = null;
		long pointer = 0;
		
		try {
			br = new BufferedReader(new FileReader(tmpFileName));
			fw_index = new FileWriter(STREET_INDEX_FILE_NAME, false);
			raf = new RandomAccessFile(STREET_FILE_NAME, "rw");
			
			while((txtAddress = br.readLine()) != null){
				
				//txt파일로부터 address를 만들고 address로부터 csv파일을 씀
				Address address = creator.createStreetAddressFromTXT(txtAddress);
				String csvAddress = creator.createCSVFromStreetAddress(address);
				
				pointer = raf.getFilePointer();
				raf.write(csvAddress.getBytes());

				//csvIndex파일을 씀
				String csvIndex = creator.createStreetIndexCSVFromAddress(address, pointer);
				fw_index.write(csvIndex);
			}		
			
			long end = System.currentTimeMillis();
			System.out.println( "returnFile 실행 시간 : " + ( end - start )/1000.0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
				try {
					if(fw_index != null)
						fw_index.close();
					if(br != null)
						br.close();
					if(raf != null)
						raf.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
			
		}
				
		return STREET_FILE_NAME;
	}
}
