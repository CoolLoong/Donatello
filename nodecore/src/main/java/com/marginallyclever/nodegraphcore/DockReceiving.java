package com.marginallyclever.nodegraphcore;

public class DockReceiving<T> extends Dock<T> {
    private Connection from;

    public DockReceiving(String _name, Class<T> type, T startingValue) throws IllegalArgumentException {
        super(_name, type, startingValue);
    }

    public void receive() {
        if (from == null) return;

        Packet<?> packet = from.get();
        if (packet == null) return;
        super.setValue(packet.getData());
    }

    public Connection getFrom() {
        return from;
    }

    public boolean hasPacketWaiting() {
        return from != null && !from.isEmpty();
    }

    public void setFrom(Connection connection) {
        from = connection;
    }

    public void removeFrom(Connection connection) {
        if (from == connection) {
            from = null;
        }
    }

    public boolean hasConnection() {
        return from != null;
    }

    /**
     * Creates a copy of this {@link Dock}, while flipping hasInput and hasOutput
     *
     * @return an inverted copy of this {@link Dock}.
     */
    @Override
    public Dock<T> createInverse() {
        return new DockShipping<T>(name, type, value);
    }
}
