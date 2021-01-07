package com.app.appuber.model;

public class Drivers {

    private String id_driver;
    private String dri_nameFirts;
    private String dri_nameLast;
    private String dri_email;
    private String dri_password;
    private String dri_plate; //private String dri_make;
    private String dri_model;

    public Drivers() {
    }

    public Drivers(String id_driver, String dri_nameFirts, String dri_nameLast, String dri_email,
                   String dri_password, String dri_plate, String dri_model) {
        this.id_driver = id_driver;
        this.dri_nameFirts = dri_nameFirts;
        this.dri_nameLast = dri_nameLast;
        this.dri_email = dri_email;
        this.dri_password = dri_password;
        this.dri_plate = dri_plate;
        this.dri_model = dri_model;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getDri_nameFirts() {
        return dri_nameFirts;
    }

    public void setDri_nameFirts(String dri_nameFirts) {
        this.dri_nameFirts = dri_nameFirts;
    }

    public String getDri_nameLast() {
        return dri_nameLast;
    }

    public void setDri_nameLast(String dri_nameLast) {
        this.dri_nameLast = dri_nameLast;
    }

    public String getDri_email() {
        return dri_email;
    }

    public void setDri_email(String dri_email) {
        this.dri_email = dri_email;
    }

    public String getDri_password() {
        return dri_password;
    }

    public void setDri_password(String dri_password) {
        this.dri_password = dri_password;
    }

    public String getDri_plate() {
        return dri_plate;
    }

    public void setDri_plate(String dri_plate) {
        this.dri_plate = dri_plate;
    }

    public String getDri_model() {
        return dri_model;
    }

    public void setDri_model(String dri_model) {
        this.dri_model = dri_model;
    }
}
