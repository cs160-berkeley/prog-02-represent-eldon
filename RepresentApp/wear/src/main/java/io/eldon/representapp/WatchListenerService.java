package io.eldon.representapp;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Stolen by eldon on 3/3/2016.
 */
public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String CONGRESSPEOPLE_DATA = "/setcongresspeople";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        // methods for each access path
        if( messageEvent.getPath().equalsIgnoreCase(CONGRESSPEOPLE_DATA) ) {
            // parse the passed message
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] parsedData = value.split("\n", 4);  // ["County, State", obamaPrc, romneyPRC, [congresspeople]]

            Intent intent = new Intent(this, MainActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("countyState", parsedData[0]);
            intent.putExtra("obamaVote", parsedData[1]);
            intent.putExtra("romneyVote", parsedData[2]);
            intent.putExtra("packedCongressPeople", parseCongressPeople(parsedData[3]));
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }

    public ArrayList<SimpleCongressPerson> parseCongressPeople(String packedCongressPeople) {
        ArrayList<SimpleCongressPerson> cps = new ArrayList<SimpleCongressPerson>();
        String[] congressPeople = packedCongressPeople.split("\n");  // split into serialized SimpleCongressPersons
        for (int i = 0; i < congressPeople.length; i++) {  // unpack into SimpleCongressPerson objects
            cps.add(new SimpleCongressPerson(congressPeople[i], i));
        }
        return cps;
    }
}