package com.example.addressbook.maps.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.example.addressbook.maps.Activator;

public class MapProviderPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public static final String MAP_SELECTION_OPTION = "MapSelection";

	public MapProviderPreferencesPage() {
		super(FieldEditorPreferencePage.GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new RadioGroupFieldEditor(MAP_SELECTION_OPTION, "Map Provider:", 1,
				new String[][] { { "Google Maps", "google" },
						{ "Bing", "bing" },
						{ "OpenStreetMap", "openstreetmap" } },
				this.getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

}
