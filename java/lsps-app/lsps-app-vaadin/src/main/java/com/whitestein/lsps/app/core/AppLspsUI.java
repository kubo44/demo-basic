package com.whitestein.lsps.app.core;

import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.UI;
import com.whitestein.lsps.human.app.core.LspsUI;
import com.whitestein.lsps.human.app.ui.AppLayout;
import com.whitestein.lsps.human.app.ui.views.TodoListView;
import com.whitestein.lsps.human.app.ui.views.TodoView;
import com.whitestein.lsps.vaadin.util.LspsUIBase;

/**
 * Vaadin UI object for the LSPS Process Application.
 *
 * UI is the topmost component in any component hierarchy. There is one UI 
 * for every Vaadin instance in a browser window. A UI may either represent 
 * an entire browser window (or tab) or some part of a html page where a Vaadin
 * application is embedded. 
 * 
 * See {@link UI} for more details.
 * 
 * Since current ui is always available via {@link UI#getCurrent()}, lsps uses
 * LspsUI implementation to provide commonly needed shared functions to the rest
 * of the application.
 * 
 * IMPORTANT 1: ui provider must return instances of {@link LspsUIBase} class. Lsps engine will
 * not work with anything else.   
 * 
 * IMPORTANT 2:default app assumes that this provider creates {@link LspsUI} instances. You
 * can change it for something else, however then you have to rewrite all views and popups too.
 */
//uncomment following use widgetset without deprecated vaadin 7 features 
//@Widgetset("com.whitestein.lsps.vaadin.widgets.WidgetSet")
public class AppLspsUI extends LspsUI {

	private static final long serialVersionUID = -691702582059076500L;

	/**
	 * Initialize the navigator. Navigator determines which url corresponds to which view. For example, 
	 * default implementation will show {@link TodoView} if url fragment starts with "todo" and it will
	 * show {@link TodoListView} if url fragment starts with "todos".
	 */
	@Override
	protected void createNavigator(ViewDisplay display) {
		super.createNavigator(display);
	}

	/**
	 * App layout decides how the application look like and is the same no matter where in the 
	 * application we are. For example, if there is a menu common for all screens, it should be 
	 * implemented in main layout.  
	 * 
	 * The navigator calls {@link ViewDisplay#showView(com.vaadin.navigator.View)} method implemented
	 * by app layout to show the correct view to the user. 
	 * 
	 * @return application layout component. It also serves as argument for {@link #createNavigator(ViewDisplay)}.
	 */
	@Override
	protected AppLayout createAppLayout() {
		return new AppAppLayout();
	}

}
