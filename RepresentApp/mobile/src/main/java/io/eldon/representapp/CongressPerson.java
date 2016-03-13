package io.eldon.representapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by eldon on 3/1/2016.
 *
 * Represents members of congress.
 */
public class CongressPerson implements Serializable {
    private String name;
    private URL siteURL;
    private String email;
    private String party;
    private String twitterID;
    private String prefix;
    private String endDate;
    private String bioguideID;
    private ArrayList<String> committees;
    private ArrayList<String> legislation;
    private int photoId;

    CongressPerson(String prefix,
                   String name,
                   URL url,
                   String email,
                   String party,
                   String twitterID,
                   String endDate,
                   String bioguideID) {
        this.prefix = prefix;
        this.name = name;
        this.siteURL = url;
        this.email = email;
        this.party = party;
        this.twitterID = twitterID;
        this.endDate = endDate;
        this.bioguideID = bioguideID;
    }

    public static ArrayList<CongressPerson> getFromSunlightData(String response) {
        try {
            JSONObject j = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray results = j.getJSONArray("results");
            ArrayList<CongressPerson> cp = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                j = results.getJSONObject(i);
                cp.add(new CongressPerson(
                        j.getString("title") + ".",
                        j.getString("first_name") + " " + j.getString("last_name"),
                        new URL(j.getString("website")),
                        j.getString("oc_email"),
                        j.getString("party").equalsIgnoreCase("D") ? "Democrat" : j.getString("party").equalsIgnoreCase("R") ? "Republican" : j.getString("party"),
                        j.getString("twitter_id"),
                        j.getString("term_end"),
                        j.getString("bioguide_id")
                ));
            }
            return cp;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /* Setters  */

    public void setLegislation(ArrayList<String> legislation) {
        this.legislation = legislation;
    }

    public void setCommittees(ArrayList<String> committees) {
        this.committees = committees;
    }


    /* Getters for private fields */

    public String getWearSerializedString() {
        return this.getName() + "," + this.getParty();
    }

    public String getCommittees() {
        if (this.committees != null) {
            return TextUtils.join("\n", this.committees);
        }
        return "";
    }

    public String getLegislation() {
        if (this.committees != null) {
            return TextUtils.join("\n", this.legislation);
        }
        return "";
    }

    public String getName() {
        return this.prefix + " " + this.name;
    }

    public String getWebsite() {
        return this.siteURL.getHost();
    }

    public String getEmail() {
        return this.email;
    }

    public String getParty() {
        return this.party;
    }

    public String getTruncatedLastTweet() {
        if (this.twitterID.length() >= 50) {
            return this.twitterID.substring(0, this.twitterID.substring(0, 46).lastIndexOf(" ")) + " [...]";
        }
        return this.twitterID;
    }

    public String getTwitterID() {
        return this.twitterID;
    }

    public String getBioguideID() {
        return this.bioguideID;
    }

    public String getEndDate() {
        return this.endDate;
    }
}