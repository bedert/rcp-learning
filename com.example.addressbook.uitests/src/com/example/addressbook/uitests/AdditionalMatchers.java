package com.example.addressbook.uitests;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;

public class AdditionalMatchers {

	public final static AbstractMatcher<? extends Widget> toolTipTextContains(final String text) {
		return new AbstractMatcher<Widget>() {

			@Override
			protected boolean doMatch(Object item) {
				return SWTUtils.getToolTipText(item).toLowerCase().contains(text.toLowerCase());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("contains ").appendValue(text).appendText(" in toolTipText"); //$NON-NLS-1$ //$NON-NLS-2$
			}

		};
	}

}
