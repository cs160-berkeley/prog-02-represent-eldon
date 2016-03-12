package io.eldon.representapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eldon on 3/12/2016.
 */
class ApiQueryWrapper extends AsyncTask<Void, Void, String> {
    private URL mUrl;
    private Exception exception;

    public ApiQueryWrapper(String mUrl) {
        super();
        try {
            this.mUrl = new URL(mUrl);
        } catch (MalformedURLException e) {
            //TODO don't know if this will work with null context lol
            Toast.makeText(null, "Invalid URL: " + mUrl, Toast.LENGTH_LONG).show();
        }
    }

    protected String doInBackground(Void... nul) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally {
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        Log.i("INFO", response);
    }
}
