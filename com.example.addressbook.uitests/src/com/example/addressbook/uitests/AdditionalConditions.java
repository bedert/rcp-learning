package com.example.addressbook.uitests;

import java.util.Arrays;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;

public class AdditionalConditions {

	public static final ICondition textEquals(final AbstractSWTBot<?> widget,
			final String expectedLabel) {
		return new DefaultCondition() {

			@Override
			public boolean test() throws Exception {
				return expectedLabel.equals(widget.getText());
			}

			@Override
			public String getFailureMessage() {
				return "Expected Text \"" + expectedLabel + "\"";
			}
		};
	}

	public static final ICondition tableHasRows(final SWTBotTable table, final String... items) {
		return new DefaultCondition() {

			@Override
			public String getFailureMessage() {
				return "rows are not in table: " + Arrays.toString(items);
			}

			@Override
			public boolean test() throws Exception {
				if (table.rowCount() == 0)
					return false;

				for (String item : items) {
					if (!table.containsItem(item)) {
						return false;
					}
				}

				return true;
			}
		};
	}

	public static final ICondition tableMissesRows(final SWTBotTable table, final String... items) {
		return new DefaultCondition() {

			@Override
			public String getFailureMessage() {
				return "rows are still in table: " + Arrays.toString(items);
			}

			@Override
			public boolean test() throws Exception {
				if (table.rowCount() == 0)
					return true;

				for (String item : items) {
					if (table.containsItem(item)) {
						return false;
					}
				}

				return true;
			}
		};
	}

}
