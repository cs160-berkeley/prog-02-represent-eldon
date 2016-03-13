package io.eldon.representapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * Moves congressperson data into the Congressional View.
 */

public class CongressionalActivity extends AppCompatActivity {
    private String mZIPCode;
    private Location mLastLocation;
    private String mCounty;
    private String mObamaVote;
    private String mRomneyVote;
    private List<CongressPerson> mCongressPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Members of Congress");

        Bundle extras = getIntent().getExtras();
        mZIPCode = extras.getString("zip");
        mLastLocation = extras.getParcelable("locationParcel");

        //TODO testing only lol
        mObamaVote = "99.9%";
        mRomneyVote = "99.9%";

        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());

        if (! mZIPCode.isEmpty()) {
            //TODO get congresspeople for this zip code
            try {
                addresses = geocoder.getFromLocationName(mZIPCode, 1);
                mLastLocation = new Location("user input");
                mLastLocation.setLatitude(addresses.get(0).getLatitude());
                mLastLocation.setLongitude(addresses.get(0).getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCongressPeople = getCongressPeopleForZip(mZIPCode);
        } else if (mLastLocation != null) {
            //TODO get congresspeople for these coordinates
            mCongressPeople = getCongressPeopleForLocation(mLastLocation);
        } else {
            // uh oh
        }

        try {
            addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mZIPCode.isEmpty()) {
            mZIPCode = addresses.get(0).getPostalCode();
            mZIPCode = mZIPCode == null ? "" : mZIPCode;
        }

        String state = addresses.get(0).getAdminArea();
        mCounty = getCountyStateFromCoordinates(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        ElectionResults electionResults = new ElectionResults(getApplicationContext());
        String[] rawElectionResults = electionResults.getObamaRomneyResults(mCounty);
        mObamaVote = rawElectionResults[0];
        mRomneyVote = rawElectionResults[1];

        Toast.makeText(this, mZIPCode + ", " + mCounty, Toast.LENGTH_LONG).show();

        // Handle a selected congressperson from a wear app, the hacky way
        String congressPersonID = extras.getString("selectCongressPerson");
        if (congressPersonID != null && ! congressPersonID.isEmpty()) {
            Integer congressPersonIndex = Integer.parseInt(congressPersonID);
            Intent getDetailIntent = new Intent(this, DetailActivity.class);
            getDetailIntent.putExtra("congressperson", mCongressPeople.get(congressPersonIndex));
            startActivity(getDetailIntent);
        }

        setContentView(R.layout.activity_congressional);
        RecyclerView mCongressPersonsView = (RecyclerView) findViewById(R.id.congresspeople_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCongressPersonsView.setHasFixedSize(true);  //TODO might need to delete, probably not though
        // use a linear layout manager
        mCongressPersonsView.setLayoutManager(new LinearLayoutManager(this));
        // specify an adapter (see also next example)
        RecyclerView.Adapter mSenatorsAdapter = new CongressPersonAdapter(this.mCongressPeople);
        // Send data to the watch
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra(
                "wearSerializedData",
                getWearSerializedData(mCounty, mObamaVote, mRomneyVote, this.mCongressPeople)
        );
        startService(sendIntent);
        // Show the view on the phone
        mCongressPersonsView.setAdapter(mSenatorsAdapter);
    }

    private String getWearSerializedData(String countyState, String obamaPrc, String romneyPrc, List<CongressPerson> congressPeople) {
        String retval = countyState + "\n";
        retval += obamaPrc + "\n";
        retval += romneyPrc + "\n";
        for (CongressPerson cp : congressPeople) {  // lol
            retval += cp.getWearSerializedString() + "\n";
        }
        return retval;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("zip", mZIPCode);
        savedInstanceState.putParcelable("locationParcel", mLastLocation);
        savedInstanceState.putSerializable("congresspeople", new ArrayList<CongressPerson>(mCongressPeople));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        mZIPCode = savedInstanceState.getString("zip");
        mLastLocation = savedInstanceState.getParcelable("locationParcel");
        mCongressPeople = (ArrayList<CongressPerson>) savedInstanceState.getSerializable("congresspeople");
    }

    private List<CongressPerson> getCongressPeopleForLocation(Location l) {
        double lat = l.getLatitude();
        double lon = l.getLongitude();
        String s = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + lat
                + "&longitude=" + lon
                + "&apikey=" + getApplicationContext().getResources().getText(R.string.sunlight_api_key
        );
        ApiQueryWrapper a = new ApiQueryWrapper(s);
        String result = "";
        try {
            result = a.execute().get();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return CongressPerson.getFromSunlightData(result);
    }

    private List<CongressPerson> getCongressPeopleForZip(String z) {
        String s = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + mZIPCode
                + "&apikey=" + getApplicationContext().getResources().getText(R.string.sunlight_api_key
        );
        ApiQueryWrapper a = new ApiQueryWrapper(s);
        String result = "";
        try {
            result = a.execute().get();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return CongressPerson.getFromSunlightData(result);
    }

    private String getCountyStateFromCoordinates(double lat, double lon) {
        String s = "https://maps.googleapis.com/maps/api/geocode/json?"
        + "latlng=" + lat + "," + lon
        + "&result_type=administrative_area_level_2"
        + "&key=" + getResources().getString(R.string.google_maps_key);
        ApiQueryWrapper a = new ApiQueryWrapper(s);
        String result = "";
        try {
            result = a.execute().get();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        String state = "";
        String county = "";
        try {
            JSONObject j = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray ja = j.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");

            for (int i = 0; i < ja.length(); i++) {
                j = ja.getJSONObject(i);
                if (j.getJSONArray("types").toString().contains("administrative_area_level_2")) {
                    county = j.getString("short_name");
                } else if (j.getJSONArray("types").toString().contains("administrative_area_level_1")) {
                    state = j.getString("short_name");
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
        }
        return county + ", " + state;
    }



    /*
    private List<CongressPerson> testingSenatorData(){
        List<CongressPerson> persons = new ArrayList<CongressPerson>();
        try {
            persons.add(new CongressPerson("Sen.",
                    "Barbara Boxer",
                    new URL("http://boxer.senate.gov"),
                    "Email",
                    "Democrat",
                    "@SenatorBoxer I'm going to write a bunch of words here to see if they're truncated properly.",
                    "11/8/2016",
                    "boxer"
            ));
            persons.get(0).setCommittees(
                    new ArrayList<String>(Arrays.asList(
                            new String[]{
                                    "Senate Select Committee on Ethics",
                                    "Senate Committee on Environment and Public Works on Ethics",
                                    "Senate Committee on Foreign Relations"
                            }))
            );
            persons.get(0).setLegislation(
                    new ArrayList<String>(Arrays.asList(
                            new String[]{
                                    "Child Nicotine Poisoning Prevention Act of 2015",
                                    "Breast Cancer Research Stamp Reauthorization Act of 2015",
                                    "Adoptive Family Relief Act"
                            }))
            );
            persons.add(new CongressPerson("Sen.",
                    "Dianne Feinstein",
                    new URL("http://feinstein.senate.gov"),
                    "Email",
                    "Democrat",
                    "@SenFeinstein I'll be on @NewsHour tonight to discuss things",
                    "11/8/2016",
                    "feinstein"));
            if (mZIPCode != null && mZIPCode.equalsIgnoreCase("91325")) {
                persons.add(new CongressPerson(
                        "Rep.",
                        "Brad Sherman",
                        new URL("http://sherman.house.gov"),
                        "Email",
                        "Democrat",
                        "@BradSherman This morning I testified before the House @Transport Committee on Gas Storage Legislation",
                        "11/8/2016",
                        "sherman"));
                mObamaVote = new Float(60.1);
                mRomneyVote = new Float(30.7);
                mCounty = "Los Angeles, CA";
            } else {
                persons.add(new CongressPerson("Rep.",
                        "Barbara Lee",
                        new URL("http://lee.house.gov"),
                        "Email",
                        "Democrat",
                        "@RepBarbaraLee I ran out of clever ideas for tweet test data.",
                        "11/8/2016",
                        "lee"));
                mObamaVote = new Float(84.6);
                mRomneyVote = new Float(12.9);
                mCounty = "Alameda, CA";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }
    */
}
