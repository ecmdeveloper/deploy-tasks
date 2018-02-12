/**
 * 
 */
package com.ecmdeveloper.ant.cetask.test;

import org.apache.tools.ant.BuildFileTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author Ricardo Belfor
 *
 */
public class PropertyTemplateTaskTest extends BuildFileTest {

	protected void setUp() throws Exception {
		configureProject("src/test/resources/update_propertytemplate.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	@Category({TestOfTheDay.class})
	public void testName() throws Exception {
		executeTarget("import");
//		assertDebuglogContaining("GovernanceCountry");
		System.out.println(getFullLog());
	}
}
