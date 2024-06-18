package org.projekt1.server.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Zeitberechnungen {
    private Integer zeitId;
    private Integer mitarbeiterId;
    private LocalDateTime arbeitsbeginn;
    private LocalDateTime arbeitsende;
    private long gesamtarbeitszeit;
    private long gesamtpausenzeit;
    private LocalDate datum;
    private LocalDateTime pausenbeginn;
    private LocalDateTime pausenende;

    // Getter und Setter

    public Integer getZeitId() {
        return zeitId;
    }

    public void setZeitId(Integer zeitId) {
        this.zeitId = zeitId;
    }

    public Integer getMitarbeiterId() {
        return mitarbeiterId;
    }

    public void setMitarbeiterId(Integer mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }

    public LocalDateTime getArbeitsbeginn() {
        return arbeitsbeginn;
    }

    public void setArbeitsbeginn(LocalDateTime arbeitsbeginn) {
        this.arbeitsbeginn = arbeitsbeginn;
    }

    public LocalDateTime getArbeitsende() {
        return arbeitsende;
    }

    public void setArbeitsende(LocalDateTime arbeitsende) {
        this.arbeitsende = arbeitsende;
    }

    public long getGesamtarbeitszeit() {
        return gesamtarbeitszeit;
    }

    public void setGesamtarbeitszeit(long gesamtarbeitszeit) {
        this.gesamtarbeitszeit = gesamtarbeitszeit;
    }

    public long getGesamtpausenzeit() {
        return gesamtpausenzeit;
    }

    public void setGesamtpausenzeit(long gesamtpausenzeit) {
        this.gesamtpausenzeit = gesamtpausenzeit;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDateTime getPausenbeginn() {
        return pausenbeginn;
    }

    public void setPausenbeginn(LocalDateTime pausenbeginn) {
        this.pausenbeginn = pausenbeginn;
    }

    public LocalDateTime getPausenende() {
        return pausenende;
    }

    public void setPausenende(LocalDateTime pausenende) {
        this.pausenende = pausenende;
    }
}
