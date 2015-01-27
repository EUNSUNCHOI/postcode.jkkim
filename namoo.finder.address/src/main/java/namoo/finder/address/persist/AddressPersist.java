package namoo.finder.address.persist;

import java.util.List;

import namoo.finder.address.entity.Address;

public interface AddressPersist {

	public void createAddress(Address address);
	public List<Address> findAddressByDong(String tmpDong);
	public List<Address> findAddressByStreet(String tmpStreet);
}
