package io.eldon.representapp;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Stolen by eldon on 3/3/2016.
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String NEW_ZIP = "/new_zip";
    private static final String SELECT_CONGRESSPERSON = "/select_congressperson";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());

        // Value contains the String we sent over in WatchToPhoneService
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        if (messageEvent.getPath().equalsIgnoreCase(SELECT_CONGRESSPERSON)) {
            Intent sendIntent = new Intent(getBaseContext(), CongressionalActivity.class);
            sendIntent.putExtra("selectCongressPerson", value);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(sendIntent);
        } else if (messageEvent.getPath().equalsIgnoreCase(NEW_ZIP)) {
            Intent myIntent = new Intent(getBaseContext(), CongressionalActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myIntent.putExtra("zip", value);
            startActivity(myIntent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
