package org.projekt1.server.service;

import org.projekt1.server.model.Mitarbeiter;
import org.projekt1.server.repository.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitarbeiterService {
    private final MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    public MitarbeiterService(MitarbeiterRepository mitarbeiterRepository) {
        this.mitarbeiterRepository = mitarbeiterRepository;
    }

    public Mitarbeiter findById(Integer mitarbeiterId) {
        return mitarbeiterRepository.findById(mitarbeiterId);
    }

    public Mitarbeiter findByUsername(String username) {
        return mitarbeiterRepository.findByUsername(username);
    }
}
