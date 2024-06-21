package org.projekt1.server.model;

public class Mitarbeiter {
    private Integer mitarbeiterId;
    private String username;
    private String password;
    private String vorname;
    private String nachname;
    private String abteilung;
    private String rolle;

    // Getter und Setter

    public Integer getMitarbeiterId() {
        return mitarbeiterId;
    }

    public void setMitarbeiterId(Integer mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
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

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public boolean isAbteilungsleiter() {
        return "Abteilungsleiter".equals(this.rolle);
    }
}
