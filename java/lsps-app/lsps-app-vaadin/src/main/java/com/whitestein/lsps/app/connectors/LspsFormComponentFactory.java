package com.whitestein.lsps.app.connectors;

import com.vaadin.data.Binder;
import com.vaadin.data.Binder.BindingBuilder;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.renderers.Renderer;
import com.whitestein.lsps.lang.type.Type;
import com.whitestein.lsps.vaadin.forms.FormComponent;
import com.whitestein.lsps.vaadin.forms.FormComponentFactoryV8;
import com.whitestein.lsps.vaadin.forms.WGrid;
import com.whitestein.lsps.vaadin.util.Variant;
import com.whitestein.lsps.vaadin.util.Variant.RecordVariant;

/**
 * Modify this class if you wish to provide additional components to form module (e.g. the new ui).
 */
public class LspsFormComponentFactory extends FormComponentFactoryV8 {

	private static final long serialVersionUID = -399441333312503745L;

	// Uncomment and modify accordingly, to add support for your custom components.
	// To add a new component, please follow these steps:
	// 1. In your model, create a Record which extends the forms::FormComponent record, or one of its descendants. Add any additional fields and methods as applicable.
	//    To call a FormComponent or Vaadin component method, simply use the call("methodName", [parameters]) call.
	//
	// 2. Create Java class which extends the FormComponent class and creates the desired Vaadin Component in {@link FormComponent#createWidget}.
	//
	// Please refer to the online documentation for more information.
	@Override
	public FormComponent<?> create(Variant.RecordVariant def) {
		final String type = def.getTypeFullName();
		if (type.equals("mymodule::MyComponent")) {
			// return new MyComponent(componentData);
		}
		return super.create(def);
	}

	// This allows you to implement additional renderers. Please consult Vaadin documentation on how to implement the
	// renderer itself.
	@Override
	public Renderer<?> createRenderer(WGrid owner, Variant.RecordVariant rendererDef) {
		final String type = rendererDef.getTypeFullName();
		if (type.equals("mymodule::MultiButtonRenderer")) {
			// return new MultiButtonRenderer()
		}
		return super.createRenderer(owner, rendererDef);
	}

	@Override
	public ValueProvider<?, ?> createPresentationProviderForRenderer(WGrid owner, Variant.RecordVariant rendererDef) {
		final String type = rendererDef.getTypeFullName();
		if (type.equals("mymodule::MultiButtonRenderer")) {
			// return new MultiButtonRendererConverter()
		}
		return super.createPresentationProviderForRenderer(owner, rendererDef);
	}

	// This allows you to implement additional editor fields for Grid component.
	// Please consult Vaadin documentation on Grid editing capabilities.
	@Override
	public BindingBuilder<?, ?> createEditorBinding(Binder<?> editorBinder, RecordVariant editorDef, Type type) {
		final String editorType = editorDef.getTypeFullName();
		if (editorType.equals("mymodule::ComplexValueEditor")) {
			// return new ComplexValueEditorField()
		}
		return super.createEditorBinding(editorBinder, editorDef, type);
	}

}
