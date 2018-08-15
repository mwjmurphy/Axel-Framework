package org.xmlactions.email;

/**
 * This class is used to contain the properties needed to send an email.
 * <p>
 * Specific uses for a Spring Configuration.
 * </p>
 * 
 * @author mike.murphy
 * 
 */
public class EMailClientConfig {
    private String fromAddress;
    private String toAddress;
    private String host;
    private String userName;
    private String password;
    private String msg;

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
