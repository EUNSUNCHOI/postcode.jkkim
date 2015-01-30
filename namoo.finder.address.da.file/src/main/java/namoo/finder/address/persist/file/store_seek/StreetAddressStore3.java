package namoo.finder.address.persist.file.store_seek;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
		//
		FileWriter fw = null;
		FileWriter fw_index = null;
		
		try {
			fw = new FileWriter(STREET_FILE_NAME, true);
			fw_index = new FileWriter(STREET_INDEX_FILE_NAME, true);
			
			fw.write(creator.createCSVFromStreetAddress(address));
			//fw_index.write(creator.createStreetIndexCSVFromAddress(address, pointer));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(fw != null && fw_index != null){
				try {
					fw.close();
					fw_index.close();
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
		List<String> idList = new ArrayList<String>();
		
		try {
			FileInputStream fis = new FileInputStream(STREET_INDEX_FILE_NAME);
			br = new BufferedReader(new InputStreamReader(fis));
			
			while ((tmpAddress = br.readLine()) != null) {
				
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//읍면동명으로 index에서 검색
				if(data[1].contains(tmpStreet)){
					idList.add(data[2]);
				}
			}
			//본래 파일에서 address를 가져옴
			br = new BufferedReader(new InputStreamReader(new FileInputStream(STREET_FILE_NAME)));
			
			while((tmpAddress = br.readLine()) != null){
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//id로 검색
				for (String id : idList) {
					if(id.equals(data[0])){
						addressList.add(creator.createStreetAddressFromCSV(tmpAddress));
					}
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
		List<String> idList = new ArrayList<String>();
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			FileInputStream fis = new FileInputStream(STREET_INDEX_FILE_NAME);
			br = new BufferedReader(new InputStreamReader(fis));
			
			while ((tmpAddress = br.readLine()) != null) {
				
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//우편번호로 index에서 검색
				if(data[0].contains(postcode)){
					idList.add(data[2]);
				}
			}
			//본래파일에서 가져옴
			br = new BufferedReader(new InputStreamReader(new FileInputStream(STREET_FILE_NAME)));
			
			while((tmpAddress = br.readLine()) != null){
				String[] data = tmpAddress.split(CSV_SEPERATOR);
				//id로 검색
				for (String id : idList) {
					if(id.equals(data[0])){
						addressList.add(creator.createStreetAddressFromCSV(tmpAddress));
					}
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
		BufferedReader br = null;
		FileWriter fw = null;
		FileWriter fw_index = null;
		FileInputStream fis = null;
		String txtAddress = null;
		
		try {
			fis = new FileInputStream(tmpFileName);
			br = new BufferedReader(new InputStreamReader(fis));
			fw = new FileWriter(STREET_FILE_NAME, false);
			fw_index = new FileWriter(STREET_INDEX_FILE_NAME, false);
			
			while((txtAddress = br.readLine()) != null){
				Address address = creator.createStreetAddressFromTXT(txtAddress);
				String csvAddress = creator.createCSVFromStreetAddress(address);
				fw.write(csvAddress);
				//인덱스에 담을 위치정보를 받아 와야함
				
				
				

				//인덱스 정렬
/*				
				if(indexList.size() < 2){   ==> 시간 겁나오래걸림...
					String csvIndex = createIndexCSVFromAddress(address);
					indexList.add(csvIndex);
				}
				else{					
					//'zipcode'는 제외한 우편번호 비교
					for (int i=1 ; i<indexList.size() ; i++) {
						String csvIndex = indexList.get(i);
						String[] data = csvIndex.split(CSV_SEPERATOR);
						String sortedPostcode = data[0];
												
						if(Integer.parseInt(address.getPostcode())
								< Integer.parseInt(sortedPostcode)) {
							
							indexList.add(i+1, indexList.get(i));
							indexList.add(i, createCSVFromAddress(address));
							break;
						}
						if(i == indexList.size()-1){
							indexList.add(createCSVFromAddress(address));
							break;
						}
					}
				}
*/
				//String csvIndex = creator.createStreetIndexCSVFromAddress(address, pointer);
				//indexList.add(csvIndex);
				//fw_index.write(csvIndex);
			}
			
			//정렬된 addressList를 index_file에 넣는다.
//			for (String csvIndex : indexList) {
//				fw_index.write(csvIndex);
//			}
			
		} catch (IOException e) {
			return null;
		} finally{
			if(fw != null && fw_index != null){
				try {
					fw.close();
					fw_index.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
				
		return STREET_FILE_NAME;
	}
}
