package com.whitestein.lsps.app.connectorsv7;

import com.whitestein.lsps.app.connectors.LspsUIComponentFactory;
import com.whitestein.lsps.vaadin.LspsAppConnector;
import com.whitestein.lsps.vaadin.ui.UIComponentFactoryImpl;

/**
 * Modify this class if you wish to provide additional components to ui module (e.g. the old ui).
 * 
 * This implementation references uses deprecated vaadin 7 features and will be removed. Use {@link LspsUIComponentFactory} instead.
 */
@Deprecated
public class LspsUIComponentFactoryV7 extends UIComponentFactoryImpl {

	private static final long serialVersionUID = 6615605610534391152L;

	/**
	 * Creates ui component factory.
	 * 
	 * @param connector
	 * @throws NullPointerException
	 */
	public LspsUIComponentFactoryV7(LspsAppConnector connector) {
		super(connector);
	}

	// Uncomment and modify accordingly, to add support for your custom components.
	// To add a new component, please follow these steps:
	// 1. In your model, create a Record which extends the ui::UIComponent record. Add any additional fields as applicable.
	// 2. Create Java class which implements the UIComponent interface and extends desired Vaadin Component.
	// 3. Implement the skeletal methods. fireUIEvent() and getComponentData() implementation is straightforward,
	//    in the refresh() method just read all fields from the definition record and apply them to the component.
	// Please refer to the online documentation for more information.
	/*
	@Override
	protected UIComponent createComponent(UIComponentData componentData) {
	    final String type = componentData.getDefinition().getTypeFullName();
	    if (type.equals("mymodule::MyComponentDefinitionRecord")) {
	        return new MyComponent(componentData);
	    }
	    return super.createComponent(componentData);
	}
	*/
}
