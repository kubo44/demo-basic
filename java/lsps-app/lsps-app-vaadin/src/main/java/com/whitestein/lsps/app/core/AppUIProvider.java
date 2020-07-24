package com.whitestein.lsps.app.core;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;
import com.whitestein.lsps.app.connectorsv7.DefaultLspsAppConnectorV7;
import com.whitestein.lsps.human.app.core.LspsUI;
import com.whitestein.lsps.human.app.core.LspsUIProvider;
import com.whitestein.lsps.vaadin.LspsAppConnector;
import com.whitestein.lsps.vaadin.util.LspsUIBase;

/**
 * Default implementation of {@link UIProvider}. If you wish to use different one, customize it
 * in vaadin servlet - default implementation provided by lsps is {@link AppServlet}.
 * 
 * - If the current user has not been authenticated, returns login page. 
 * - If the user was authenticated (for example by single sign on), but does not have the right to
 *   use lsps app, returns login failed page {@link  #getLoginFailedUIClass()}.
 *   
 * - If the user is logged in and has rights, returns lsps ui page {@link #getLspsUIClass()}
 * 
 * IMPORTANT 1: ui provider must return instances of {@link LspsUIBase} class. Lsps engine will
 * not work with anything else.   
 * 
 * IMPORTANT 2:default app assumes that this provider creates {@link LspsUI} instances. You
 * can change it for something else, however then you have to rewrite all views and popups too.
 * 
 * TYPICAL CUSTOMIZATIONS:
 * - Change {@link  #getLspsUIClass()} topmost component for lsps itself. If you want to change anything 
 *   in default app you will likely have to change to do this too. Most importantly, it defines navigation, 
 *   app layout etc. See {@link LspsUI} for more details.
 * 
 * - Change {@link #getThemeManager()} if you wish to change default theme, list of supported themes or anything
 *   else related to themes handling.
 *   
 * - Change {@link  #getLoginUIClass()} if you want to change login page look or functionality.
 * - Change {@link  #getLoginFailedUIClass()} if you want to change login failed page look or functionality.
 * - Change {@link #getUIClass(UIClassSelectionEvent event)} decision logic on which ui class should be 
 *   used. 
 */
public class AppUIProvider extends LspsUIProvider {

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends LspsUI> getLspsUIClass() {
		return AppLspsUI.class;
	}

	/**
	 * NOTE: must return {@link LspsUIBase} instance. 
	 */
	@Override
	public UI createInstance(UICreateEvent event) {
		// this creates instance of whatever was returned by getUIClass
		UI result = super.createInstance(event);

		if (result instanceof LspsUI) {
			LspsUI lspsUI = (LspsUI) result;
			lspsUI.setThemeManager(getThemeManager());
			lspsUI.setAppConnector(createLspsAppConnector(lspsUI));
		}

		return result;
	}

	private LspsAppConnector createLspsAppConnector(LspsUI lspsUI) {
		/**
		 * Uncomment to create application that does not use deprecated vaadin 7 features.
		 */
		//return new DefaultLspsAppConnector(lspsUI);
		return new DefaultLspsAppConnectorV7(lspsUI);
	}

}
