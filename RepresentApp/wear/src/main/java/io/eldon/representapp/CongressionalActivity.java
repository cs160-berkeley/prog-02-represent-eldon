package io.eldon.representapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.util.ArrayList;

public class CongressionalActivity extends Activity {

    private TextView mTextView;
    private ArrayList<SimpleCongressPerson> mCongressPeople;
    private String mCountyState;
    private String mObamaVote;
    private String mRomneyVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String serializedCongressPeople = extras.getString("packedCongressPeople");
            mCongressPeople = parseCongressPeople(serializedCongressPeople);
            mCountyState = extras.getString("countyState");
            mObamaVote = extras.getString("obamaVote");
            mRomneyVote = extras.getString("romneyVote");
        }

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    private ArrayList<SimpleCongressPerson> parseCongressPeople(String wearSerializedString) {
        String[] serializedCongressPeople = wearSerializedString.split("\n");
        ArrayList<SimpleCongressPerson> congressPeople = new ArrayList<SimpleCongressPerson>();
        for (int i = 0; i < serializedCongressPeople.length; i++) {
            congressPeople.add(new SimpleCongressPerson(serializedCongressPeople[i], i));
        }
        return congressPeople;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("countyState", mCountyState);
        savedInstanceState.putString("obamaVote", mObamaVote);
        savedInstanceState.putString("romneyVote", mRomneyVote);
        savedInstanceState.putSerializable("congresspeople", mCongressPeople);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        mCongressPeople = (ArrayList<SimpleCongressPerson>) savedInstanceState.getSerializable("congresspeople");
        mCountyState = savedInstanceState.getString("countyState");
        mObamaVote = savedInstanceState.getString("obamaVote");
        mRomneyVote = savedInstanceState.getString("romneyVote");

    }

}
