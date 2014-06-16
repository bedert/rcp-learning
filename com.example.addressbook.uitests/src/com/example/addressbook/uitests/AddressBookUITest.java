package com.example.addressbook.uitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.example.addressbook.editor.AddressEditorInput;
import com.example.addressbook.editor.ids.EditorIds;
import com.example.addressbook.entities.Address;
import com.example.addressbook.ids.AddressBook;
import com.example.addressbook.services.AddressBookServices;
import com.example.addressbook.services.IAddressService;
import com.example.addressbook.views.AddressView;

public class AddressBookUITest {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	@Before
	public void setUp() {
		bot.resetWorkbench();
	}

	@Test
	public void testAddressBookWindow() {
		//assertNotNull("Adressbuch-Fenster", bot.shellWithId(ApplicationWorkbenchWindowAdvisor.KEY_ADDRESSBOOK_WORKBENCH, ApplicationWorkbenchWindowAdvisor.ID_ADDRESSBOOK_WORKBENCH));
		assertNotNull("Adressbuch-Fenster", bot.shell("Adressbuch")); // TODO get this via id
		assertNotNull("PerspectiveBar not visisble", bot.toolbarButtonWithTooltip("Open Perspective")); // TODO get this button via id
		assertEquals("Adressen", bot.activePerspective().getLabel());
		assertTrue(bot.perspectiveById(AddressBook.PERSPECTIVE_ID_ADDRESSES).isActive());
	}

	private SWTBotView viewInPerspective(String perspectiveId, String viewId) {
		bot.perspectiveById(perspectiveId).activate();
		SWTBotView view = bot.viewById(viewId);
		view.show();
		return view;
	}

//	@Test
//	public void testAddressListButton() {
//		SWTBotView view = addressListView();
//		assertEquals("Adressen", view.getTitle());
//		SWTBot viewBot = view.bot();
//		
//		viewBot.button("Adressen laden").click();
//		viewBot.label("50 Adressen geladen");
//	}
	
	private SWTBotView addressListView() {
		return viewInPerspective(AddressBook.PERSPECTIVE_ID_ADDRESSES, AddressBook.VIEW_ID_ADDRESS_LIST);
	}

	@Test
	public void testAddressView() {
		SWTBotView view = addressView();
		assertEquals("Adresse", view.getTitle());
		SWTBot viewBot = view.bot();
		
		assertNotNull(viewBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.NAME_TEXT_ID));
		assertTrue(viewBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.NAME_TEXT_ID).isActive());
		assertNotNull(viewBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.STREET_TEXT_ID));
		assertNotNull(viewBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.ZIP_TEXT_ID));
		assertNotNull(viewBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.CITY_TEXT_ID));
		assertNotNull(viewBot.comboBoxWithId(AddressView.WIDGET_ID_KEY, AddressView.COUNTRY_COMBO_ID));
	}

	private SWTBotView addressView() {
		return viewInPerspective(AddressBook.PERSPECTIVE_ID_ADDRESSES, AddressBook.VIEW_ID_ADDRESS);
	}

	
	
	// Adressen im Hintergrund laden
	
//	@Test
//	public void testLoadAddressesJob() {
//		SWTBotView view = addressListView();
//		assertEquals("Adressen", view.getTitle());
//		SWTBot viewBot = view.bot();
//		SWTBotButton loadButton = viewBot.button("Adressen laden");
//		final SWTBotLabel countLabel = viewBot.label();
//
//		loadButton.click();
//		bot.waitUntil(AdditionalConditions.textEquals(countLabel, "50 Adressen geladen"));
//
//		AddressBookServices.getAddressService().deleteAddress(5);
//		StopWatch watch = new StopWatch("UI response time");
//		loadButton.click();
//		watch.assertDurationLessThanMs(100);
//		bot.waitUntil(AdditionalConditions.textEquals(countLabel, "49 Adressen geladen"));
//	}
	
	
	
	// Länderauswahl
	
	@Test
	public void testCountryList() {
		SWTBot addressView = addressView().bot();
		SWTBotCombo combo = addressView.comboBoxWithId(AddressView.WIDGET_ID_KEY, AddressView.COUNTRY_COMBO_ID);
		combo.setSelection("Deutschland");
	}
	
	
	// Adresstabelle

	@Test
	public void testAddressTable() {
		SWTBotTable addressTable = addressListTable();
		assertTrue(addressTable.containsItem("Gudrun Hartmann"));

		SortInspector sortInspector = new SortInspector();
		for (int i = 0; i < addressTable.rowCount(); i++) {
				sortInspector.next(addressTable.getTableItem(i).getText());
		}
	}

	private SWTBotTable addressListTable() {
		SWTBotTable table = addressListView().bot().table();
		bot.waitUntil(AdditionalConditions.tableHasRows(table));
		return table;
	}

	
	// Adressliste und Adressanzeige koppeln

	@Test
	public void testSelectionMasterDetail() {
		addressListTable().select("Gudrun Hartmann");
		assertGudrun(addressView().bot());

		addressListTable().select("Elke Neumann");
		assertEquals("Elke Neumann", addressView().bot().textWithId(AddressView.WIDGET_ID_KEY, AddressView.NAME_TEXT_ID).getText());
	}

	private void assertGudrun(SWTBot detailBot) {
		assertEquals("Gudrun Hartmann", detailBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.NAME_TEXT_ID).getText());
		assertEquals("Hartmannstraße 24", detailBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.STREET_TEXT_ID).getText());
		assertEquals("78378", detailBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.ZIP_TEXT_ID).getText());
		assertEquals("Gelsenkirchen", detailBot.textWithId(AddressView.WIDGET_ID_KEY, AddressView.CITY_TEXT_ID).getText());
		assertEquals("Deutschland", detailBot.comboBoxWithId(AddressView.WIDGET_ID_KEY, AddressView.COUNTRY_COMBO_ID).getText());
	}
	
	
	// Menü und Toolbar befüllen

	@Test
	public void testMenu() {
		
		SWTBotMenu file = bot.menu("File");
		file.menu("Save");
		file.menu("Save All");
		// ---
		file.menu("Close");
		file.menu("Close All");
		// ---
		file.menu("Refresh");
		// ---
		file.menu("Exit");

		SWTBotMenu edit = bot.menu("Edit");
		edit.menu("Undo");
		edit.menu("Redo");
		// ---
		edit.menu("Cut");
		edit.menu("Copy");
		edit.menu("Paste");
		// ---
		edit.menu("Delete");
	}
	
	@Test
	public void testToolbar() throws Exception {
		assertNotNull(toolbarButtonTooltipTextContains("Save"));
	}
	
	

	@SuppressWarnings("unchecked")
	private SWTBotToolbarPushButton toolbarButtonTooltipTextContains(String string) {
		Matcher<Widget> matcher = Matchers.allOf(WidgetMatcherFactory.widgetOfType(ToolItem.class),
						AdditionalMatchers.toolTipTextContains(string));
		return new SWTBotToolbarPushButton((ToolItem) bot.widget(matcher, 0), matcher);
	}
	
	

	// Adressliste aktualisieren
	
	@Test
	public void testAddressListRefresh() {
		IAddressService addressService = AddressBookServices.getAddressService();
		Address address = addressService.getAddress(1);
		address.setName("Joe Name");
		addressService.saveAddress(address);

		addressListView().show();
		bot.menu("File").menu("Refresh").click();

		bot.waitUntil(AdditionalConditions.tableHasRows(addressListTable(), "Joe Name"));
	}
	
	

	// Refresh-Command kontextabhängig aktivieren

	@Test
	public void testRefreshEnabledOnlyForAddressListView() throws Exception {
		addressListView().show();
		assertTrue(bot.menu("File").menu("Refresh").isEnabled());

		addressView().show();
		assertFalse(bot.menu("File").menu("Refresh").isEnabled());
	}
	
	
	
	// Adresse löschen im Kontextmenü
	
	public void testDeleteAddress() throws Exception {
		SWTBotTable addressTable = addressListTable();
		assertTrue(addressTable.containsItem("Carl Hoffmann"));
		assertTrue(addressTable.containsItem("Jutta Herrmann"));
		addressListTable().select("Carl Hoffmann", "Jutta Herrmann");
		bot.menu("Edit").menu("Delete").click();
		bot.waitUntil(AdditionalConditions.tableMissesRows(addressListTable(), "Carl Hoffmann", "Jutta Herrmann"));
	}

	
	
	// Adresseditoren
	
	@Test
	public void testAddressEditor() throws Exception {

		SWTBotTable table = addressListTable();
		table.select("Elke Neumann", "Gudrun Hartmann");
		SWTBotMenu openAddress = table.contextMenu("Open Address");

		openAddress.click();
		assertEquals(2, bot.editors().size());

		SWTBotEditor editor = bot.editorByTitle("Gudrun Hartmann");
		editor.show();
		assertGudrun(editor.bot());

		assertFalse(editor.isDirty());
		String changedName = "Joe Changed";
		editor.bot().textWithLabel("Name:").setText(changedName);
		assertTrue(editor.isDirty());
		editor.save();
		assertEquals(changedName, AddressBookServices.getAddressService().getAddress(23).getName());
		assertFalse(editor.isDirty());
	}

	@Test
	public void testEditorsUnique() throws Exception {
		for (int i = 1; i <= 2; i++) {
			openEditor(new AddressEditorInput(23), EditorIds.ADDRESS_EDITOR_ID);
			assertEquals(1, bot.editors().size());
		}
	}
	
	

	private void openEditor(final IEditorInput input, final String editorId) {
		UIThreadRunnable.asyncExec(new VoidResult() {

			@Override
			public void run() {
				IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					activePage.openEditor(input, editorId);
				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
			}

		});
	}
	
	
}