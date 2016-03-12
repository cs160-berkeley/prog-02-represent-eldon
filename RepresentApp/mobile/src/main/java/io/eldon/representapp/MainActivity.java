package io.eldon.representapp;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Button mSubmitZipBtn;
    private Button mMyLocationBtn;  //TODO do something about this for part c
    private TextView mZIPEntry;

    LocationManager mLocationManager;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mSubmitZipBtn = (Button) findViewById(R.id.in_zipcode_btn);
        mMyLocationBtn = (Button) findViewById(R.id.in_mylocation_btn);
        mZIPEntry = (TextView) findViewById(R.id.in_zipcode_text);

        mSubmitZipBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CongressionalActivity.class);
                myIntent.putExtra("zip", mZIPEntry.getText().toString());
                MainActivity.this.startActivity(myIntent);
            }
        });

        mMyLocationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation == null) {
                    Toast.makeText(getApplicationContext(), "Couldn't get last location", Toast.LENGTH_SHORT).show();
                } else {
                    Intent myIntent = new Intent(MainActivity.this, CongressionalActivity.class);
                    myIntent.putExtra("zip", "");
                    myIntent.putExtra("locationParcel", (Parcelable) mLastLocation);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // meh
        Toast.makeText(getApplicationContext(), connectionResult.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
