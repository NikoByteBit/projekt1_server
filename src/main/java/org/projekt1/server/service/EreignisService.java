package org.projekt1.server.service;

import org.projekt1.server.model.Terminal;
import org.projekt1.server.repository.TerminalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EreignisService {

    private final MitarbeiterZeitService mitarbeiterZeitService;
    private final TerminalRepository terminalRepository;

    public EreignisService(MitarbeiterZeitService mitarbeiterZeitService, TerminalRepository terminalRepository) {
        this.mitarbeiterZeitService = mitarbeiterZeitService;
        this.terminalRepository = terminalRepository;
    }

    public void verarbeiteEreignis(Integer mitarbeiterId, Integer terminalId, String eventType) {
        Terminal terminal = terminalRepository.findById(terminalId);
        if (terminal == null) {
            throw new IllegalArgumentException("Unbekanntes Terminal");
        }
        String terminalType = terminal.getType();
        String connectedRoomA = terminal.getConnectedRoomA();
        String connectedRoomB = terminal.getConnectedRoomB();

        switch (eventType) {
            case "Eintritt":
                if (terminalType.equals("Zwischenraum")) {
                    handleRoomTransition(mitarbeiterId, connectedRoomA, connectedRoomB);
                }
                break;
            case "Austritt":
                if (terminalType.equals("Zwischenraum")) {
                    handleRoomTransition(mitarbeiterId, connectedRoomB, connectedRoomA);
                }
                break;
            default:
                throw new IllegalArgumentException("Unbekannter Ereignistyp: " + eventType);
        }
    }

    private void handleRoomTransition(Integer mitarbeiterId, String fromRoom, String toRoom) {
        if (fromRoom.equals("Pausenraum") && toRoom.equals("Arbeitsraum")) {
            mitarbeiterZeitService.endPausenzeit(mitarbeiterId, LocalDateTime.now());
            mitarbeiterZeitService.startArbeitszeit(mitarbeiterId, LocalDateTime.now());
        } else if (fromRoom.equals("Arbeitsraum") && toRoom.equals("Pausenraum")) {
            mitarbeiterZeitService.endArbeitszeit(mitarbeiterId, LocalDateTime.now());
            mitarbeiterZeitService.startPausenzeit(mitarbeiterId, LocalDateTime.now());
        }
    }
}
