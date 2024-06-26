package com.marginallyclever.nodegraphcore;

import java.util.ArrayList;
import java.util.List;

public class DockShipping<T> extends Dock<T> {
    private final List<Connection> to = new ArrayList<>();

    public DockShipping(String _name, Class<T> type, T startingValue) throws IllegalArgumentException {
        super(_name, type, startingValue);
    }

    public void send(Packet<?> packet) {
        super.setValue(packet.getData());
        for (Connection c : to) {
            c.send(packet);
        }
    }

    public List<Connection> getTo() {
        return to;
    }

    public void addTo(Connection connection) {
        to.add(connection);
    }

    public void removeTo(Connection connection) {
        to.remove(connection);
    }

    public boolean outputHasRoom() {
        for (Connection c : to) {
            if (!c.isEmpty()) return false;
        }
        return true;
    }

    /**
     * Creates a copy of this {@link Dock}, while flipping hasInput and hasOutput
     *
     * @return an inverted copy of this {@link Dock}.
     */
    @Override
    public Dock<T> createInverse() {
        return new DockReceiving<>(name, type, value);
    }
}
