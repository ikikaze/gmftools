/*
 * generated by Xtext
 */
package org.xtext.example;

import org.eclipse.xtext.ui.core.editor.info.ResourceForResourceWorkingCopyEditorInputFactory;
import org.eclipse.xtext.ui.core.editor.model.IResourceForEditorInputFactory;

/**
 * Use this class to register components to be used within the IDE.
 */
public class EntitiesUiModule extends org.xtext.example.AbstractEntitiesUiModule {

	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return ResourceForResourceWorkingCopyEditorInputFactory.class;
	}
}
