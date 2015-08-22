package com.hueske.destinymanager;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huesk on 8/21/2015.
 */
public class Destiny {

    private String displayName;
    private String membershipType;
    private String membershipId;
    private ArrayList<String> characterIDs;
    private HashMap<String, JSONArray> inventory;

    public Destiny(String userName) {
        this(userName, "All");
    }
    public Destiny(String userName, String membershipType) {
        String memType;
        if (membershipType == "PSN") {
            memType = "2";
        } else if (membershipType == "Xbox") {
            memType = "1";
        } else {
            memType = "All";
        }
        this.displayName = userName;
        this.membershipType = memType;
        this.membershipId = Endpoints.playerSearch(userName, membershipType);
        this.characterIDs = Endpoints.getCharacterIDs(this);
        this.inventory = Endpoints.getInventory(this);
    }

    public String getDisplayName() {
        return this.displayName;
    }
    public String getMembershipType() {
        return this.membershipType;
    }
    public String getMembershipId() {
        return this.membershipId;
    }
    public ArrayList<String> getCharacterIDs() {
        return this.characterIDs;
    }
    public HashMap<String, JSONArray> getInventory() {
        return this.inventory;
    }
}
