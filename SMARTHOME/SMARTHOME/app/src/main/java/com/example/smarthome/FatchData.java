package com.example.smarthome;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FatchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    public static  String value;
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            //URL url = new URL("https://api.thingspeak.com/channels/1620663/feeds.json?api_key=AGGZ6EBKBDKEIHVJ&results=2");
            URL url = new URL("https://api.thingspeak.com/channels/1627758/feeds.json?api_key=HMRVT3S71JL3I186&results=2");
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection() ;
            InputStream inputStream = httpurlconnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "" ;
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line ;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            JSONObject jo = new JSONObject(data);
            JSONArray sys  = jo.getJSONArray("feeds");

            this.value= sys.getJSONObject(1).getString("field1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        float val = Float.parseFloat(value);
        //val = val*10 ;
        activity_gar.gauge.setValue((int)val);
        activity_gar.value_ppm.setText(Float.toString(val));
        activity_gar.click.setVisibility(View.VISIBLE);

        set_values(this.value);

    }





    public void  set_values(String Value) {
        Float val = Float.parseFloat(Value);
        if (val < 1500) {
            if (activity_gar.bd.isPlaying()) {
                activity_gar.bd.stop();
            }
            activity_gar.gauge.setEndValue(3000);
            activity_gar.txt_end.setText("3000");
            activity_gar.condi.setText("Fresh Air");
            activity_gar.condi.setTextColor(Color.GREEN);
        } else if (val < 3000) {
            if (!activity_gar.bd.isPlaying()) {
                activity_gar.bd.start();
            }
            activity_gar.gauge.setEndValue(5000);
            activity_gar.txt_end.setText("3000");
            activity_gar.condi.setText("Modarte Air");
            activity_gar.condi.setTextColor(Color.YELLOW);
        } else {
            if (val >= 3000) {
                activity_gar.gauge.setEndValue(7000);
                activity_gar.txt_end.setText("7000");
            } else {
                activity_gar.gauge.setEndValue(3000);
                activity_gar.txt_end.setText("3000");
            }
            activity_gar.condi.setText("Harmful Air");
            activity_gar.condi.setTextColor(Color.RED);
        }
    }
}
