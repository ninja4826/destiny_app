package com.hueske.destinymanager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by huesk on 8/21/2015.
 */
public class Endpoints {

    private static String baseUrl = "https://www.bungie.net/Platform/Destiny/";
    private static String apiKey = "00047325d52041e5bd0ccaaa3702f3c2";

    public static int playerSearch(String displayName, int membershipType) {
        String url = baseUrl += ("SearchDestinyPlayer/"+membershipType+"/"+displayName+"/");

        JSONObject response = execute(url);

//      TODO: Replace return value with real value
        return 0;
    }

    private static JSONObject execute(String url) {
        final JSONObject responseObj = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Iterator<String> keyIter = response.keys();
                while (keyIter.hasNext()) {
                    try {
                        String key = keyIter.next();
                        responseObj.put(key, response.get(key));
                    } catch (JSONException e) {
                        System.err.println("Caught JSONException: " + e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//              TODO: Error-Handling
            }
        });
        return responseObj;
    }
}
