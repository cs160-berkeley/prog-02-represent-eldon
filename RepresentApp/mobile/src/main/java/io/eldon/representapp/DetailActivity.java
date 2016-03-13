package io.eldon.representapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private CongressPerson mCongressPerson;
    private TextView mName;
    private TextView mParty;
    private TextView mEndDate;
    private TextView mCommittees;
    private TextView mLegislation;
    private ImageView mPhotoID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCongressPerson = (CongressPerson) getIntent().getExtras().getSerializable("congressperson");
        setContentView(R.layout.activity_detail);

        mName = (TextView) findViewById(R.id.person_name);
        mParty = (TextView) findViewById(R.id.person_party);
        mEndDate = (TextView) findViewById(R.id.person_enddate);
        mCommittees = (TextView) findViewById(R.id.person_committees);
        mLegislation = (TextView) findViewById(R.id.person_legislation);
        mPhotoID = (ImageView) findViewById(R.id.person_photo);

        mCongressPerson.setCommittees(getCommittees(mCongressPerson));
        mCongressPerson.setLegislation(getLegislation(mCongressPerson));

        mName.setText(mCongressPerson.getName());
        mParty.setText(mCongressPerson.getParty());
        mEndDate.setText("Term ends " + mCongressPerson.getEndDate() + ".");
        mCommittees.setText(mCongressPerson.getCommittees());
        mLegislation.setText(mCongressPerson.getLegislation());
        //mPhotoID.setImageResource(mCongressPerson.getPhotoID());
        Picasso.with(getApplicationContext()).load(
                "https://theunitedstates.io/images/congress/225x275/"
                        + mCongressPerson.getBioguideID()
                        + ".jpg").into(mPhotoID
        );
    }

    private ArrayList<String> getCommittees(CongressPerson p) {
        ArrayList<String> results = new ArrayList<>();
        ApiQueryWrapper a = new ApiQueryWrapper("http://congress.api.sunlightfoundation.com/committees?" +
                "member_ids=" + mCongressPerson.getBioguideID() +
                "&apikey=" + getResources().getString(R.string.sunlight_api_key)
        );
        String result = "";
        try {
            result = a.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject j = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray ja = j.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                j = ja.getJSONObject(i);
                results.add(j.getString("name"));
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
        }
        return results;
    }

    private ArrayList<String> getLegislation(CongressPerson p) {
        ArrayList<String> results = new ArrayList<>();
        ApiQueryWrapper a = new ApiQueryWrapper("http://congress.api.sunlightfoundation.com/bills?" +
                "sponsor_id=" + mCongressPerson.getBioguideID() +
                "&apikey=" + getResources().getString(R.string.sunlight_api_key)
        );
        String result = "";
        try {
            result = a.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject j = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray ja = j.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                j = ja.getJSONObject(i);
                if (j.getString("short_title") == "null" || j.getString("introduced_on") == "null") {
                    continue;
                }
                results.add(j.getString("introduced_on") + " " + j.getString("short_title"));
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
        }
        return results;
    }
}
