package io.eldon.representapp;

import java.net.URL;

/**
 * Created by eldon on 3/1/2016.
 *
 * Represents members of congress.
 */
class CongressPerson {
    private String name;
    private URL siteURL;
    private String email;
    private String party;
    private String lastTweet;
    private String prefix;
    private int photoId;

    CongressPerson(String prefix,
                   String name,
                   URL url,
                   String email,
                   String party,
                   String lastTweet,
                   int photoId) {
        this.prefix = prefix;
        this.name = name;
        this.siteURL = url;
        this.email = email;
        this.party = party;
        this.lastTweet = lastTweet;
        this.photoId = photoId;
    }

    /* Getters for private fields */

    public String getName() {
        return this.name;
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
}