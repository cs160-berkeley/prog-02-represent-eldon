package io.eldon.representapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * Moves congressperson data into the Congressional View.
 */

public class CongressionalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_congressional);
        RecyclerView mSenatorsView = (RecyclerView) findViewById(R.id.senators_view);
        RecyclerView mRepresentativesView = (RecyclerView) findViewById(R.id.representatives_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mSenatorsView.setHasFixedSize(true);  //TODO might need to delete
        mRepresentativesView.setHasFixedSize(true);  //TODO might need to delete this too

        // use a linear layout manager
//        final LinearLayoutManager senatorLayoutManager = new LinearLayoutManager(this);
//        senatorLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mSenatorsView.setLayoutManager(senatorLayoutManager);

        mSenatorsView.setLayoutManager(new LinearLayoutManager(this));
        mRepresentativesView.setLayoutManager(new LinearLayoutManager(this));


        // specify an adapter (see also next example)
        RecyclerView.Adapter mSenatorsAdapter = new CongressPersonAdapter(testingSenatorData()); //TODO replace with API data
        mSenatorsView.setAdapter(mSenatorsAdapter);
        RecyclerView.Adapter mRepresentativesAdapter = new CongressPersonAdapter(testingRepresentativeData());
        mRepresentativesView.setAdapter(mRepresentativesAdapter);
    }


    List<CongressPerson> testingSenatorData(){
        List<CongressPerson> persons = new ArrayList<CongressPerson>();
        try {
            persons.add(new CongressPerson("Barbara Boxer", new URL("http://boxer.senate.gov"), "Email", "Democrat", "I'm going to write a bunch of words here to see if they're truncated properly.", R.drawable.boxer));
            persons.add(new CongressPerson("Dianne Feinstein", new URL("http://feinstein.senate.gov"), "Email", "Democrat", "I'll be on @NewsHour tonight to discuss things", R.drawable.feinstein));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    List<CongressPerson> testingRepresentativeData(){
        List<CongressPerson> persons = new ArrayList<CongressPerson>();
        try {
            persons.add(new CongressPerson("Barbara Lee", new URL("http://lee.house.gov"), R.drawable.lee));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
