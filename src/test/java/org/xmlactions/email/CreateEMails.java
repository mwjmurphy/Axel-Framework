package org.xmlactions.email;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

public class CreateEMails
{
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(this.getClass().getName().toString());

    /** this is the file name and path for the log4j configuration file. */
    // private String log4jFileName = "log4j.properties";
    /** all emails will be sent to this host */
    //private String host = "etsoslex01";
    //private String domain = "ETSOSL.OriginalSolutions.ie";
    private String host = "osldubwsscan01";
    private String domain = "OSL.OriginalSolutions.ie";
    //private String userName = null;
    //private String password = null;

    // private String host = "osldubwsscan01";
    private String type = "s";
    private String dataFolder = "";

    private final static String[] toETSStructured = { "ets_struct_1" };
    private final static String[] toETSNonStructured = { "ets_unstruct_1" };
    //private final static String [] toETSStructured = {"ets_structured"};
    //private final static String [] toETSNonStructured = {"ets_nonstructured"};
    private final static String[] toACAS = { "acas_unstruct_1" };
    private final static String toETSStructuredPassword = "ets";
    private final static String toETSNonStructuredPassword = "ets";
    private final static String toACASPassword = "ets";

    /**
     * @param host is the email host like 'osldubwsscan01'
     * @param domain is the email domain address like 'etsosl.originalsolutions.ie'
     * @param type is the email type we want to create like 's' for ets structured, 'u' for ets unstructured or 'a' for an acas unstructured.
     * @param dataFolder is where we get the attachments from
     */
    public CreateEMails(String host, String domain, String userName, String password, String type, String dataFolder)
            throws Exception
    {
        this.host = host;
        this.domain = domain;
        //this.userName = userName;
        //this.password = password;
        this.type = type;
        this.dataFolder = dataFolder;
        if ("s".equalsIgnoreCase(type))
        {
            sendETSStructured();
        }
        else if ("sb".equalsIgnoreCase(type))
        {
            sendETSStructuredBad();
        }
        else if ("u".equalsIgnoreCase(type))
        {
            sendETSNonStructured();
        }
        else if ("a".equalsIgnoreCase(type))
        {
            sendACAS();
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length < 5)
        {
            System.out.println("Missing parameters!");
            System.out
                    .println("parameters are: 'host address' , 'domain', 'user name', 'password', 'type of email', 'data folder'");
            System.out.println("where:\n"
                    + "'host address' is the address of the email server, as an example ETSOSLEX01.\n"
                    + "'domain' is the email server domain name, as an example ETSOSL.OriginalSolutions.ie\n"
                    + "'user name' is the email account we want to send mail to\n"
                    + "'password' is the email account password\n"
                    + "'type of email' is the type we want to send, types are:" + "      's' - ETS Structured,\n"
                    + "      'sb' - ETS Structured Bad - causes error,\n" + "      'u' - ETS Unstructured and\n"
                    + "      'a' - for ACAS\n"
                    + "'data folder' is the location of the sample data folder where we get email attachments from.");
            System.exit(0);
        }
        new CreateEMails(args[0], args[1], args[2], args[3], args[4], args[5]);
    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    public void _sendACAS() throws Exception
    {
        Address[] address = new Address[toACAS.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toACAS.length; pLoop++)
        {
            String to = toACAS[pLoop] + "@" + host;
            address[pLoop] = new InternetAddress(to);
        }
        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", host);

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSentDate(new Date());
        for (Address element : address)
        {
            logger.debug("Sending ACAS email to " + element.toString());
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ACAS");
        message.setText("Test message for ACAS");

        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < 1; iLoop++)
            {
                // Send message
                //Transport.send(message);
                transport.connect();
                transport.sendMessage(message, address);
                transport.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error sending message[" + iLoop + "] to " + address[0].toString() + ". Error:"
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    public void sendACAS() throws Exception
    {
        Address[] address = new Address[toACAS.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toACAS.length; pLoop++)
        {
            String to = toACAS[pLoop] + "@" + host;
            address[pLoop] = new InternetAddress(to);
        }
        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", host);

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSentDate(new Date());
        for (Address element : address)
        {
            logger.debug("Sending ACAS Correspondence email to " + element.toString());
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ACAS Correspondence");
        message.setText("Test message for ACAS Correspondence");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ACAS");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File[] files = getAttachments(dataFolder);
        for (File file : files)
        {
            if ((file.exists() == true) && (file.isFile() == true))
            {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file.getAbsolutePath());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());
                multipart.addBodyPart(messageBodyPart);
            }
        }

        // Put parts in message
        message.setContent(multipart);

        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < 1; iLoop++)
            {
                // Send message
                //Transport.send(message);
                transport.connect();
                transport.sendMessage(message, address);
                transport.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error sending message[" + iLoop + "] to " + address[0].toString() + ". Error:"
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    public void sendETSNonStructured() throws Exception
    {
        Address[] address = new Address[toETSNonStructured.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toETSNonStructured.length; pLoop++)
        {
            String to = toETSNonStructured[pLoop] + "@" + host;
            address[pLoop] = new InternetAddress(to);
        }
        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", host);

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSentDate(new Date());
        for (Address element : address)
        {
            logger.debug("Sending ETS Non Structured email to " + element.toString());
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ETS Non Structured");
        message.setText("Test message for ETS Non Structured");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ETS");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File[] files = getAttachments(dataFolder);
        for (File file : files)
        {
            if ((file.exists() == true) && (file.isFile() == true))
            {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file.getAbsolutePath());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());
                multipart.addBodyPart(messageBodyPart);
            }
        }

        // Put parts in message
        message.setContent(multipart);

        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < 1; iLoop++)
            {
                // Send message
                //Transport.send(message);
                transport.connect();
                transport.sendMessage(message, address);
                transport.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error sending message[" + iLoop + "] to " + address[0].toString() + ". Error:"
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    public void sendETSStructured() throws Exception
    {
        Address[] address = new Address[toETSStructured.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toETSStructured.length; pLoop++)
        {
            String to = toETSStructured[pLoop] + "@" + domain;
            address[pLoop] = new InternetAddress(to);
        }
        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", host);
        MyAuthenticator auth = new MyAuthenticator(toETSStructured[0], toETSStructuredPassword);
        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSentDate(new Date());
        for (Address element : address)
        {
            logger.debug("Sending ETS Structured email to " + element.toString());
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ETS Structured ET1");
        message.setText("Test message for ETS Structured ET1");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ET1");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File[] files = getAttachments(dataFolder);
        for (File file : files)
        {
            if ((file.exists() == true) && (file.isFile() == true))
            {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file.getAbsolutePath());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());
                multipart.addBodyPart(messageBodyPart);
            }
        }

        // Put parts in message
        message.setContent(multipart);

        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < 1; iLoop++)
            {
                // Send message
                //Transport.send(message);
                //transport.connect (this.host, this.userName, this.password);
                transport.connect();
                transport.sendMessage(message, address);
                transport.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error sending message[" + iLoop + "] to " + address[0].toString() + ". Error:"
                    + ex.getMessage(), ex);
        }

    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    public void sendETSStructuredBad() throws Exception
    {
        Address[] address = new Address[toETSStructured.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toETSStructured.length; pLoop++)
        {
            String to = toETSStructured[pLoop] + "@" + host;
            address[pLoop] = new InternetAddress(to);
        }
        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", host);

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSentDate(new Date());
        for (Address element : address)
        {
            logger.debug("Sending ETS Structured Bad email to " + element.toString());
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ETS Structured Bad ET1");
        message.setText("Test message for ETS Structured Bad ET1");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ET1");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File file = new File(dataFolder + "/ET1-BAD.txt");
        if (file.exists() == false)
        {
            throw new IOException("File 's" + dataFolder + "/ET1-BAD.txt' does not exist");
        }
        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(dataFolder + "/ET1-BAD.txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ET1-BAD.txt");
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource(dataFolder + "/ETS1-LTJ-2245-12791-05274-04.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ETS1-LTJ-2245-12791-05274-04.pdf");
        // multipart.addBodyPart (messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < 1; iLoop++)
            {
                // Send message
                //Transport.send(message);
                transport.connect();
                transport.sendMessage(message, address);
                transport.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error sending message[" + iLoop + "] to " + address[0].toString() + ". Error:"
                    + ex.getMessage(), ex);
        }

    }

    /**
     * This will build a list of all files in the sample data folder.
     * @param folderName is where we look for the sample data.
     * @return the files found as a list.
     * @throws java.lang.Exception
     */
    File[] getAttachments(String folderName) throws Exception
    {
        File file = new File(folderName);
        File[] files = file.listFiles();
        return (files);
    }

    class MyAuthenticator extends Authenticator
    {
        String login = null;
        String password = null;

        MyAuthenticator(String login, String password)
        {
            super();
            this.login = login;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(login, password);
        }
    }

}
