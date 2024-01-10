package com.watchhub.watchstore.service;

import java.util.List;

import com.watchhub.watchstore.entity.Address;
import com.watchhub.watchstore.exceptions.NotFoundException;


/**
 * @author deepankarbatra
 * 
 * Service interface for managing addresses.
 */
public interface AddressService {

	
    /**
     * Saves the given address.
     *
     * @param address The address to save.
     * @return The saved address.
     */
    Address save(Address address, String userId);

    /**
     * Retrieves all addresses associated with the given user ID.
     *
     * @param userId The user ID to retrieve addresses for.
     * @return A list of addresses associated with the user ID.
     */
    List<Address> get(String userId);

    /**
     * Updates the address with the specified address ID.
     *
     * @param addressId The ID of the address to update.
     * @param addressTransform   The updated address information.
     * @return The updated address.
     */
    Address update(Long addressId, Address addressTransform);

    /**
     * Deletes the address with the specified address ID.
     *
     * @param addressId The ID of the address to delete.
     */
    void delete(Long addressId);

    /**
     * Finds an address by its address ID.
     *
     * @param addressId The ID of the address to find.
     * @return An Optional containing the found address, or an empty Optional if not found.
     */
    
    /**
	 * Retrieves an Address object by its addressId and userId.
	 *
	 * @param addressId The ID of the address.
	 * @param userId The ID of the user associated with the address.
	 * @return Address object 
	 * @throws NotFoundException if Address object is not found for given addressId and userId.
	 * 
	 * @author tushar01
	 */
	Address findByIdAndUserId(long addressId, String userId);
}

