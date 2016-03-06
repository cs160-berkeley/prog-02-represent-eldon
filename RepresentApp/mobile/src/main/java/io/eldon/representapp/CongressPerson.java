package io.eldon.representapp;

import android.text.TextUtils;

import java.io.Serializable;
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
    private String lastTweet;
    private String prefix;
    private String endDate;
    private ArrayList<String> committees;
    private ArrayList<String> legislation;
    private int photoId;

    CongressPerson(String prefix,
                   String name,
                   URL url,
                   String email,
                   String party,
                   String lastTweet,
                   String endDate,
                   int photoId) {
        this.prefix = prefix;
        this.name = name;
        this.siteURL = url;
        this.email = email;
        this.party = party;
        this.lastTweet = lastTweet;
        this.endDate = endDate;
        this.photoId = photoId;
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
        if (this.lastTweet.length() >= 50) {
            return this.lastTweet.substring(0, this.lastTweet.substring(0, 46).lastIndexOf(" ")) + " [...]";
        }
        return this.lastTweet;
    }

    public String getLastTweet() {
        return this.lastTweet;
    }

    public int getPhotoID() {
        return this.photoId;
    }

    public String getEndDate() {
        return this.endDate;
    }
}