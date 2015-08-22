package com.hueske.destinymanager;

import android.os.AsyncTask;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by huesk on 8/21/2015.
 */
public class RetrieveJSONTask extends AsyncTask<String, Void, JSONObject> {

    private final String apiKey = "00047325d52041e5bd0ccaaa3702f3c2";
    protected JSONObject doInBackground(String... urls) {
        JSONObject responseObj = new JSONObject();
        try {
            URL obj = new URL(urls[0]);
            try {
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestProperty("X-API-KEY", apiKey);

                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                String response = "";
                while ((inputLine = in.readLine()) != null) {
                    response += inputLine;
                }
                in.close();
                try {
                    responseObj = new JSONObject(response);
                } catch (JSONException e) {
                    System.err.println("Caught JSONException: " + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }
        } catch (MalformedURLException e) {
            System.err.println("Caught MalformedURLException: " + e.getMessage());
        }
        return responseObj;
    }
}
