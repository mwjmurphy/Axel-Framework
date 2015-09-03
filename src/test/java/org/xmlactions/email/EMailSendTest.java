package org.xmlactions.email;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.xmlactions.email.EMailSend;



public class EMailSendTest {

	@Test
	public void testToPassward() {
		
		String result = EMailSend.toPassword("m1i1k1e1");
		
		assertEquals("m1****e1", result);
	}
}
