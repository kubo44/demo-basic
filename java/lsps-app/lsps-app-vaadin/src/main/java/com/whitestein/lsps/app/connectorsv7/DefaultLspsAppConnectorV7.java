package com.whitestein.lsps.app.connectorsv7;

import com.whitestein.lsps.app.connectors.DefaultLspsAppConnector;
import com.whitestein.lsps.human.app.core.LspsAppConnectorImpl;
import com.whitestein.lsps.human.app.core.LspsUI;
import com.whitestein.lsps.vaadin.LspsAppConnector;
import com.whitestein.lsps.vaadin.forms.FormComponentFactoryV8;
import com.whitestein.lsps.vaadin.ui.UIComponentFactory;

/**
 * Default implementation of application connector {@link LspsAppConnector}. This implementation references uses deprecated 
 * vaadin 7 features and will be removed. Use {@link DefaultLspsAppConnector} instead.
 */
@Deprecated
public class DefaultLspsAppConnectorV7 extends LspsAppConnectorImpl {

	private static final long serialVersionUID = 6600639435905976895L;

	/**
	 * Full constructor.
	 * @param ui not null
	 */
	public DefaultLspsAppConnectorV7(LspsUI ui) {
		super(ui);
	}

	@Override
	public UIComponentFactory getComponentFactory() {
		return new LspsUIComponentFactoryV7(this);
	}

	@Override
	public FormComponentFactoryV8 getFormsComponentFactory() {
		return new LspsFormComponentFactoryV7();
	}

}
