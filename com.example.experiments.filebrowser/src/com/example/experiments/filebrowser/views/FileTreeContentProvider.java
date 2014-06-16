package com.example.experiments.filebrowser.views;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.example.experiments.filebrowser.fileutil.FileSystemBrowser;

public class FileTreeContentProvider implements ITreeContentProvider {
	
	FileSystemBrowser browser = null;

	public FileTreeContentProvider(FileSystemBrowser fileBrowser) {
		this.browser = fileBrowser;
	}

	@Override
	public void dispose() {
		System.out.println("Disposing content provider!");

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		System.out.println("Input changed!");

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof File[]){
			return ((File[])inputElement);
		}
		
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof File){
			return browser.getDirectoryContent((File)parentElement);
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof File) {
			return browser.getParent((File) element);
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof File){
			return browser.hasChildren((File)element);
		}
		return false;
	}

}
