package org.projekt1.server.service;

import org.projekt1.server.model.Terminal;
import org.projekt1.server.repository.TerminalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {
    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    public List<Terminal> getAllTerminals() {
        return terminalRepository.findAll();
    }

    public Terminal getTerminalById(Integer terminalId) {
        return terminalRepository.findById(terminalId);
    }

    public void createTerminal(Terminal terminal) {
        terminalRepository.save(terminal);
    }

    public void updateTerminal(Terminal terminal) {
        terminalRepository.update(terminal);
    }

    public void deleteTerminal(Integer terminalId) {
        terminalRepository.delete(terminalId);
    }
}
