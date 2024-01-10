package com.watchhub.watchstore.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.watchhub.watchstore.dao.DeliveryAddressDao;
import com.watchhub.watchstore.entity.DeliveryAddress;
import com.watchhub.watchstore.service.DeliveryAddressService;
/**
 * 
 * Implementation of the DeliveryAddress interface that provides methods for managing delivery addresses.
 *
 * @author tushar01
 */
@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

	private final DeliveryAddressDao deliveryAddressDao;
	
	public DeliveryAddressServiceImpl(final DeliveryAddressDao deliveryAddressDao)
	{
		this.deliveryAddressDao=deliveryAddressDao;
	}
	
	@Override
	public Optional<DeliveryAddress> findByAddressId(long id) {
		return this.deliveryAddressDao.findById(id);
	}

	@Override
	public DeliveryAddress save(DeliveryAddress deliveryAddress) {
		return this.deliveryAddressDao.save(deliveryAddress);
	}

}
