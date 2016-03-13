package io.eldon.representapp;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by eldon on 3/12/2016.
 */
public class ElectionResults {
    private JSONObject mRawVotingData;

    public ElectionResults(Context c) {
        String json = null;
        try {
            InputStream is = c.getResources().openRawResource(R.raw.condensed_electioncounty2012);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            mRawVotingData = (JSONObject) new JSONTokener(json).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] getObamaRomneyResults(String countyState) {
        try {
            JSONObject j = mRawVotingData.getJSONObject(countyState);
            return new String[]{j.getString("obama"), j.getString("romney")};
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
}
