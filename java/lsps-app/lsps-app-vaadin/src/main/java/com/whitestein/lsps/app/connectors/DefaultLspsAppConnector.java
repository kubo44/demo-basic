package com.whitestein.lsps.app.connectors;

import com.whitestein.lsps.human.app.core.LspsAppConnectorImplV8;
import com.whitestein.lsps.human.app.core.LspsUI;
import com.whitestein.lsps.vaadin.LspsAppConnector;
import com.whitestein.lsps.vaadin.forms.FormComponentFactoryV8;
import com.whitestein.lsps.vaadin.ui.UIComponentFactory;

/**
 * Default implementation of application connector {@link LspsAppConnector}.
 */
public class DefaultLspsAppConnector extends LspsAppConnectorImplV8 {

	private static final long serialVersionUID = 6600639435905976895L;

	/**
	 * Full constructor.
	 * @param ui not null
	 */
	public DefaultLspsAppConnector(LspsUI ui) {
		super(ui);
	}

	@Override
	public UIComponentFactory getComponentFactory() {
		return new LspsUIComponentFactory(this);
	}

	@Override
	public FormComponentFactoryV8 getFormsComponentFactory() {
		return new LspsFormComponentFactory();
	}

}
