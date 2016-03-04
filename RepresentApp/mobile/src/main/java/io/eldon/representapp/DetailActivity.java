package io.eldon.representapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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

        mName.setText(mCongressPerson.getName());
        mParty.setText(mCongressPerson.getParty());
        mEndDate.setText("Term ends " + mCongressPerson.getEndDate() + ".");
        mCommittees.setText(mCongressPerson.getCommittees());
        mLegislation.setText(mCongressPerson.getLegislation());
        mPhotoID.setImageResource(mCongressPerson.getPhotoID());

    }
}
