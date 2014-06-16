package com.example.experiments.filebrowser.fileutil;

import java.io.File;

public class FileSystemBrowser {
	
	public File[] getRoots(){
		return File.listRoots();
	}
	
	public File[] getDirectoryContent(File directory){
		return (directory == null || !directory.isDirectory()) ? new File[]{} : directory.listFiles();
	}
	
	public File getParent(File file){
		return (file == null) ? null : file.getParentFile();
	}
	
	public boolean hasChildren(File file){
		return (file == null) ? null : (file.isDirectory() && file.list() != null && file.list().length > 0);
	}
}
