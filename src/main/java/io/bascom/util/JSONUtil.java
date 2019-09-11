package io.bascom.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONUtil {

    protected static final String MESSAGE = "message";

    public static String generateResponse(String value) {
        JSONArray array = new JSONArray();
        try {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put(MESSAGE, value);
            array.put(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
            // todo 1. Decide what to send back
            // todo 2. legit logging
        }
        return array.toString();
    }

    public static String generateResponse(List<?> collection) {
        JSONObject envelope = new JSONObject();
        // Metadata
        envelope.put("metadata", new JSONObject().put("count", collection.size()));
        // Data
        JSONArray array = new JSONArray();
        envelope.put("data", array);
        try {
            for (Object element : collection) {
                JSONObject jsonObject = new JSONObject(element);
                array.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // todo 1. Decide what to send back
            // todo 2. legit logging
        }
        return envelope.toString();
    }
}