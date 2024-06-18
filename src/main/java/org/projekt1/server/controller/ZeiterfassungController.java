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
            // This method needs to be implemented in MitarbeiterZeitService
            var zeiten = mitarbeiterZeitService.getArbeitszeiten(mitarbeiterId);
            return ResponseEntity.ok(zeiten);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Additional endpoints can be defined here for updating, deleting, or managing other aspects of the time tracking system.
}
