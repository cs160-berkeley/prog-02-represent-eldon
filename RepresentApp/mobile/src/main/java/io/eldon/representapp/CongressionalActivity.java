package io.eldon.representapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Moves congressperson data into the Congressional View.
 */

public class CongressionalActivity extends AppCompatActivity {
    private String mZIPCode;
    private String mCounty;
    private Float mObamaVote;
    private Float mRomneyVote;
    private List<CongressPerson> congressPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mZIPCode = getIntent().getExtras().getString("zip");

        congressPeople = testingSenatorData();  //TODO replace with API data

        if (! mZIPCode.isEmpty()) {
            setTitle("Members of Congress for " + mZIPCode);
        } else {
            setTitle("Members of Congress");
        }

        String congressPersonID = getIntent().getExtras().getString("selectCongressPerson");
        if (congressPersonID != null && ! congressPersonID.isEmpty()) {
            Integer congressPersonIndex = Integer.parseInt(congressPersonID);
            Intent getDetailIntent = new Intent(this, DetailActivity.class);
            getDetailIntent.putExtra("congressperson", congressPeople.get(congressPersonIndex));
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
        RecyclerView.Adapter mSenatorsAdapter = new CongressPersonAdapter(this.congressPeople);

        // Send data to the watch
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra(
                "wearSerializedData",
                getWearSerializedData(mCounty, mObamaVote, mRomneyVote, this.congressPeople)
        );
        startService(sendIntent);

        // Show the view on the phone
        mCongressPersonsView.setAdapter(mSenatorsAdapter);
    }

    private String getWearSerializedData(String countyState, Float obamaPrc, Float romneyPrc, List<CongressPerson> congressPeople) {
        String retval = countyState + "\n";
        retval += obamaPrc.toString() + "\n";
        retval += romneyPrc.toString() + "\n";
        for (CongressPerson cp : congressPeople) {  // lol
            retval += cp.getWearSerializedString() + "\n";
        }
        return retval;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putString("zip", mZIPCode);
//        savedInstanceState.putSerializable("congresspeople", new ArrayList<CongressPerson>(congressPeople));
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//        mZIPCode = savedInstanceState.getString("zip");
//        congressPeople = (ArrayList<CongressPerson>) savedInstanceState.getSerializable("congresspeople");
//    }

    List<CongressPerson> testingSenatorData(){
        List<CongressPerson> persons = new ArrayList<CongressPerson>();
        try {
            persons.add(new CongressPerson("Sen.",
                    "Barbara Boxer",
                    new URL("http://boxer.senate.gov"),
                    "Email",
                    "Democrat",
                    "@SenatorBoxer I'm going to write a bunch of words here to see if they're truncated properly.",
                    "11/8/2016",
                    R.drawable.boxer
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
                    R.drawable.feinstein
            ));
            if (mZIPCode.equalsIgnoreCase("91325")) {
                persons.add(new CongressPerson(
                        "Rep.",
                        "Brad Sherman",
                        new URL("http://sherman.house.gov"),
                        "Email",
                        "Democrat",
                        "@BradSherman This morning I testified before the House @Transport Committee on Gas Storage Legislation",
                        "11/8/2016",
                        R.drawable.sherman
                ));
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
                        R.drawable.lee
                ));
                mObamaVote = new Float(84.6);
                mRomneyVote = new Float(12.9);
                mCounty = "Alameda, CA";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
