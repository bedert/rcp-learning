package com.example.experiments.filebrowser.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.example.experiments.filebrowser.ids.FileBrowser;

public class FilePerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.addView(FileBrowser.VIEW_ID_FILEVIEW, IPageLayout.LEFT,
				0.4f, layout.getEditorArea());

	}

}
