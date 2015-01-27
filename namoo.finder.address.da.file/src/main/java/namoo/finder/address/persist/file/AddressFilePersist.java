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
		// TODO Auto-generated method stub
		return dongAddressStore.registerAddress(address);
	}
	
	public boolean registerAddressByStreet(Address address){
		// TODO Auto-generated method stub
		return streetAddressStore.registerAddress(address);
	}

	public List<Address> findAddressByDong(String tmpDong) {
		// TODO Auto-generated method stub
		return dongAddressStore.findAddress(tmpDong);
	}

	public List<Address> findAddressByStreet(String tmpStreet) {
		// TODO Auto-generated method stub
		return streetAddressStore.findAddress(tmpStreet);
	}


}
