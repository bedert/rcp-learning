package com.example.addressbook.views;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;

import com.example.addressbook.entities.Address;
import com.example.addressbook.services.AddressBookServices;
import com.example.addressbook.services.IAddressChangeListener;
import com.example.addressbook.services.IAddressService;

public class AddressList extends ViewPart {

	private TableViewer tableViewer;
	private Table table;

	public AddressList() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableViewer = new TableViewer(parent, SWT.MULTI);

		this.getSite().setSelectionProvider(tableViewer);

		table = tableViewer.getTable();

		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn viewerNameColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		viewerNameColumn.getColumn().setWidth(300);

		viewerNameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address address = (Address) element;
				return address.getName();
			}

		});

		MenuManager menuManager = new MenuManager();
		table.setMenu(menuManager.createContextMenu(tableViewer.getTable()));
		this.getSite().registerContextMenu(menuManager, tableViewer);
		this.getSite().setSelectionProvider(tableViewer);

		AddressBookServices.getAddressService().addAddressChangeListener(
				addressChangeListener);

		refresh();
	}

	@Override
	public void dispose() {
		AddressBookServices.getAddressService().removeAddressChangeListener(
				addressChangeListener);
		super.dispose();
	}

	@Override
	public void setFocus() {
		this.table.setFocus();
	}

	private void setAddressList(final List<Address> allAddresses) {

		UIJob addressesLoadedJob = new UIJob("Addresses loaded") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				tableViewer.setInput(allAddresses);
				return Status.OK_STATUS;
			}

		};

		addressesLoadedJob.schedule();

	}

	public void refresh() {

		Job loadAddressesJob = new Job("Load Addresses") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IAddressService service = AddressBookServices
						.getAddressService();
				List<Address> allAddresses = service.getAllAddresses();
				setAddressList(allAddresses);
				return Status.OK_STATUS;
			}

		};

		loadAddressesJob.schedule();

	}

	private IAddressChangeListener addressChangeListener = new IAddressChangeListener() {

		@Override
		public void addressesChanged() {
			refresh();
		}

	};

}
