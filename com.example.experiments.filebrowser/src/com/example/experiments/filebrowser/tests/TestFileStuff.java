package com.example.experiments.filebrowser.tests;

import java.io.File;

import org.junit.Test;

public class TestFileStuff {
	
	@Test
	public void testFileList(){
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			System.out.println(roots[i].getAbsolutePath());
		}
	}
	
	@Test
	public void testListContent(){
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			File root = roots[i];
			if(root.isDirectory()){
				File files[] = root.listFiles();
				for (int j = 0; j < files.length; j++) {
					System.out.println(files[j].getAbsolutePath());
				}
			}else{
					System.out.println(root.getAbsolutePath());
			}
			
		}
	}

}
