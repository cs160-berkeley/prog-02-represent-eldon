package io.eldon.representapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mSubmitZipBtn;
    private Button mMyLocationBtn;
    private TextView mZIPEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubmitZipBtn = (Button)findViewById(R.id.in_zipcode_btn);
        mMyLocationBtn = (Button)findViewById(R.id.in_mylocation_btn);
        mZIPEntry = (TextView)findViewById(R.id.in_zipcode_text);

        mSubmitZipBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CongressionalActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
