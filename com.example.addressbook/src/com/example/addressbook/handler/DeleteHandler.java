package com.example.addressbook.handler;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.example.addressbook.entities.Address;
import com.example.addressbook.services.AddressBookServices;



public class DeleteHandler extends AbstractHandler  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		if(!(selection instanceof IStructuredSelection)){
			return null;
		}
		
		
		for(Iterator<?> it = ((IStructuredSelection)selection).iterator(); it.hasNext();){
			Object ob = it.next();
			
			if(ob instanceof Address){
				AddressBookServices.getAddressService().deleteAddress(((Address)ob).getId());
			}
		}
		
		return null;
	}

}
