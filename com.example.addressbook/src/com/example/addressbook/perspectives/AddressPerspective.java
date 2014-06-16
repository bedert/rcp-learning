package com.example.addressbook.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.example.addressbook.ids.AddressBook;

public class AddressPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView(AddressBook.VIEW_ID_ADDRESS_LIST, IPageLayout.LEFT,
				0.4f, layout.getEditorArea());
		
		layout.addView(AddressBook.VIEW_ID_ADDRESS, IPageLayout.TOP, 0.5f,
				IPageLayout.ID_EDITOR_AREA);
	}
}
