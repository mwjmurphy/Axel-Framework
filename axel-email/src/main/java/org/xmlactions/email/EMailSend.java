
package org.xmlactions.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

public class EMailSend
{

	private final static Logger log = LoggerFactory.getLogger(EMailSend.class);

	public static void sendEMail(String toAddress, String host, String userName, String password, String msg)
			throws AddressException, MessagingException
	{
        sendEMail("mike.murphy@riostl.com", toAddress, host, userName, password, "EMail Addresses - Message [" + msg
                + "]", msg);
	}

    public static void sendEMail(String fromAddress, String toAddress, String host, String userName, String password,
            String subject,
            String msg) throws AddressException, MessagingException {

    	log.debug(String.format("sendEMail(from:%s, to:%s, host:%s, userName:%s, password:%s)\nsubject:{" + subject + "\n}\nmessage:{" + msg + "\n}",
    			fromAddress, toAddress, host, userName, toPassword(password), subject, msg));
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        Session session;
        if (!StringUtils.isEmpty(password)) {
            props.put("mail.smtp.auth", "true");
            //EMailAuthenticator auth = new EMailAuthenticator(userName + "+" + host, password);
            EMailAuthenticator auth = new EMailAuthenticator(userName, password);
            session = Session.getInstance(props, auth);
        } else {
            session = Session.getInstance(props);
        }

        // Define message
        MimeMessage message = new MimeMessage(session);
        // message.setFrom(new InternetAddress("email_addresses@riostl.com"));
        message.setFrom(new InternetAddress(fromAddress));
        message.addRecipient(RecipientType.TO, new InternetAddress(toAddress));
        message.setSubject(subject);
        message.setText(msg);

        // Send message
        if (StringUtils.isEmpty(password)) {
            Transport.send(message);
        } else {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            // Send message
            transport.connect();
            transport.sendMessage(message, new Address[] { new InternetAddress(toAddress) });
            transport.close();
        }

    }
    
    public static String toPassword(String pwd) {
    	char [] hidden = new char[pwd.length()];
    	for (int i = 0 ; i < pwd.length(); i++) {
    		if (i < 2) {
    			hidden[i] = pwd.charAt(i);
    		} else if (i > pwd.length()-3) {
    			hidden[i] = pwd.charAt(i);
    		} else {
    			hidden[i]= '*';
    		}
    	}
    	
    	return new String(hidden);
    }
}
