package com.app.appuber.model;

public class Users {

    private String id_user;
    private String use_nameFirts;
    private String use_nameLast;
    private String use_nameName;
    private String use_email;
    private String use_password;

    public Users() {

    }

    public Users(String id_user, String use_nameFirts, String use_nameLast, String use_nameName, String use_email, String use_password) {
        this.id_user = id_user;
        this.use_nameFirts = use_nameFirts;
        this.use_nameLast = use_nameLast;
        this.use_nameName = use_nameName;
        this.use_email = use_email;
        this.use_password = use_password;
    }

    public String getId_user() {
        return id_user;
    }
    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUse_nameFirts() {
        return use_nameFirts;
    }
    public void setUse_nameFirts(String use_nameFirts) {
        this.use_nameFirts = use_nameFirts;
    }

    public String getUse_nameLast() {
        return use_nameLast;
    }
    public void setUse_nameLast(String use_nameLast) {
        this.use_nameLast = use_nameLast;
    }

    public String getUse_nameName() {
        return use_nameName;
    }
    public void setUse_nameName(String use_nameName) {
        this.use_nameName = use_nameName;
    }

    public String getUse_email() {
        return use_email;
    }
    public void setUse_email(String use_email) {
        this.use_email = use_email;
    }

    public String getUse_password() {
        return use_password;
    }
    public void setUse_password(String use_password) {
        this.use_password = use_password;
    }
}