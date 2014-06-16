package com.example.addressbook.editor.handler;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.example.addressbook.editor.AddressEditorInput;
import com.example.addressbook.editor.ids.EditorIds;
import com.example.addressbook.entities.Address;



public class OpenAddressHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(!(selection instanceof IStructuredSelection)){
			return null;
		}
		
		IStructuredSelection structuredSelection = (IStructuredSelection)selection;
		
		Iterator<?> iter = structuredSelection.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof Address){
				IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
				try {
					// TODO: check if there is open editor for this address!
					
					
					page.openEditor(new AddressEditorInput(((Address)obj).getId().intValue()), EditorIds.ADDRESS_EDITOR_ID);
				} catch (PartInitException e) {
					throw new ExecutionException("Could not open address editor!", e);
				}
				
			}
		}
		
		
		return null;
	}

}
