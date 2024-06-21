package org.projekt1.server.repository;

import org.projekt1.server.model.Terminal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TerminalRepository {
    private final JdbcTemplate jdbcTemplate;

    public TerminalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class TerminalMapper implements RowMapper<Terminal> {
        @Override
        public Terminal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Terminal terminal = new Terminal();
            terminal.setTerminalId(rs.getInt("terminal_id"));
            terminal.setLocation(rs.getString("location"));
            terminal.setType(rs.getString("type"));
            terminal.setDescription(rs.getString("description"));
            terminal.setConnectedRoomA(rs.getString("connected_room_a"));
            terminal.setConnectedRoomB(rs.getString("connected_room_b"));
            return terminal;
        }
    }

    public List<Terminal> findAll() {
        String sql = "SELECT * FROM Terminals";
        return jdbcTemplate.query(sql, new TerminalMapper());
    }

    public Terminal findById(Integer terminalId) {
        String sql = "SELECT * FROM Terminals WHERE terminal_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{terminalId}, new TerminalMapper());
    }

    public void save(Terminal terminal) {
        String sql = "INSERT INTO Terminals (location, type, description) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, terminal.getLocation(), terminal.getType(), terminal.getDescription());
    }

    public void update(Terminal terminal) {
        String sql = "UPDATE Terminals SET location = ?, type = ?, description = ? WHERE terminal_id = ?";
        jdbcTemplate.update(sql, terminal.getLocation(), terminal.getType(), terminal.getDescription(), terminal.getTerminalId());
    }

    public void delete(Integer terminalId) {
        String sql = "DELETE FROM Terminals WHERE terminal_id = ?";
        jdbcTemplate.update(sql, terminalId);
    }
}
