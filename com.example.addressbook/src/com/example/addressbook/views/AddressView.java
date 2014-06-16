package com.example.addressbook.views;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.example.addressbook.entities.Address;
import com.example.addressbook.entities.Country;
import com.example.addressbook.ids.WidgetIdHelper;
import com.example.addressbook.services.AddressBookServices;
import org.eclipse.core.databinding.UpdateValueStrategy;

public class AddressView extends ViewPart {
	@SuppressWarnings("unused") // generated
	private DataBindingContext m_bindingContext;
	
	
	// Widget Key
	public final static String WIDGET_ID_KEY = "com.example.addressbook.views.widget.key";
	// Widget Ids
	public static final String CITY_TEXT_ID = WidgetIdHelper.ID_VIEWS_PACKAGE_PREFIX + ".cityText";
	public static final String ZIP_TEXT_ID = WidgetIdHelper.ID_VIEWS_PACKAGE_PREFIX + ".zipText";
	public static final String NAME_TEXT_ID = WidgetIdHelper.ID_VIEWS_PACKAGE_PREFIX + ".nameText";
	public static final String STREET_TEXT_ID = WidgetIdHelper.ID_VIEWS_PACKAGE_PREFIX + ".streetTextId";
	public static final String COUNTRY_COMBO_ID = WidgetIdHelper.ID_VIEWS_PACKAGE_PREFIX + ".countryComboId";
	
	
	private Text ortText;
	private Text zipText;
	private Text nameText;
	private Text streetText;
	private Combo countryCombo;
	private ComboViewer landComboViewer;

	private WritableValue currentAddress = new WritableValue();

	public AddressView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(6, true));

		Label lblName = new Label(parent, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblName.setText(AddressViewMessages.AddressView_Name);

		nameText = new Text(parent, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				5, 1));
		nameText.setData(AddressView.WIDGET_ID_KEY, AddressView.NAME_TEXT_ID);

		Label lblStrasse = new Label(parent, SWT.NONE);
		lblStrasse.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblStrasse.setText(AddressViewMessages.AddressView_Street);

		streetText = new Text(parent, SWT.BORDER);
		streetText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 5, 1));
		streetText.setData(AddressView.WIDGET_ID_KEY, AddressView.STREET_TEXT_ID);

		Label lblPlzOrt = new Label(parent, SWT.NONE);
		lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPlzOrt.setText(AddressViewMessages.AddressView_ZipCity);

		zipText = new Text(parent, SWT.BORDER);
		zipText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		zipText.setData(AddressView.WIDGET_ID_KEY, AddressView.ZIP_TEXT_ID);

		ortText = new Text(parent, SWT.BORDER);
		ortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				4, 1));
		ortText.setData(AddressView.WIDGET_ID_KEY, AddressView.CITY_TEXT_ID);
		

		Label lblLand = new Label(parent, SWT.NONE);
		lblLand.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblLand.setText(AddressViewMessages.AddressView_Country);

		countryCombo = new Combo(parent, SWT.NONE);
		countryCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 5, 1));
		countryCombo.setData(AddressView.WIDGET_ID_KEY, AddressView.COUNTRY_COMBO_ID);

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
							IStructuredSelection structuredSelection = (IStructuredSelection) selection;
							Country selectedCountry = (Country) structuredSelection
									.getFirstElement();
							String countryName = selectedCountry.getName();
							System.out
									.println("selected country = " + countryName); //$NON-NLS-1$
						}
					}

				});

		Collection<Country> countryList = AddressBookServices
				.getAddressService().getAllCountries();
		
		landComboViewer.setInput(countryList);

		// selection service listener: listen for selections in the address list
		// view
		ISelectionService selectionService = this.getSite()
				.getWorkbenchWindow().getSelectionService();
		selectionService.addSelectionListener(new ISelectionListener() {

			@Override
			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {
				if (!(selection instanceof IStructuredSelection)) {
					return;
				}
				Iterator<?> iterator = ((IStructuredSelection) selection)
						.iterator();
				while (iterator.hasNext()) {
					Object object = (Object) iterator.next();
					if (object instanceof Address) {
						setAddress((Address) object);
					}
				}
			}
		});
		m_bindingContext = initDataBindings();

	}

	@Override
	public void setFocus() {
		nameText.forceFocus();

	}

	private void setAddress(Address address) {

		this.currentAddress.setValue(address);
		// this.ortText.setText(address.getCity());
		// this.nameText.setText(address.getName());
		// this.streetText.setText(address.getStreet());
		// this.zipText.setText(address.getZip());
		// this.landComboViewer.setSelection(new StructuredSelection(address
		// .getCountry()));
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextNameTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(nameText);
		IObservableValue currentAddressNameObserveDetailValue = PojoProperties.value(Address.class, "name", String.class).observeDetail(currentAddress);
		bindingContext.bindValue(observeTextNameTextObserveWidget, currentAddressNameObserveDetailValue, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
		//
		IObservableValue observeTextStreetTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(streetText);
		IObservableValue currentAddressStreetObserveDetailValue = PojoProperties.value(Address.class, "street", String.class).observeDetail(currentAddress);
		bindingContext.bindValue(observeTextStreetTextObserveWidget, currentAddressStreetObserveDetailValue, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
		//
		IObservableValue observeTextZipTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(zipText);
		IObservableValue currentAddressZipObserveDetailValue = PojoProperties.value(Address.class, "zip", String.class).observeDetail(currentAddress);
		bindingContext.bindValue(observeTextZipTextObserveWidget, currentAddressZipObserveDetailValue, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
		//
		IObservableValue observeTextOrtTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(ortText);
		IObservableValue currentAddressCityObserveDetailValue = PojoProperties.value(Address.class, "city", String.class).observeDetail(currentAddress);
		bindingContext.bindValue(observeTextOrtTextObserveWidget, currentAddressCityObserveDetailValue, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
		//
		IObservableValue observeSingleSelectionLandComboViewer = ViewerProperties.singleSelection().observe(landComboViewer);
		IObservableValue currentAddressCountryObserveDetailValue = PojoProperties.value(Address.class, "country", Country.class).observeDetail(currentAddress);
		bindingContext.bindValue(observeSingleSelectionLandComboViewer, currentAddressCountryObserveDetailValue, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
		//
		return bindingContext;
	}
}
