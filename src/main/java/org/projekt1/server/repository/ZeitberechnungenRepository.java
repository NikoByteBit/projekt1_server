package org.projekt1.server.repository;

import org.projekt1.server.model.Zeitberechnungen;
import java.time.LocalDate;

public interface ZeitberechnungenRepository {
    Zeitberechnungen findByMitarbeiterIdAndDatum(Integer mitarbeiterId, LocalDate datum);
    void save(Zeitberechnungen zeiten);
    void update(Zeitberechnungen zeiten);
}
