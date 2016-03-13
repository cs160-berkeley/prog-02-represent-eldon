package io.eldon.representapp;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by eldon on 3/12/2016.
 */
public class ElectionResults extends Activity {
    private JSONObject mRawVotingData;

    private static ElectionResults ourInstance = new ElectionResults();

    public static ElectionResults getInstance() {
        return ourInstance;
    }

    private ElectionResults() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("file_name.json");
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
