package com.example.addressbook.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class AddressEditorInput implements IEditorInput {

	private int id;
	
	public AddressEditorInput(int id){
		super();
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)return true;
		if(obj == null || getClass() != obj.getClass())return false;
		if(this.id == ((AddressEditorInput)obj).getId()){
			return true;
		}
			
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	public int getId(){
		return this.id;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Adresse " + id;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return getName();
	}

}
