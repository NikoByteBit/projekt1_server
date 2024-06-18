package org.projekt1.server.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.projekt1.server.service.EreignisService;
import org.projekt1.server.service.MitarbeiterZeitService;

@RestController
@RequestMapping("/api")
public class ZeiterfassungController {

    private final EreignisService ereignisService;
    private final MitarbeiterZeitService mitarbeiterZeitService;

    @Autowired
    public ZeiterfassungController(EreignisService ereignisService, MitarbeiterZeitService mitarbeiterZeitService) {
        this.ereignisService = ereignisService;
        this.mitarbeiterZeitService = mitarbeiterZeitService;
    }

    // Endpoint to handle event processing
    @PostMapping("/events")
    public ResponseEntity<?> verarbeiteEreignis(@RequestParam Integer mitarbeiterId, @RequestParam Integer terminalId, @RequestParam String eventType) {
        try {
            ereignisService.verarbeiteEreignis(mitarbeiterId, terminalId, eventType);
            return ResponseEntity.ok().body("Ereignis erfolgreich verarbeitet");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to fetch work time details
    @GetMapping("/arbeitszeiten/{mitarbeiterId}")
    public ResponseEntity<?> getArbeitszeiten(@PathVariable Integer mitarbeiterId) {
        try {
            var zeiten = mitarbeiterZeitService.getArbeitszeiten(mitarbeiterId);
            return ResponseEntity.ok(zeiten);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

/*
//Zeiterfassungscontroller mit terminalverwaltung
package org.projekt1.server.controller;

import org.projekt1.server.model.Terminal;
import org.projekt1.server.service.EreignisService;
import org.projekt1.server.service.MitarbeiterZeitService;
import org.projekt1.server.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZeiterfassungController {

    private final EreignisService ereignisService;
    private final MitarbeiterZeitService mitarbeiterZeitService;
    private final TerminalService terminalService;

    @Autowired
    public ZeiterfassungController(EreignisService ereignisService, MitarbeiterZeitService mitarbeiterZeitService, TerminalService terminalService) {
        this.ereignisService = ereignisService;
        this.mitarbeiterZeitService = mitarbeiterZeitService;
        this.terminalService = terminalService;
    }

    // Endpoint to handle event processing
    @PostMapping("/events")
    public ResponseEntity<?> verarbeiteEreignis(@RequestParam Integer mitarbeiterId, @RequestParam Integer terminalId, @RequestParam String eventType) {
        try {
            ereignisService.verarbeiteEreignis(mitarbeiterId, terminalId, eventType);
            return ResponseEntity.ok().body("Ereignis erfolgreich verarbeitet");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to fetch work time details
    @GetMapping("/arbeitszeiten/{mitarbeiterId}")
    public ResponseEntity<?> getArbeitszeiten(@PathVariable Integer mitarbeiterId) {
        try {
            var zeiten = mitarbeiterZeitService.getArbeitszeiten(mitarbeiterId);
            return ResponseEntity.ok(zeiten);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Endpoint to fetch all terminals
    @GetMapping("/terminals")
    public List<Terminal> getAllTerminals() {
        return terminalService.getAllTerminals();
    }

    // Endpoint to fetch a terminal by ID
    @GetMapping("/terminals/{terminalId}")
    public Terminal getTerminalById(@PathVariable Integer terminalId) {
        return terminalService.getTerminalById(terminalId);
    }

    // Endpoint to create a new terminal
    @PostMapping("/terminals")
    public ResponseEntity<?> createTerminal(@RequestBody Terminal terminal) {
        try {
            terminalService.createTerminal(terminal);
            return ResponseEntity.ok().body("Terminal erfolgreich erstellt");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to update an existing terminal
    @PutMapping("/terminals/{terminalId}")
    public ResponseEntity<?> updateTerminal(@PathVariable Integer terminalId, @RequestBody Terminal terminal) {
        try {
            terminal.setTerminalId(terminalId);
            terminalService.updateTerminal(terminal);
            return ResponseEntity.ok().body("Terminal erfolgreich aktualisiert");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to delete a terminal
    @DeleteMapping("/terminals/{terminalId}")
    public ResponseEntity<?> deleteTerminal(@PathVariable Integer terminalId) {
        try {
            terminalService.deleteTerminal(terminalId);
            return ResponseEntity.ok().body("Terminal erfolgreich gelöscht");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
*/
