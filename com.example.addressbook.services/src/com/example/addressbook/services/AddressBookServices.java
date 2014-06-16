package com.example.addressbook.services;

import com.example.addressbook.services.internal.RandomDataAddressService;

/**
 * AddressBookServices provides an IAddressService implementation as singleton.
 * For practice purposes only, for real applications consider using OSGi
 * Services / Dependency Injection instead of a Singleton class to provide
 * client-side services.
 * 
 * <code>
 * IAddressService addressService = AddressBookServices.getAddressService();
 * </code>
 * 
 * @author Ralf Ebert
 */
public class AddressBookServices {

	private static IAddressService addressService = new RandomDataAddressService();

	public static IAddressService getAddressService() {
		return addressService;
	}

}