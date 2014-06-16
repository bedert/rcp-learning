package com.example.experiments.filebrowser.views;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class FileTreeLabelProvider extends LabelProvider{

	@Override
	public Image getImage(Object element) {
		if(element instanceof File){
			File theFile = (File)element;
			if(theFile.isDirectory()){
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			}else{
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
			}
			
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if(element instanceof File){
			File theFile = (File)element;
			if(theFile.isDirectory()){
				return theFile.getName() != null && theFile.getName().length() > 0 ? theFile.getName() : theFile.getPath();
			}else{
				return theFile.getName();
			}
		}
		return super.getText(element);
	}
}
