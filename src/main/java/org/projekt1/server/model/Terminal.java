package org.projekt1.server.model;

public class Terminal {
    private Integer terminalId;
    private String location;
    private String type; // 'Zwischenraum'
    private String description;
    private String connectedRoomA;
    private String connectedRoomB;

    // Getter und Setter

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConnectedRoomA() {
        return connectedRoomA;
    }

    public void setConnectedRoomA(String connectedRoomA) {
        this.connectedRoomA = connectedRoomA;
    }

    public String getConnectedRoomB() {
        return connectedRoomB;
    }

    public void setConnectedRoomB(String connectedRoomB) {
        this.connectedRoomB = connectedRoomB;
    }
}
