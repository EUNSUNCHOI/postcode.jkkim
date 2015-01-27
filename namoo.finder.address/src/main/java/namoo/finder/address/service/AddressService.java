package namoo.finder.address.service;

import java.util.List;

import namoo.finder.address.entity.Address;

public interface AddressService {

	public void createAddressByDong(Address address);
	public void createAddressByStreet(Address address);
	public List<Address> readAddressByDong(String tmpDong);
	public List<Address> readAddressByStreet(String tmpStreet);
	//public void updateAddress(Address address);
	//public void deleteAddress();
	public Address checkAddress(List<Address> addressList, int num);
}
