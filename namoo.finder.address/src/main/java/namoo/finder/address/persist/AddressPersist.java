package namoo.finder.address.persist;

import java.util.List;

import namoo.finder.address.entity.Address;

public interface AddressPersist {

	public List<Address> findAddress(String tmpStreet);
}
