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

	public Address checkAddress(List<Address> addressList, int num) {
		// 
		return addressList.get(num);
	}

	public List<Address> readAddressByDong(String tmpDong) {
		//
		if(tmpDong == null){
			return null;
		}
		return addressPersist.findAddressByDong(tmpDong);
	}

	public List<Address> readAddressByStreet(String tmpStreet) {
		//
		if(tmpStreet == null){
			return null;
		}
		
		return addressPersist.findAddressByStreet(tmpStreet);
	}

	public List<Address> readAddressByDongPostcode(String postcode) {
		//
		if(postcode == null){
			return null;
		}
		
		return addressPersist.findAddressByDongPostcode(postcode);
	}
	
	public List<Address> readAddressByStreetPostcode(String postcode) {
		//
		if(postcode == null){
			return null;
		}
		
		return addressPersist.findAddressByStreetPostcode(postcode);
	}
	
	public boolean createAddressByDong(Address address) {
		//
		if(address == null){
			return false;
		}
		
		return addressPersist.registerAddressByDong(address);
	}

	public boolean createAddressByStreet(Address address) {
		//
		if(address == null){
			return false;
		}
		
		return addressPersist.registerAddressByStreet(address);
	}

	public String changeFileToCSV(String fileName) {
		//
		if(fileName == null){
			return null;
		}
		
		return addressPersist.returnFile(fileName);
	}
}
