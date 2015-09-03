/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlactions.email;


import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.xmlactions.email.EMailAuthenticator;


/**
 * Reads only new emails from an imap server.
 * 
 * @author MichaelMurphy
 */
public class ReadNewEMails
{

	ReadNewEMails()
	{

	}

	public static void main(String[] args) throws Exception
	{

		ReadNewEMails em = new ReadNewEMails();
		// em.sendEMail();
		em.readEMail();
	}

	/**
	 * <p>
	 * Reads all email accounts retrieved from the listener config. Each email
	 * is sent to a JMS queue with any attachments. If the Email is an ET1 or
	 * ET3 then it is also mapped to an XML.
	 * </p>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private void readEMail() throws Exception
	{

		String host = null;
		String userName = null;
		String password = null;
		String loginName = null;
		boolean ETS = true;
		if (ETS == false)
		// ACAS Setting
		{
			host = "192.168.102.51";
			userName = "Guildford";
			password = "password";
			loginName = "Guildford";
		} else
		// ETS Setting
		{
			// host = "ETSOSLEX01";
			// userName = "ets_error_1";
			// password = "ets";
			// loginName = "ets_error_1";
			host = "riostl.com";
			userName = "mike.murphy";
			password = "n1e1i1l1";
			loginName = "mike.murphy@riostl.com";
		}

		// Create empty properties
		Properties props = new Properties();

		if ("imap".equals("imap")) {
			props.setProperty("mail.imap.partialfetch", "true");
		}

		EMailAuthenticator auth = new EMailAuthenticator(loginName, password);
		// Get session
		Session session = Session.getDefaultInstance(props, auth);

		Folder folder = null;
		// Get the store
		Store store = session.getStore("imap");
		try {
			store.connect(host, userName, password);
			// Get folder
			folder = store.getFolder("INBOX");
			// folder.open(Folder.READ_ONLY);
			folder.open(Folder.READ_WRITE);// we need write so we can set flags
			// and remove emails from inbox.

			int unreadMessages = folder.getUnreadMessageCount();
			if (unreadMessages == 0) {
				return;
			}
			// Get all messages
			Message messages[] = folder.getMessages();

			for (int i = 0, n = messages.length; i < n; i++) {
				// Message message = folder.getMessage(1);
				Message message = messages[i];
				try {
					Date receivedDate = message.getReceivedDate();
					Date now = new Date();
					long dayInMillis = (24 * 60 * 60 * 1000);
					long days = (now.getTime() - receivedDate.getTime()) / dayInMillis;
					boolean delete = days > 12;
					// message.setFlag(Flags.Flag.SEEN, true);

				} catch (Exception ex) {
				}
			}
		} catch (Exception ex) {
		} finally {
			if ((folder != null) && (folder.isOpen() == true)) {
				folder.close(true);
			}
		}
	}

	/**
	 * This is a test call for creaing fake emails on server.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void sendEMail() throws Exception
	{

		String host = null;
		String userName = null;
		String password = null;
		String loginName = null;

		boolean ETS = true;
		if (ETS == false)
		// ACAS Setting
		{
			host = "192.168.102.51";
			userName = "Guildford";
			loginName = "Guildford";
			password = "password";
		}
		// ETS Setting
		{
			// host = "ETSOSLEX01";
			host = "etsosl.originalsolutions.ie";
			userName = "ets_error_1";
			password = "ets";
			loginName = "ets_error_1";
		}
		Address[] address = new Address[1];
		address[0] = new InternetAddress(userName + "@" + host);

		String from = userName + "@" + host;

		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.setProperty("mail.smtp.host", "ETSOSLEX01");

		// Get session
		EMailAuthenticator auth = new EMailAuthenticator("administrator" + "@" + "ETSOSLEX01", "etsOSL123");
		Session session = Session.getDefaultInstance(props, auth);

		// Define message
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setSentDate(new Date());

		for (Address element : address) {
			message.addRecipient(Message.RecipientType.TO, element);
		}
		message.setSubject("Test to Groupwise");
		message.setText("This is the body content");

		int iLoop = 0;
		try {
			Provider provider = session.getProvider("smtp");

			Transport transport = session.getTransport(provider);
			for (iLoop = 0; iLoop < 1; iLoop++) {
				// Send message
				// Transport.send(message);
				transport.connect();
				transport.sendMessage(message, address);
				transport.close();
			}
		} catch (Exception ex) {
		}
	}
}
