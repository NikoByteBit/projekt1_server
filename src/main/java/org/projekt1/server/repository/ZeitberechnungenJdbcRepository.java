package org.projekt1.server.repository;

import org.projekt1.server.model.Zeitberechnungen;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class ZeitberechnungenJdbcRepository implements ZeitberechnungenRepository {
    private final JdbcTemplate jdbcTemplate;

    public ZeitberechnungenJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class ZeitberechnungenMapper implements RowMapper<Zeitberechnungen> {
        @Override
        public Zeitberechnungen mapRow(ResultSet rs, int rowNum) throws SQLException {
            Zeitberechnungen zeiten = new Zeitberechnungen();
            zeiten.setZeitId(rs.getInt("zeit_id"));
            zeiten.setMitarbeiterId(rs.getInt("mitarbeiter_id"));
            zeiten.setArbeitsbeginn(rs.getTimestamp("arbeitsbeginn") != null ? rs.getTimestamp("arbeitsbeginn").toLocalDateTime() : null);
            zeiten.setArbeitsende(rs.getTimestamp("arbeitsende") != null ? rs.getTimestamp("arbeitsende").toLocalDateTime() : null);
            zeiten.setPausenbeginn(rs.getTimestamp("pausenbeginn") != null ? rs.getTimestamp("pausenbeginn").toLocalDateTime() : null);
            zeiten.setPausenende(rs.getTimestamp("pausenende") != null ? rs.getTimestamp("pausenende").toLocalDateTime() : null);
            zeiten.setDatum(rs.getDate("datum").toLocalDate());
            zeiten.setGesamtarbeitszeit(rs.getLong("gesamtarbeitszeit"));
            zeiten.setGesamtpausenzeit(rs.getLong("gesamtpausenzeit"));
            return zeiten;
        }
    }

    @Override
    public Zeitberechnungen findByMitarbeiterIdAndDatum(Integer mitarbeiterId, LocalDate datum) {
        String sql = "SELECT * FROM Zeitberechnungen WHERE mitarbeiter_id = ? AND datum = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{mitarbeiterId, datum}, new ZeitberechnungenMapper());
    }

    @Override
    public void save(Zeitberechnungen zeiten) {
        String sql = "INSERT INTO Zeitberechnungen (mitarbeiter_id, arbeitsbeginn, arbeitsende, datum, pausenbeginn, pausenende, gesamtarbeitszeit, gesamtpausenzeit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, zeiten.getMitarbeiterId(), zeiten.getArbeitsbeginn(), zeiten.getArbeitsende(), zeiten.getDatum(), zeiten.getPausenbeginn(), zeiten.getPausenende(), zeiten.getGesamtarbeitszeit(), zeiten.getGesamtpausenzeit());
    }

    @Override
    public void update(Zeitberechnungen zeiten) {
        String sql = "UPDATE Zeitberechnungen SET arbeitsbeginn = ?, arbeitsende = ?, pausenbeginn = ?, pausenende = ?, gesamtarbeitszeit = ?, gesamtpausenzeit = ? WHERE zeit_id = ?";
        jdbcTemplate.update(sql, zeiten.getArbeitsbeginn(), zeiten.getArbeitsende(), zeiten.getPausenbeginn(), zeiten.getPausenende(), zeiten.getGesamtarbeitszeit(), zeiten.getGesamtpausenzeit(), zeiten.getZeitId());
    }
}
