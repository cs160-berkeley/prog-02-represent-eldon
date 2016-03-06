package io.eldon.representapp;

import java.io.Serializable;

/**
 * Created by eldon on 3/4/2016.
 */
public class SimpleCongressPerson implements Serializable {
    private String name;
    private String party;
    private int id;

    public SimpleCongressPerson(String WearSerializedString, int id) {
        String[] results = WearSerializedString.split(",");
        this.name = results[0];
        this.party = results[1];
        this.id = id;
    }

    public SimpleCongressPerson(String name, String party, int id) {
        this.name = name;
        this.party = party;
        this.id = id;
    }

    public String getPartyColor() {
        if (this.party.equalsIgnoreCase("Democrat")) {
            return "#02bfe7";
        } else if (this.party.equalsIgnoreCase("Republican")) {
            return "#e31c3d";
        } else {
            return "#F1F1F1";
        }
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public int getId() {
        return id;
    }
}
