package com.example.addressbook.services;

import java.util.Collection;
import java.util.List;

import com.example.addressbook.entities.Address;
import com.example.addressbook.entities.Country;

/**
 * IAddressService is a simple data providing service which maintains a list of
 * {@link Address} objects.
 * 
 * @author Ralf Ebert
 */
public interface IAddressService {

	/**
	 * Returns a List<Address> of all known addresses.
	 */
	public List<Address> getAllAddresses();

	/**
	 * Returns a Address by its primary key id.
	 */
	public Address getAddress(int id);

	/**
	 * Saves a changed address. New addresses created with new Address() are
	 * created automatically and returned with a primary key.
	 */
	public Address saveAddress(Address changedOrNewAddress);

	/**
	 * Deletes the address with matching primary key id.
	 */
	public void deleteAddress(int id);

	/**
	 * Returns a list of all known countries as List<Country>.
	 */
	public Collection<Country> getAllCountries();

	/**
	 * Returns a list of all known cities as String[].
	 */
	public String[] getAllCities();

	/**
	 * Adds an IAddressChangeListener which will be notified whenever addresses
	 * are changed, added or removed.
	 */
	public void addAddressChangeListener(IAddressChangeListener listener);

	/**
	 * Removes an IAddressChangeListener.
	 */
	public void removeAddressChangeListener(IAddressChangeListener listener);

}