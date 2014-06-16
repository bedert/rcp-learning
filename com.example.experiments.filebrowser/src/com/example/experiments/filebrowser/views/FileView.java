package com.example.experiments.filebrowser.views;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.example.experiments.filebrowser.fileutil.FileSystemBrowser;

public class FileView extends ViewPart {

	private Tree tree;

	public FileView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		fillLayout.marginHeight = 5;
		fillLayout.marginWidth = 5;
		parent.setLayout(fillLayout);
		
		tree = new Tree(parent, SWT.VIRTUAL | SWT.BORDER);
		TreeViewer treeViewer = new TreeViewer(tree);
		
		FileSystemBrowser fileBrowser = new FileSystemBrowser();
		treeViewer.setContentProvider(new FileTreeContentProvider(fileBrowser));
		treeViewer.setLabelProvider(new FileTreeLabelProvider());
		File[] roots = fileBrowser.getRoots();
		treeViewer.setInput(roots);
		treeViewer.setExpandedElements(roots);
				
	}

	@Override
	public void setFocus() {
		tree.setFocus();
	}
}
