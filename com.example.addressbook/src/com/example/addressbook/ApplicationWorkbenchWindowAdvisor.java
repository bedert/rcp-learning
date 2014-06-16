package com.example.addressbook;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public static final String ID_ADDRESSBOOK_WORKBENCH = "com.example.addressbook.workbench.id";
	public static final String KEY_ADDRESSBOOK_WORKBENCH = "com.example.addressbook.workbench.key";

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setTitle("Adressbuch"); //$NON-NLS-1$
        configurer.setData(KEY_ADDRESSBOOK_WORKBENCH, ID_ADDRESSBOOK_WORKBENCH);
        
        configurer.setShowPerspectiveBar(true);
        configurer.setShowProgressIndicator(true);
    }
}
