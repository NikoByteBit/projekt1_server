package org.projekt1.server.repository;

import org.projekt1.server.model.Mitarbeiter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MitarbeiterRepository {
    private final JdbcTemplate jdbcTemplate;

    public MitarbeiterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class MitarbeiterMapper implements RowMapper<Mitarbeiter> {
        @Override
        public Mitarbeiter mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mitarbeiter mitarbeiter = new Mitarbeiter();
            mitarbeiter.setMitarbeiterId(rs.getInt("mitarbeiter_id"));
            mitarbeiter.setUsername(rs.getString("username"));
            mitarbeiter.setPassword(rs.getString("password"));
            mitarbeiter.setVorname(rs.getString("vorname"));
            mitarbeiter.setNachname(rs.getString("nachname"));
            mitarbeiter.setAbteilung(rs.getString("abteilung"));
            mitarbeiter.setRolle(rs.getString("rolle"));
            return mitarbeiter;
        }
    }

    public Mitarbeiter findById(Integer mitarbeiterId) {
        String sql = "SELECT * FROM Mitarbeiter WHERE mitarbeiter_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{mitarbeiterId}, new MitarbeiterMapper());
    }

    public Mitarbeiter findByUsername(String username) {
        String sql = "SELECT * FROM Mitarbeiter WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new MitarbeiterMapper());
    }
}
