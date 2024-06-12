package com.marginallyclever.nodegraphcore.type;

import java.util.ArrayList;
import java.util.List;

public class FourNumberArray {
    List<FourNumber> v = new ArrayList<>();

    public List<FourNumber> get() {
        return v;
    }

    public void set(List<FourNumber> v) {
        this.v = v;
    }
}
