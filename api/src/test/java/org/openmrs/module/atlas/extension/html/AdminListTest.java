package org.openmrs.module.atlas.extension.html;

import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import org.openmrs.api.context.Context;
import org.openmrs.module.Extension.MEDIA_TYPE;
import org.openmrs.module.atlas.extension.html.AdminList;

public class AdminListTest {
	
	/**
	 * This method runs before every test and opens the context session.
	 */
	@Before
	public void runBeforeEveryTest() {
		Context.openSession();
	}

	/**
	 * This method runs after every test and closes the context session.
	 */
	@After
	public void runAfterEveryTest() {
		Context.closeSession();
	}

	/**
	 * @see AdminList#getLinks()
	 * @verifies the returned map should not be null
	 */
	@Test
	@Ignore("Unignore after finding out how to authenticate a mock user")
	public void getLinks_shouldTheReturnedMapShouldNotBeNull() throws Exception {
		AdminList ext = new AdminList();
		
		Map<String, String> links = ext.getLinks();
		if (links == null) {
			fail("Some links should be returned");
		}
		
	}
	
	/**
	 * @see AdminList#getLinks()
	 * @verifies the list should contain a positive number of links
	 */
	@Test
	@Ignore("Unignore after finding out how to authenticate a mock user")
	public void getLinks_shouldTheListShouldContainAPositiveNumberOfLinks() throws Exception {
		AdminList ext = new AdminList();
		
		Map<String, String> links = ext.getLinks();
		
		assertTrue("There should be a positive number of links", links.values().size() > 0);
	}
	
	/**
	 * @see AdminList#getMediaType()
	 * @verifies The media type should be html
	 */
	@Test
	public void getMediaType_shouldTheMediaTypeShouldBeHtml() throws Exception {
		AdminList ext = new AdminList();
		
		assertTrue("The media type of this extension should be html", ext.getMediaType().equals(MEDIA_TYPE.html));
		
	}
}
