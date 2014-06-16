package com.example.addressbook.maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class MapView extends ViewPart {

	private Browser browser;

	public MapView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		browser = new Browser(parent, SWT.NONE);
		try {
			browser.setUrl("http://maps.google.de/maps?q=" +
					URLEncoder.encode("Hamburg", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}

}
