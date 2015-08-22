package com.hueske.destinymanager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by huesk on 8/21/2015.
 */
public class Endpoints {

    private static String baseUrl = "https://www.bungie.net/Platform/Destiny/";
    private static String apiKey = "00047325d52041e5bd0ccaaa3702f3c2";
    public static String playerSearch(String displayName, String membershipType) {
        String url = baseUrl + "SearchDestinyPlayer/"+membershipType+"/"+displayName+"/";

        JSONObject response = execute(url);
        String responseString = "";
        try {
            responseString = response.getJSONArray("Response").getJSONObject(0).getString("membershipId");
        } catch (JSONException e) {
            System.err.println("Caught JSONException: " + e.getMessage());
        }
        return responseString;
    }

    private static JSONObject execute(String url) {
        JSONObject responseObj = new JSONObject();
        try {
            try {
                responseObj = new RetrieveJSONTask().execute(url).get();
            } catch (ExecutionException e) {
                System.err.println("Caught ExecutionException: " + e.getMessage());
            }
        } catch (InterruptedException e) {
            System.err.println("Caught InterruptedException: " + e.getMessage());
        }
        return responseObj;
    }
}