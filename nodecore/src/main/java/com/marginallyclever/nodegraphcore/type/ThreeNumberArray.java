package com.marginallyclever.nodegraphcore.type;

import java.util.ArrayList;
import java.util.List;

public class ThreeNumberArray {
    List<ThreeNumber> v = new ArrayList<>();

    public List<ThreeNumber> get() {
        return v;
    }

    public void set(List<ThreeNumber> v) {
        this.v = v;
    }
}
