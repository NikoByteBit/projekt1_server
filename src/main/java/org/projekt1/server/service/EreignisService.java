package org.projekt1.server.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class EreignisService {

    private final MitarbeiterZeitService mitarbeiterZeitService;

    public EreignisService(MitarbeiterZeitService mitarbeiterZeitService) {
        this.mitarbeiterZeitService = mitarbeiterZeitService;
    }

    public void verarbeiteEreignis(Integer mitarbeiterId, Integer terminalId, String eventType) {
        switch (eventType) {
            case "Eintritt":
                mitarbeiterZeitService.startArbeitszeit(mitarbeiterId, LocalDateTime.now());
                break;
            case "Austritt":
                mitarbeiterZeitService.endArbeitszeit(mitarbeiterId, LocalDateTime.now());
                break;
            case "Pausenbeginn":
                mitarbeiterZeitService.startPausenzeit(mitarbeiterId, LocalDateTime.now());
                break;
            case "Pausenende":
                mitarbeiterZeitService.endPausenzeit(mitarbeiterId, LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException("Unbekannter Ereignistyp: " + eventType);
        }
    }
}
