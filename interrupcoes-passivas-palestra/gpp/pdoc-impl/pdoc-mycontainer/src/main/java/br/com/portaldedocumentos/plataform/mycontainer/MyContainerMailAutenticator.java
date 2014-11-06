package br.com.portaldedocumentos.plataform.mycontainer;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyContainerMailAutenticator extends Authenticator {

    public String username;
    public String password;

    public MyContainerMailAutenticator() {
    }

    public MyContainerMailAutenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}