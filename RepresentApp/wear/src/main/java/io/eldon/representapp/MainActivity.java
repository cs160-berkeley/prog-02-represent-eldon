package io.eldon.representapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {
    private TextView mName;
    private TextView mParty;
    private LinearLayout mContainer;
    private SimpleCongressPerson mCongressPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (TextView) findViewById(R.id.person_name);
        mParty = (TextView) findViewById(R.id.person_party);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String catName = extras.getString("CAT_NAME");
        }

        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                startService(sendIntent);
            }
        });
    }
}
