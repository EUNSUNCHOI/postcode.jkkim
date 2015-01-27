package namoo.finder.address.persist;

import java.util.List;

import namoo.finder.address.entity.Address;

public interface AddressPersist {

	public void registerAddressByDong(Address address);
	public void registerAddressByStreet(Address address);
	public List<Address> findAddressByDong(String tmpDong);
	public List<Address> findAddressByStreet(String tmpStreet);
}
