package com.watchhub.watchstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watchhub.watchstore.util.constants.Constant;
import com.watchhub.watchstore.dao.AddressDao;
import com.watchhub.watchstore.entity.Address;
import com.watchhub.watchstore.exceptions.NotFoundException;
import com.watchhub.watchstore.service.AddressService;

/**
 * @author deepankarbatra
 * Implementation of the {@link AddressService} interface that provides methods for managing addresses.
 */
@Service
public class AddressServiceImpl implements AddressService {

	private AddressDao addressDao;

	/**
     * Constructs a new {@code AddressServiceImpl} with the specified {@link AddressDao}.
     *
     * @param addressDao The {@link AddressDao} to be used for data access.
     */
	@Autowired
	public AddressServiceImpl(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@Override
	public Address save(Address address, String userId) {
		address.setUserId(userId);
		Address addressRes = this.addressDao.save(address);
		return addressRes;
	}

	@Override
	public List<Address> get(String userId) {
		List<Address> address = (List<Address>) this.addressDao.findByUserId(userId);
		if (address.isEmpty()) {
			throw new NotFoundException("Address", "Address not found");
		}
		return address;
	}

	@Override
	public Address update(Long addressId, Address address) {
		Optional<Address> addressOptional = this.addressDao.findById(addressId);
		addressOptional.ifPresentOrElse(addressRes -> {
			addressRes.setAddressId(addressId);
			addressRes.setStreetName(address.getStreetName());
			addressRes.setCity(address.getCity());
			addressRes.setCountry(address.getCountry());
			addressRes.setLandmark(address.getLandmark());
			addressRes.setPhoneNumber(address.getPhoneNumber());
			addressRes.setPincode(address.getPincode());
			addressRes.setState(address.getState());
		}, () -> {
			throw new NotFoundException("update address", "Address not found");
		});
		return this.addressDao.save(addressOptional.get());
	}

	@Override
	public void delete(Long addressId) throws NotFoundException {
		Optional<Address> addressOptional = this.addressDao.findById(addressId);
		addressOptional.ifPresentOrElse(address -> this.addressDao.deleteById(address.getAddressId()), () -> {
			throw new NotFoundException("delete address", "Address not found");
		});
	}

	@Override
	public Address findByIdAndUserId(long addressId, String userId) {
		Optional<Address> address=this.addressDao.findByAddressIdAndUserId(addressId,userId);
		if(address.isEmpty())
			throw new NotFoundException("addressId", Constant.ADDRESS_NOT_FOUND + addressId);
		return address.get();
	}

}
