package namoo.finder.address.service;

import java.util.List;

import namoo.finder.address.entity.Address;

public interface AddressService {

	public boolean createAddressByDong(Address address);
	public boolean createAddressByStreet(Address address);
	public List<Address> readAddressByDong(String tmpDong);
	public List<Address> readAddressByStreet(String tmpStreet);
	public List<Address> readAddressByDongPostcode(String postcode);
	public List<Address> readAddressByStreetPostcode(String postcode);
	//public void updateAddress(Address address);
	//public void deleteAddress();
	public Address checkAddress(List<Address> addressList, int num);
	public String changeFileToCSV(String fileName);
}
