package org.projekt1.server.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Duration;

@Service
public class MitarbeiterZeitService {

    public void startArbeitszeit(Integer mitarbeiterId, LocalDateTime startZeit) {
        // Logik zum Starten der Arbeitszeit
        // Speichern des Zeitstempels in der Datenbank
        Zeitberechnungen zeiten = new Zeitberechnungen();
        zeiten.setMitarbeiterId(mitarbeiterId);
        zeiten.setArbeitsbeginn(startZeit);
        zeitberechnungenRepository.save(zeiten);
    }

    public void endArbeitszeit(Integer mitarbeiterId, LocalDateTime endZeit) {
        // Logik zum Beenden der Arbeitszeit und Berechnung der Arbeitsdauer
        // Aktualisierung der gespeicherten Zeiten in der Datenbank
        Zeitberechnungen zeiten = zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
        if (zeiten != null) {
            zeiten.setArbeitsende(endZeit);
            zeiten.setGesamtarbeitszeit(Duration.between(zeiten.getArbeitsbeginn(), endZeit).toMinutes() / 60.0);
            zeitberechnungenRepository.save(zeiten);
        }
    }

    public void startPausenzeit(Integer mitarbeiterId, LocalDateTime startPausenzeit) {
        // Starten der Pausenzeit
        // Finde den aktuellen Arbeitstagseintrag des Mitarbeiters
        Zeitberechnungen zeiten = zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
        if (zeiten != null) {
            zeiten.setPausenbeginn(startPausenzeit);
            zeitberechnungenRepository.save(zeiten);
        } else {
            throw new IllegalStateException("Keine aktive Arbeitszeit gefunden, um die Pause zu beginnen.");
        }
    }

    public void endPausenzeit(Integer mitarbeiterId, LocalDateTime endPausenzeit) {
        // Beenden der Pausenzeit und Weiterführung der Arbeitszeit
        // Finde den aktuellen Arbeitstagseintrag des Mitarbeiters
        Zeitberechnungen zeiten = zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
        if (zeiten != null && zeiten.getPausenbeginn() != null) {
            zeiten.setPausenende(endPausenzeit);
            // Berechne die Dauer der Pause in Minuten
            long pauseDauer = Duration.between(zeiten.getPausenbeginn(), endPausenzeit).toMinutes();
            zeiten.setGesamtpausenzeit(pauseDauer);
            // Aktualisiere die gesamte Arbeitszeit, subtrahiere die Pausenzeit
            if (zeiten.getArbeitsbeginn() != null && zeiten.getArbeitsende() != null) {
                long gesamtArbeitszeit = Duration.between(zeiten.getArbeitsbeginn(), zeiten.getArbeitsende()).toMinutes() - pauseDauer;
                zeiten.setGesamtarbeitszeit(gesamtArbeits--zeit);
            }
            zeitberechnungenRepository.save(zeiten);
        } else {
            throw new IllegalStateException("Pausenbeginn nicht gesetzt oder keine aktive Arbeitszeit gefunden.");
        }
    }
    }
}
