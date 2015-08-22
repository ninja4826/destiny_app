package com.hueske.destinymanager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by huesk on 8/21/2015.
 */
public class Endpoints {

    private static String baseUrl = "https://www.bungie.net/Platform/Destiny/";

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

    public static ArrayList<String> getCharacterIDs(Destiny destiny) {
        String membershipType = destiny.getMembershipType();
        String membershipId = destiny.getMembershipId();
        String url = baseUrl + membershipType + "/Account/" + membershipId + "/";

        JSONObject response = execute(url);
        ArrayList<String> characterArray = new ArrayList<String>();
        try {
            JSONArray characters = response.getJSONObject("Response").getJSONObject("data").getJSONArray("characters");
            for (int i = 0; i < characters.length(); i++) {
                characterArray.add(characters.getJSONObject(i).getString("characterId"));
            }

        } catch (JSONException e) {
            System.err.println("Caught JSONException: " + e.getMessage());
        }
        return characterArray;
    }

    public static HashMap<String, JSONArray> getInventory(Destiny destiny) {
        HashMap<String, JSONArray> inventory = new HashMap<String, JSONArray>();
        String url = baseUrl + destiny.getMembershipType() + "/Account/" + destiny.getMembershipId() + "/";
        for (int i = 0; i < destiny.getCharacterIDs().size(); i++) {
            String charID = destiny.getCharacterIDs().get(i);
            JSONObject response = execute(url + destiny.getCharacterIDs().get(i) + "/inventory/");
            try {
                JSONArray inventoryArray = response.getJSONObject("Response").getJSONObject("data").getJSONObject("buckets").getJSONArray("Equippable");
                inventory.put(charID, inventoryArray);
            } catch (JSONException e) {
                System.err.println("Caught JSONException: " + e.getMessage());
            }

        }
        return inventory;
    }
}