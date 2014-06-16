package com.example.addressbook.editor;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.example.addressbook.entities.Address;
import com.example.addressbook.entities.Country;
import com.example.addressbook.services.AddressBookServices;

public class AddressEditor extends EditorPart {

	private Text ortText;
	private Text zipText;
	private Text nameText;
	private Text streetText;
	private Combo countryCombo;
	private ComboViewer landComboViewer;
	private boolean dirty;

	public AddressEditor() {

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		int id = this.getEditorInput().getId();
		Address address = AddressBookServices.getAddressService()
				.getAddress(id);

		address.setCity(this.ortText.getText());
		ISelection selection = this.landComboViewer.getSelection();
		if (!selection.isEmpty()) {
			IStructuredSelection iss = (IStructuredSelection) selection;
			Object obj = iss.getFirstElement();
			Country country = (Country) obj;
			address.setCountry(country);
		}
		address.setName(this.nameText.getText());
		address.setStreet(this.streetText.getText());
		address.setZip(this.zipText.getText());

		AddressBookServices.getAddressService().saveAddress(address);
		this.setDirty(false);
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException("SaveAs is not supported."); //$NON-NLS-1$

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		Assert.isTrue(input instanceof AddressEditorInput,
				"Input object must be of type AddressEditorInput!"); //$NON-NLS-1$
	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	private void setDirty(boolean dirty) {
		this.dirty = dirty;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {

		

		parent.setLayout(new GridLayout(6, true));

		Label lblName = new Label(parent, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblName.setText(EditorMessages.AddressEditor_Name);

		nameText = new Text(parent, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				5, 1));

		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});

		Label lblStrasse = new Label(parent, SWT.NONE);
		lblStrasse.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblStrasse.setText(EditorMessages.AddressEditor_Street);

		streetText = new Text(parent, SWT.BORDER);
		streetText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 5, 1));
		streetText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});

		Label lblPlzOrt = new Label(parent, SWT.NONE);
		lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPlzOrt.setText(EditorMessages.AddressEditor_ZipCity);

		zipText = new Text(parent, SWT.BORDER);
		zipText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		zipText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});

		ortText = new Text(parent, SWT.BORDER);
		ortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				4, 1));
		ortText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});

		Label lblLand = new Label(parent, SWT.NONE);
		lblLand.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblLand.setText(EditorMessages.AddressEditor_Country);

		countryCombo = new Combo(parent, SWT.NONE);
		countryCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 5, 1));

		landComboViewer = new ComboViewer(countryCombo);
		landComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		landComboViewer.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				Country country = (Country) element;
				return country.getName();
			}

		});

		landComboViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (!selection.isEmpty()) {
							setDirty(true);
						}
					}
				});

		Collection<Country> countryList = AddressBookServices
				.getAddressService().getAllCountries();
		landComboViewer.setInput(countryList);

		this.loadAddress();
	}

	private void loadAddress() {
		AddressEditorInput input = this.getEditorInput();
		Address address = AddressBookServices.getAddressService().getAddress(
				input.getId());
		
		this.setPartName(address.getName());
		
		this.landComboViewer.setSelection(new StructuredSelection(address
				.getCountry()));
		this.nameText.setText(address.getName());
		this.streetText.setText(address.getStreet());
		this.zipText.setText(address.getZip());
		this.ortText.setText(address.getCity());
		
		this.setDirty(false);
	}

	@Override
	public void setFocus() {
		this.nameText.setFocus();

	}

	@Override
	public AddressEditorInput getEditorInput() {
		return (AddressEditorInput) super.getEditorInput();
	}

}
