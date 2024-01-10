package com.watchhub.watchstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.watchhub.watchstore.dao.AddressDao;
import com.watchhub.watchstore.entity.Address;
import com.watchhub.watchstore.exceptions.NotFoundException;
import com.watchhub.watchstore.service.impl.AddressServiceImpl;

public class AddressServiceImplTest {

	@Mock
	private AddressDao addressDao;

	@InjectMocks
	private AddressServiceImpl addressService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveAddressTest() {
		Address address = new Address();
		String userId = "deepankar1@gmail.com";
		when(addressDao.save(address)).thenReturn(address);

		Address savedAddress = addressService.save(address, userId);

		assertEquals(address, savedAddress);
		verify(addressDao, times(1)).save(address);
	}

	@Test
	public void getAddressByUserIdTest() {
		String userId = "123";
		List<Address> addresses = new ArrayList<>();
		addresses.add(new Address());
		when(addressDao.findByUserId(userId)).thenReturn(addresses);

		List<Address> retrievedAddresses = addressService.get(userId);

		assertFalse(retrievedAddresses.isEmpty());
		assertEquals(addresses, retrievedAddresses);
		verify(addressDao, times(1)).findByUserId(userId);
	}

	@Test
	public void getAddressList_NotFoundExceptionTest() {
		String userId = "123";
		List<Address> emptyAddressList = new ArrayList<>(); // Empty address list to simulate addresses not found
		when(addressDao.findByUserId(userId)).thenReturn(emptyAddressList);

		NotFoundException exception = assertThrows(NotFoundException.class,
		() -> addressService.get(userId));

		assertEquals("Address not found", exception.getMessage());

		verify(addressDao, times(1)).findByUserId(userId);
		verifyNoMoreInteractions(addressDao);
	}


	@Test
	public void updateAddressTest() {
		// Given
		long addressId = 1L;
		Address addressToUpdate = new Address();
		Optional<Address> optionalAddress = Optional.of(addressToUpdate);
		when(addressDao.findById(addressId)).thenReturn(optionalAddress);
		when(addressDao.save(addressToUpdate)).thenReturn(addressToUpdate);

		// When
		Address updatedAddress = addressService.update(addressId, addressToUpdate);

		// Then
		assertEquals(addressToUpdate, updatedAddress);
		verify(addressDao, times(1)).findById(addressId);
		verify(addressDao, times(1)).save(addressToUpdate);
	}

	@Test
	public void updateAddress_NotFoundExceptionTest() {
		long addressId = 1L;
		Address addressToUpdate = new Address();
		Optional<Address> optionalAddress = Optional.empty();
		when(addressDao.findById(addressId)).thenReturn(optionalAddress);

		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> addressService.update(addressId, addressToUpdate));

		assertEquals("Address not found", exception.getMessage());

		verify(addressDao, times(1)).findById(addressId);
		verifyNoMoreInteractions(addressDao);
	}

	@Test
	public void deleteAddressTest() {
		long addressId = 1L;
		Address addressToDelete = new Address();
		addressToDelete.setAddressId(addressId);
		Optional<Address> optionalAddress = Optional.of(addressToDelete);
		when(addressDao.findById(addressId)).thenReturn(optionalAddress);

		assertDoesNotThrow(() -> addressService.delete(addressId));

		verify(addressDao, times(1)).findById(addressId);
		verify(addressDao, times(1)).deleteById(addressId);
	}

	@Test
	public void deleteAddress_NotFoundExceptionTest() {
		long addressId = 1L;
		Optional<Address> optionalAddress = Optional.empty();
		when(addressDao.findById(addressId)).thenReturn(optionalAddress);

		NotFoundException exception = assertThrows(NotFoundException.class, () -> addressService.delete(addressId));

		assertEquals("Address not found", exception.getMessage());

		verify(addressDao, times(1)).findById(addressId);
		verifyNoMoreInteractions(addressDao);
	}

}
