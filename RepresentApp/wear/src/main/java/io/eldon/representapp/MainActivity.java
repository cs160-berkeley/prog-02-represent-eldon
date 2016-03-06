package io.eldon.representapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends WearableActivity {
    private TextView mName;
    private TextView mParty;
    private LinearLayout mContainer;
    private ArrayList<SimpleCongressPerson> mCongressPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (TextView) findViewById(R.id.person_name);
        mParty = (TextView) findViewById(R.id.person_party);
        mContainer = (LinearLayout) findViewById(R.id.container);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String serializedCongressPeople = extras.getString("packedCongressPeople");
            mCongressPeople = parseCongressPeople(serializedCongressPeople);
            mParty.setText(extras.getString("countyState"));
            mName.setText(mCongressPeople.get(0).getName());
            mContainer.setBackgroundColor(Color.parseColor(mCongressPeople.get(0).getPartyColor()));
        }



//        mContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                startService(sendIntent);
//            }
//        });
    }

    private ArrayList<SimpleCongressPerson> parseCongressPeople(String wearSerializedString) {
        String[] serializedCongressPeople = wearSerializedString.split("\n");
        ArrayList<SimpleCongressPerson> congressPeople = new ArrayList<SimpleCongressPerson>();
        for (int i = 0; i < serializedCongressPeople.length; i++) {
            congressPeople.add(new SimpleCongressPerson(serializedCongressPeople[i], i));
        }
        return congressPeople;
    }
}
