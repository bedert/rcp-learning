package com.example.addressbook.editor;

import org.eclipse.osgi.util.NLS;

public class EditorMessages extends NLS {
	private static final String BUNDLE_NAME = "com.example.addressbook.editor.messages"; //$NON-NLS-1$
	public static String AddressEditor_Country;
	public static String AddressEditor_Name;
	public static String AddressEditor_Street;
	public static String AddressEditor_ZipCity;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, EditorMessages.class);
	}

	private EditorMessages() {
	}
}
