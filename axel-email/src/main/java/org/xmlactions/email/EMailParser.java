/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */


package org.xmlactions.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;

/**
 * Parses the content of an email including body and attachments to an
 * EMailExportMessage class.
 * 
 * @author MichaelMurphy
 */
public class EMailParser
{

	private static Logger log = LoggerFactory.getLogger(EMailParser.class);

	// if this gets set then we've moved onto a forwarded email content
	private Message lastMessage;

	private boolean firstMessageProcessed;

	// emails can have both plain text and html content, text is what we want as
	// it's easier to work with. We set this true if we find a text/plain body
	// content, which will instruct the code to ignore any text/html parts
	private boolean ignoreHTML = false;

	public void mapMessage(Message message) throws IOException, MessagingException, DocumentException
	{

		log.info("reading email:" + message.getSubject());
		setLastMessage(message); // if this is set then we have an attached
		// message
		setFirstMessageProcessed(false);
		Object object = message;

		if (object instanceof Multipart) {
			mapMultiPart((Multipart) object);
		} else if (object instanceof Part) {
			// showPart((Part) content);
			handlePart((Part) object);
		} else {
			throw new IOException("Invalid content found in email:" + object.getClass().getName() + "\n" + object);
		}
	}

	private void mapMultiPart(Multipart multiPart) throws MessagingException, IOException, DocumentException
	{

		for (int i = 0, n = multiPart.getCount(); i < n; i++) {
			BodyPart bodyPart = multiPart.getBodyPart(i);
			// showPart(bodyPart);
			handlePart(bodyPart);
		}
	}

	private void handlePart(Part part) throws MessagingException, IOException, DocumentException
	{

		log.debug("\n\n\nhandlePart ==>>");
		log.debug("part.toString():" + part.toString());
		log.debug("part.getContent():" + (part.getFileName() == null ? part.getContent().toString() : "Attachment"));
		log.debug("part.getContentType():" + part.getContentType());
		log.debug("part.getFilename():" + part.getFileName());
		log.debug("part.isAttachment:" + part.getFileName());
		log.debug("part.isMessage:" + (part.getContent() instanceof Message));
		Object obj = part.getContent();
		if (obj instanceof Multipart) {
			Multipart mmp = (Multipart) obj;
			for (int i = 0; i < mmp.getCount(); i++) {
				Part bodyPart = mmp.getBodyPart(i);
				if (bodyPart instanceof Message) {
					setFirstMessageProcessed(true);// need to mark this when we
					// get a forwarded message
					// so we don't look for case
					// numbers in forwarded
					// emails.
				}
				handlePart(bodyPart);
			}
		} else if (obj instanceof Part) {
			if (obj instanceof Message) {
				setFirstMessageProcessed(true);// need to mark this when we get
				// a forwarded message so we
				// don't look for case numbers
				// in forwarded emails.
			}
			handlePart((Part) obj);
		} else {
			if (part instanceof MimeBodyPart) {
				MimeBodyPart p = (MimeBodyPart) part;
				Enumeration enumeration = p.getAllHeaders();
				while (enumeration.hasMoreElements()) {
					Object e = enumeration.nextElement();
					if (e == null)
						e = null;
				}
				Object content = p.getContent();
				enumeration = p.getAllHeaderLines();
				while (enumeration.hasMoreElements()) {
					Object e = enumeration.nextElement();
					if (e == null)
						e = null;
				}
				DataHandler dh = p.getDataHandler();
				if (dh == null)
					dh = null;
			}
			addPart(part);
			log.debug("=== Add Part ===");
			log.debug((String) (part.getFileName() != null ? "isAttachment" : part.getContent()));
			// log.info("not recognised class:" + obj.getClass().getName() +
			// "\n" + obj);
		}
		log.debug("<<== handlePart");
	}

	public boolean isFirstMessageProcessed()
	{

		return firstMessageProcessed;
	}

	public void setFirstMessageProcessed(boolean firstMessageProcessed)
	{

		this.firstMessageProcessed = firstMessageProcessed;
	}

	private void showPart(Part part) throws IOException, MessagingException
	{

		log.info("\n\n\nshowPart ==>>");
		log.info("part.toString():" + part.toString());
		log.info("part.getContent():" + (part.getFileName() == null ? part.getContent().toString() : "Attachment"));
		log.info("part.getContentType():" + part.getContentType());
		log.info("part.getFilename():" + part.getFileName());
		log.info("part.isAttachment:" + part.getFileName());
		log.info("part.isMessage:" + (part.getContent() instanceof Message));
		Object obj = part.getContent();
		if (obj instanceof Multipart) {
			log.info("MultiPart");

			Multipart mmp = (Multipart) obj;
			for (int i = 0; i < mmp.getCount(); i++) {
				Part bodyPart = mmp.getBodyPart(i);
				showPart(bodyPart);
			}
		} else if (obj instanceof Part) {
			showPart((Part) obj);
		} else {
			log.info("=== Add Part ===");
			log.info((String) (part.getFileName() != null ? "isAttachment" : part.getContent()));
			// log.info("not recognised class:" + obj.getClass().getName() +
			// "\n" + obj);
		}
		log.info("<<== showPart");
	}

	private void addPart(Part part) throws IOException, MessagingException, DocumentException
	{

		String contentType = part.getContentType();
		boolean isAttachment;

		if (part.getFileName() != null) {
			isAttachment = true;
		} else {
			isAttachment = false;
		}

		log.debug("isAttachment:" + isAttachment + " contentType:" + contentType);

		if (isAttachment == true) {
		} else {
			// Check if plain
			if (contentType.toLowerCase().indexOf("text/plain") >= 0) {
				log.debug("process text/plain");
				setIgnoreHTML(true); // if we get any text/plain for body
			} else if (isIgnoreHTML() == false && contentType.toLowerCase().indexOf("text/html") >= 0) {
				// log.debug("skipping text/html");
				log.debug("process text/html");
				InputStream is = convertHTMLToText(part.getInputStream());
				try {
				} finally {
					IOUtils.closeQuietly(is);
				}

			} else {
				log.debug("ignoring part [" + contentType + "]");
			}
		}
	}

	private void addNewEMailMessage(Message message, InputStream inputStream)
			throws MessagingException, DocumentException, IOException
	{

		addContent(inputStream);
	}

	private void addContent(InputStream inputStream) throws DocumentException, IOException
	{

		if (inputStream.markSupported()) {
			inputStream.mark(inputStream.available());
		}

		String bodyContent = IOUtils.toString(inputStream);

		if (this.firstMessageProcessed == false) {
		}
		if (inputStream.markSupported()) {
			inputStream.reset();
		}
	}

	private InputStream convertHTMLToText(InputStream inputStream) throws IOException
	{

		HtmlToText htmlToText = new HtmlToText();
		String plainText = htmlToText.map(IOUtils.toString(inputStream));
		return IOUtils.toInputStream(plainText);
	}

	public void setLastMessage(Message message)
	{

		this.lastMessage = message;
	}

	public Message getLastMessage()
	{

		return lastMessage;
	}

	/**
	 * Gets the subject from the email if one exists.
	 * 
	 * @param email
	 *            that we want to get the subject from.
	 * @return the subject or "" if no subject found.
	 */
	public static String getSubject(Message email)
	{

		String subject = null;
		try {
			// MM 24 Jun 09. Fixed code from 'subject = getSubject(email);' to
			// 'email.getSubject();' the latter causes an stack overflow.
			subject = email.getSubject();
		} catch (Throwable t) {
			// MM 28 JUL 2009 - lower log severity from error to warn
			log.warn("Error getting Subject from email:" + t.getMessage() + ". Subject ignored");
			subject = "";
		}
		return subject;
	}

	public void setIgnoreHTML(boolean ignoreHTML)
	{

		this.ignoreHTML = ignoreHTML;
	}

	public boolean isIgnoreHTML()
	{

		return ignoreHTML;
	}

}
