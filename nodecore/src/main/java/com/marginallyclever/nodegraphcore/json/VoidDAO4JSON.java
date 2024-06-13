package com.marginallyclever.nodegraphcore.json;

import com.marginallyclever.nodegraphcore.DAO4JSON;
import org.json.JSONException;

public class VoidDAO4JSON implements DAO4JSON<Void> {
    @Override
    public Object toJSON(Object value) throws JSONException {
        return "";
    }

    @Override
    public Void fromJSON(Object object) throws JSONException {
        return null;
    }
}