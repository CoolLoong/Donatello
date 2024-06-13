package com.marginallyclever.nodegraphcore;

public class DockValue<T> extends Dock<T> {
    public DockValue(String _name, Class<T> type, T startingValue) throws IllegalArgumentException {
        super(_name, type, startingValue);
    }

    /**
     * Creates a copy of this {@link Dock}, while flipping hasInput and hasOutput
     *
     * @return an inverted copy of this {@link Dock}.
     */
    @Override
    public Dock<T> createInverse() {
        return new DockValue<T>(name, type, value);
    }
}
