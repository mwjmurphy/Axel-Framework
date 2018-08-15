
package org.xmlactions.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EMailAuthenticator extends Authenticator
{

	String login = null;

	String password = null;

	public EMailAuthenticator(String login, String password)
	{

		super();
		this.login = login;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication()
	{

		return new PasswordAuthentication(login, password);
	}

}
