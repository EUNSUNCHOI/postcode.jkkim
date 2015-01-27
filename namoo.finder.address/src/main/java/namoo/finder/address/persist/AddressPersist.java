package namoo.finder.address.persist;

import java.util.List;

import namoo.finder.address.entity.Address;

public interface AddressPersist {

	public boolean registerAddressByDong(Address address);
	public boolean registerAddressByStreet(Address address);
	public List<Address> findAddressByDong(String tmpDong);
	public List<Address> findAddressByStreet(String tmpStreet);
}
