package com.example.addressbook.views;

import org.eclipse.osgi.util.NLS;

public class AddressViewMessages extends NLS {
	private static final String BUNDLE_NAME = "com.example.addressbook.views.messages"; //$NON-NLS-1$
	public static String AddressView_Country;
	public static String AddressView_Name;
	public static String AddressView_Street;
	public static String AddressView_ZipCity;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, AddressViewMessages.class);
	}

	private AddressViewMessages() {
	}
}
