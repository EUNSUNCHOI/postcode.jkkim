package namoo.finder.address.persist.file;

import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.AddressPersist;
import namoo.finder.address.persist.file.store.AddressStore;

public class AddressFilePersist implements AddressPersist{
	
	private AddressStore addressStore;
	
	public AddressFilePersist(){
		this.addressStore = AddressStore.getInstance();
	}

	public void createAddress(Address address) {
		// TODO Auto-generated method stub
		
	}

	public List<Address> findAddressByDong(String tmpDong) {
		// TODO Auto-generated method stub
		return addressStore.findAddressByDong(tmpDong);
	}

	public List<Address> findAddressByStreet(String tmpStreet) {
		// TODO Auto-generated method stub
		return addressStore.findAddressByStreet(tmpStreet);
	}


}
