package com.whitestein.lsps.app.core;

import javax.servlet.ServletException;

import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;
import com.whitestein.lsps.human.app.tools.AddViewportBootstrapListener;
import com.whitestein.lsps.vaadin.util.TransactionAccessorImpl;
import com.whitestein.lsps.vaadin.util.VaadinServletWithTransaction;

/**
 * Custom Vaadin servlet. An application that uses LSPS forms has either to use
 * {@link VaadinServletWithTransaction} or make sure that a DB transaction is:
 * 1.) present in requests (using e.g. {@link TransactionAccessorImpl})
 * 2.) committed after each request.
 * 
 */
public class AppServlet extends VaadinServletWithTransaction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();

		//register a bootstrap listener that adds a meta header to the bootstrap page
		getService().addSessionInitListener(new SessionInitListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void sessionInit(SessionInitEvent event) {
				event.getSession().addBootstrapListener(new AddViewportBootstrapListener());
				event.getSession().addUIProvider(getUIProvider());
			}
		});
	}

	/**
	 * @return ui provider decides which {@link UI} class/instance is going to be used. There is one UI 
	 * for every Vaadin instance in a browser window. It represents topmost component in Vaadin component 
	 * hierarchy and is responsible for rendering whole application. 
	 * 
	 * UI provider is first entry point for all customizations. 
	 * 
	 */
	protected UIProvider getUIProvider() {
		return new AppUIProvider();
	}

}
