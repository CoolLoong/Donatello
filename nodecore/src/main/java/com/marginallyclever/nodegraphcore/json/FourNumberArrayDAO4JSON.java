package com.marginallyclever.nodegraphcore.json;

import com.marginallyclever.nodegraphcore.DAO4JSON;
import com.marginallyclever.nodegraphcore.type.FourNumber;
import com.marginallyclever.nodegraphcore.type.FourNumberArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FourNumberArrayDAO4JSON implements DAO4JSON<FourNumberArray> {
    @Override
    public Object toJSON(Object value) throws JSONException {
        FourNumberArray fourNumberArray = (FourNumberArray) value;
        JSONArray objects = new JSONArray();
        fourNumberArray.get().forEach(f -> {
            JSONObject r = new JSONObject();
            r.put("x", f.getX());
            r.put("y", f.getY());
            r.put("z", f.getZ());
            r.put("speed", f.getW());
            objects.put(r);
        });
        return objects;
    }

    @Override
    public FourNumberArray fromJSON(Object object) throws JSONException {
        FourNumberArray fourNumberArray = new FourNumberArray();
        JSONArray jsonArray = (JSONArray) object;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject r = jsonArray.getJSONObject(i);
            FourNumber fourNumber = new FourNumber(r.getDouble("x"), r.getDouble("y"), r.getDouble("z"), r.getDouble("speed"));
            fourNumberArray.get().add(fourNumber);
        }
        return fourNumberArray;
    }
}