package namoo.finder.address.persist.file;

import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.AddressPersist;
import namoo.finder.address.persist.file.store.DongAddressStore;
import namoo.finder.address.persist.file.store.StreetAddressStore;

public class AddressFilePersist implements AddressPersist{
	
	private DongAddressStore dongAddressStore;
	private StreetAddressStore streetAddressStore;
		
	public AddressFilePersist(){
		this.dongAddressStore = DongAddressStore.getInstance();
		this.streetAddressStore = StreetAddressStore.getInstance();
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
		return dongAddressStore.returnFile(fileName);
	}

}
