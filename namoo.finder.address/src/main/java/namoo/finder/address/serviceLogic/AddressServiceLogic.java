package namoo.finder.address.serviceLogic;

import java.util.List;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.AddressPersist;
import namoo.finder.address.service.AddressService;

public class AddressServiceLogic implements AddressService {

	private AddressPersist addressPersist;
	
	public AddressServiceLogic(AddressPersist addressPersist){
		this.addressPersist = addressPersist;
	}
	
	//------------------------------------------------------------
	
	public List<Address> readAddress(String tmpStreet) {
		// TODO Auto-generated method stub
		
		if(tmpStreet == null){
			return null;
		}
		
		return addressPersist.findAddress(tmpStreet);
	}

	public Address checkAddress(List<Address> addressList, int num) {
		// TODO Auto-generated method stub
		return addressList.get(num);
	}

}
