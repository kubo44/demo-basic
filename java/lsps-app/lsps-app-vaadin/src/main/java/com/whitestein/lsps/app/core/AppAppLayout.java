package com.whitestein.lsps.app.core;

import com.vaadin.ui.Component;
import com.whitestein.lsps.human.app.ui.components.DefaultAppLayout;
import com.whitestein.lsps.human.app.ui.components.DefaultMainMenu;

/**
 * Stub application layout ready to be customized. See {@link DefaultAppLayout} for details.
 */
public class AppAppLayout extends DefaultAppLayout {

	private static final long serialVersionUID = -4351448050819219347L;

	/**
	 * Change to customize main application menu.
	 * 
	 * @return main menu component
	 */
	@Override
	protected Component createMainMenu() {
		return new DefaultMainMenu(CONTENT_AREA_ID);
	}

}
