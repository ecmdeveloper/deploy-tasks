/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import com.filenet.api.admin.LocalizedString;
import com.filenet.api.core.Factory;

/**
 * @author Ricardo Belfor
 *
 */
public abstract class ClassDefinitionAction {

	protected LocalizedString createLocalizedString(String name, String localeName) {
		LocalizedString localizedString = Factory.LocalizedString.createInstance();

		localizedString.set_LocalizedText( name );
		localizedString.set_LocaleName(localeName);
		return localizedString;
	}	
	
	
}
