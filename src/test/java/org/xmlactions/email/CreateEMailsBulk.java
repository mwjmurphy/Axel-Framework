package org.xmlactions.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

public class CreateEMailsBulk
{
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(this.getClass().getName().toString());

    /** this is the file name and path for the log4j configuration file. */
    // private String log4jFileName = "log4j.properties";
    /** all emails will be sent to this host */
    private String host = "127.0.0.1";

    private final static String[] toETS = { "ets_1", "ets_2", "ets_3", "ets_4", "ets_5", "ets_6", "ets_7", "ets_8",
            "ets_9", "ets_10" };
    private final static String[] toET1 = { "et1_1", "et1_2", "et1_3", "et1_4", "et1_5", "et1_6", "et1_7", "et1_8",
            "et1_9", "et1_10" };

    private final static String[] toET3 = { "et3_1", "et3_2", "et3_3", "et3_4", "et3_5", "et3_6", "et3_7", "et3_8",
            "et3_9", "et3_10" };

    private final static String[] toACAS = { "acas_1", "acas_2", "acas_3", "acas_4", "acas_5", "acas_6", "acas_7",
            "acas_8", "acas_9", "acas_10" };

    public CreateEMailsBulk(String host, int count, String dataFolder) throws Exception
    {
        this.host = host;
        //PropertyConfigurator.configure(log4jFileName);
        sendETSEmail(count, dataFolder);
        sendET1Email(count, dataFolder);
        sendET1aEmail(count, dataFolder);
        sendBadET1Email(count, dataFolder);
        sendET3Email(count, dataFolder);
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length < 3)
        {
            System.out.println("Missing parameters!");
            System.out.println("parameters are: 'host address' , 'number of emails', 'data folder'");
            System.out.println("where:\n"
                    + "'host address' is the address of the email server, as an example 127.0.0.1.\n"
                    + "'number of emails' is how many emails we want to send to each account."
                    + "'data folder' is the location of the sample data folder where we get email attachments from.");
            System.exit(0);
        }
        new CreateEMailsBulk(args[0], Integer.parseInt(args[1]), args[2]);
    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    private void sendETSEmail(int count, String dataFolder) throws Exception
    {
        Address[] address = new Address[toETS.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toETS.length; pLoop++)
        {
            String to = toETS[pLoop] + "@" + host;
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
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ETS");
        message.setText("Test message for ETS");

        logger.debug("Sending " + count + " messages to " + address[0].toString());
        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < count; iLoop++)
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
                    + ex.getMessage());
        }
    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    private void sendET1Email(int count, String dataFolder) throws Exception
    {
        Address[] address = new Address[toET1.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toET1.length; pLoop++)
        {
            String to = toET1[pLoop] + "@" + host;
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
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ET1");
        message.setText("Test message for ET1");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ET1");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File file = new File(dataFolder + "/ET1-LTJ-2245-12791-05274-04.txt");
        if (file.exists() == false)
        {
            throw new Exception("File 's" + dataFolder + "/ET1-LTJ-2245-12791-05274-04.txt' does not exist");
        }
        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(dataFolder + "/ET1-LTJ-2245-12791-05274-04.txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ET1-LTJ-2245-12791-05274-04.txt");
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource(dataFolder + "/ETS1-LTJ-2245-12791-05274-04.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ETS1-LTJ-2245-12791-05274-04.pdf");
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        logger.debug("Sending " + count + " messages to " + address[0].toString());
        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < count; iLoop++)
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
                    + ex.getMessage());
        }

    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    private void sendET1aEmail(int count, String dataFolder) throws Exception
    {
        Address[] address = new Address[toET1.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toET1.length; pLoop++)
        {
            String to = toET1[pLoop] + "@" + host;
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
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ET1a");
        message.setText("Test message for ET1");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ET1");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File file = new File(dataFolder + "/ET1_TEST_TEST.txt");
        if (file.exists() == false)
        {
            throw new Exception("File 'sampledata/ET1_TEST_TEST.txt' does not exist");
        }
        // Text form attachment which is ET1
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(dataFolder + "/ET1_TEST_TEST.txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ET1_TEST_TEST.txt.txt");
        multipart.addBodyPart(messageBodyPart);

        // Text form attachment which is ET1
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource(dataFolder + "/ET1a_TEST_TEST.txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ET1a_TEST_TEST.txt.txt");
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource(dataFolder + "/ETS1-LTJ-4733-11198-10719-106.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ETS1-LTJ-4733-11198-10719-106.pdf");
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        logger.debug("Sending " + count + " messages to " + address[0].toString());
        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < count; iLoop++)
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
                    + ex.getMessage());
        }

    }

    /**
     * This is a test call for creaing fake emails on server. This will
     * send a bad structured message.
     *
     * @throws Exception the exception
     */
    private void sendBadET1Email(int count, String dataFolder) throws Exception
    {
        Address[] address = new Address[toET1.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toET1.length; pLoop++)
        {
            String to = toET1[pLoop] + "@" + host;
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
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ET1 bad");
        message.setText("Test message for ET1 bad");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ET1");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        DataSource source;
        // Part two is attachment
        /* don't sent ET1 text attachment
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource("sampledata/ET1-LTJ-2245-12791-05274-04.txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ET1-LTJ-2245-12791-05274-04.txt");
        multipart.addBodyPart(messageBodyPart);
         */

        File file = new File(dataFolder + "/ETS1-LTJ-2245-12791-05274-04.pdf");
        if (file.exists() == false)
        {
            throw new Exception("File 's" + dataFolder + "/ETS1-LTJ-2245-12791-05274-04.pdf' does not exist");
        }
        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource(dataFolder + "/ETS1-LTJ-2245-12791-05274-04.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ETS1-LTJ-2245-12791-05274-04.pdf");
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        logger.debug("Sending " + count + " messages to " + address[0].toString());
        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < count; iLoop++)
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
                    + ex.getMessage());
        }

    }

    /**
     * This is a test call for creaing fake emails on server.
     *
     * @throws Exception the exception
     */
    private void sendET3Email(int count, String dataFolder) throws Exception
    {
        Address[] address = new Address[toET3.length];
        String from = "mail.sender@" + host;
        for (int pLoop = 0; pLoop < toET3.length; pLoop++)
        {
            String to = toET3[pLoop] + "@" + host;
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
            message.addRecipient(Message.RecipientType.TO, element);
        }
        message.setSubject("Methods ET3");
        message.setText("Test message for ET3");

        //	create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        //fill message
        messageBodyPart.setText("ET3");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        File file = new File(dataFolder + "/ET3-LTJ-1318-15591-05146-80.txt");
        if (file.exists() == false)
        {
            throw new Exception("File 's" + dataFolder + "/ET3-LTJ-1318-15591-05146-80.txt' does not exist");
        }
        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(dataFolder + "/ET3-LTJ-1318-15591-05146-80.txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ET3-LTJ-1318-15591-05146-80.txt");
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        /* ignore pdf, not mandatory
        messageBodyPart = new MimeBodyPart();
        source = new FileDataSource("sampledata/ETS3-LTJ-1318-15591-05146-80.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ETS3-LTJ-1318-15591-05146-80.pdf");
        multipart.addBodyPart(messageBodyPart);
         */

        // Put parts in message
        message.setContent(multipart);

        logger.debug("Sending " + count + " messages to " + address[0].toString());
        int iLoop = 0;
        try
        {
            Provider provider = session.getProvider("smtp");

            Transport transport = session.getTransport(provider);
            for (iLoop = 0; iLoop < count; iLoop++)
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
                    + ex.getMessage());
        }
    }

}
