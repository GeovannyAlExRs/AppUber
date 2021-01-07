package com.app.appuber.model;

public class Clients {

    private String id_client;
    private String cli_nameFirts;
    private String cli_nameLast;
    private String cli_email;
    private String cli_password;

    public Clients() {
    }

    public Clients(String id_client, String cli_nameFirts, String cli_nameLast, String cli_email, String cli_password) {
        this.id_client = id_client;
        this.cli_nameFirts = cli_nameFirts;
        this.cli_nameLast = cli_nameLast;
        this.cli_email = cli_email;
        this.cli_password = cli_password;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getCli_nameFirts() {
        return cli_nameFirts;
    }

    public void setCli_nameFirts(String cli_nameFirts) {
        this.cli_nameFirts = cli_nameFirts;
    }

    public String getCli_nameLast() {
        return cli_nameLast;
    }

    public void setCli_nameLast(String cli_nameLast) {
        this.cli_nameLast = cli_nameLast;
    }

    public String getCli_email() {
        return cli_email;
    }

    public void setCli_email(String cli_email) {
        this.cli_email = cli_email;
    }

    public String getCli_password() {
        return cli_password;
    }

    public void setCli_password(String cli_password) {
        this.cli_password = cli_password;
    }
}