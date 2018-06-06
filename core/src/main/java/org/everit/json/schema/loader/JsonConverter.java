package org.everit.json.schema.loader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


public class JsonConverter extends JSONObject {

    public static Map<String, Object> toMap(JSONObject obj) {
        Map<String, Object> results = new HashMap();

        String key;
        Object transformedValue;
        for (Iterator keys = obj.keys(); keys.hasNext(); results.put(key, transformedValue)) {
            key = (String) keys.next();
            Object value = obj.get(key);
            if (value != null && !JSONObject.NULL.equals(value)) {
                if (value instanceof JSONObject) {
                    transformedValue = toMap((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    transformedValue = toList((JSONArray) value);
                } else {
                    transformedValue = value;
                }
            } else {
                transformedValue = null;
            }
        }
        return results;
    }

    public static List<Object> toList(JSONArray array) {
        List<Object> results = new ArrayList(array.length());
        for (int i = 0; i < array.length(); i++) {
            Object element = array.get(i);
            if (element != null && !JSONObject.NULL.equals(element)) {
                if (element instanceof JSONArray) {
                    results.add(toList((JSONArray) element));
                } else if (element instanceof JSONObject) {
                    results.add(toMap((JSONObject) element));
                } else {
                    results.add(element);
                }
            } else {
                results.add(null);
            }
        }
        return results;
    }

}
