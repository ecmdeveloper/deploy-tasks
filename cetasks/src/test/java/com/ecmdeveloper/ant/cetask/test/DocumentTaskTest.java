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
public class DocumentTaskTest extends BuildFileTest {

	protected void setUp() throws Exception {
		configureProject("src/test/resources/update_objects.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	@Category({TestOfTheDay.class})
	public void testName() throws Exception {
		executeTarget("dashboard");
		assertDebuglogContaining("Woensdag");
		System.out.println(getFullLog());
	}
}
