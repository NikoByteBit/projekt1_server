package org.projekt1.server.service;

import org.projekt1.server.model.Zeitberechnungen;
import org.projekt1.server.repository.ZeitberechnungenRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MitarbeiterZeitService {
    private final ZeitberechnungenRepository zeitberechnungenRepository;

    public MitarbeiterZeitService(ZeitberechnungenRepository zeitberechnungenRepository) {
        this.zeitberechnungenRepository = zeitberechnungenRepository;
    }

    public void startArbeitszeit(Integer mitarbeiterId, LocalDateTime startZeit) {
        Zeitberechnungen zeiten = new Zeitberechnungen();
        zeiten.setMitarbeiterId(mitarbeiterId);
        zeiten.setArbeitsbeginn(startZeit);
        zeiten.setDatum(LocalDate.now());
        zeitberechnungenRepository.save(zeiten);
    }

    public void endArbeitszeit(Integer mitarbeiterId, LocalDateTime endZeit) {
        Zeitberechnungen zeiten = zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
        if (zeiten != null) {
            zeiten.setArbeitsende(endZeit);
            zeiten.setGesamtarbeitszeit(Duration.between(zeiten.getArbeitsbeginn(), endZeit).toMinutes());
            zeitberechnungenRepository.update(zeiten);
        }
    }

    public void startPausenzeit(Integer mitarbeiterId, LocalDateTime startPausenzeit) {
        Zeitberechnungen zeiten = zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
        if (zeiten != null) {
            zeiten.setPausenbeginn(startPausenzeit);
            zeitberechnungenRepository.update(zeiten);
        } else {
            throw new IllegalStateException("Keine aktive Arbeitszeit gefunden, um die Pause zu beginnen.");
        }
    }

    public void endPausenzeit(Integer mitarbeiterId, LocalDateTime endPausenzeit) {
        Zeitberechnungen zeiten = zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
        if (zeiten != null && zeiten.getPausenbeginn() != null) {
            zeiten.setPausenende(endPausenzeit);
            long pauseDauer = Duration.between(zeiten.getPausenbeginn(), endPausenzeit).toMinutes();
            zeiten.setGesamtpausenzeit(pauseDauer);
            zeitberechnungenRepository.update(zeiten);
        } else {
            throw new IllegalStateException("Pausenbeginn nicht gesetzt oder keine aktive Arbeitszeit gefunden.");
        }
    }

    public Zeitberechnungen getArbeitszeiten(Integer mitarbeiterId) {
        return zeitberechnungenRepository.findByMitarbeiterIdAndDatum(mitarbeiterId, LocalDate.now());
    }
}
