package namoo.finder.address.persist.file;

import java.io.File;
import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.AddressPersist;
import namoo.finder.address.persist.file.store1.DongAddressStore1;
import namoo.finder.address.persist.file.store1.StreetAddressStore1;

public class AddressFilePersist implements AddressPersist{
	
	private DongAddressStore1 dongAddressStore;
	private StreetAddressStore1 streetAddressStore;
	
	private static final String STREET_FILE_NAME = "address_street.txt";
	private static final String DONG_FILE_NAME = "address_dong.txt";
		
	public AddressFilePersist(){
		this.dongAddressStore = DongAddressStore1.getInstance();
		this.streetAddressStore = StreetAddressStore1.getInstance();
	}

	public boolean registerAddressByDong(Address address) {
		//
		return dongAddressStore.registerAddress(address);
	}
	
	public boolean registerAddressByStreet(Address address){
		//
		return streetAddressStore.registerAddress(address);
	}

	public List<Address> findAddressByDong(String tmpDong) {
		//
		return dongAddressStore.findAddress(tmpDong);
	}

	public List<Address> findAddressByStreet(String tmpStreet) {
		//
		return streetAddressStore.findAddress(tmpStreet);
	}

	public List<Address> findAddressByDongPostcode(String postcode) {
		//
		return dongAddressStore.findAddressByDongPostcode(postcode);
	}
	
	public List<Address> findAddressByStreetPostcode(String postcode) {
		//
		return streetAddressStore.findAddressByStreetPostcode(postcode);
	}

	public String returnFile(String fileName) {
		//
		if(fileName == null){
			return null;
		}
		//file 이 street파일인지 dong파일인지 분별
		File file = new File(fileName);
		if(file.getPath().equals(DONG_FILE_NAME)){
			return dongAddressStore.returnFile(fileName);
		}
		else if(file.getPath().equals(STREET_FILE_NAME)){
			return streetAddressStore.returnFile(fileName);
		}
		
		return null;
	}

}
