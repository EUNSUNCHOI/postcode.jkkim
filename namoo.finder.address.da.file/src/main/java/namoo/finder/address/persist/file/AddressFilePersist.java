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
	
	public List<Address> findAddress(String tmpStreet) {
		// TODO Auto-generated method stub
		//도로명으로 검색
		return addressStore.findAddress(tmpStreet);
	}

}
