package io.eldon.representapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZIPCode = getIntent().getExtras().getString("zip");
        Log.e("mZIPCode is now:", mZIPCode);
        if (! mZIPCode.isEmpty()) {
            setTitle("Members of Congress for " + mZIPCode);
        } else {
            setTitle("Members of Congress");
        }

        setContentView(R.layout.activity_congressional);
        RecyclerView mSenatorsView = (RecyclerView) findViewById(R.id.congresspeople_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mSenatorsView.setHasFixedSize(true);  //TODO might need to delete, probably not though

        // use a linear layout manager
//        final LinearLayoutManager senatorLayoutManager = new LinearLayoutManager(this);
//        senatorLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mSenatorsView.setLayoutManager(senatorLayoutManager);

        mSenatorsView.setLayoutManager(new LinearLayoutManager(this));


        // specify an adapter (see also next example)
        RecyclerView.Adapter mSenatorsAdapter = new CongressPersonAdapter(testingSenatorData()); //TODO replace with API data
        mSenatorsView.setAdapter(mSenatorsAdapter);
    }


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
                            new String[] {
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
            persons.add(new CongressPerson("Rep.",
                    "Barbara Lee",
                    new URL("http://lee.house.gov"),
                    "Email",
                    "Democrat",
                    "@RepBarbaraLee I ran out of clever ideas for tweet test data.",
                    "11/8/2016",
                    R.drawable.lee
            ));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
