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
	
	public void createAddress(Address address) {
		// TODO Auto-generated method stub
		
	}

	public Address checkAddress(List<Address> addressList, int num) {
		// TODO Auto-generated method stub
		return addressList.get(num);
	}

	public List<Address> readAddressByDong(String tmpDong) {
		// TODO Auto-generated method stub
		if(tmpDong == null){
			return null;
		}
		return addressPersist.findAddressByDong(tmpDong);
	}

	public List<Address> readAddressByStreet(String tmpStreet) {
		// TODO Auto-generated method stub
		if(tmpStreet == null){
			return null;
		}
		
		return addressPersist.findAddressByStreet(tmpStreet);
	}

}
