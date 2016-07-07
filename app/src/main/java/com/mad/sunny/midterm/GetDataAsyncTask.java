/**
 * Name: Sunny Singh
 */

package com.mad.sunny.midterm;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class GetDataAsyncTask extends AsyncTask<String, Void, String> {

    String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/city?APPID=340d1f65d73e4f4daa06d1b312bd569f";
    Handler handler;

    public GetDataAsyncTask(Handler h) {
        handler = h;
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(baseUrl+"&q="+ URLEncoder.encode(params[0], "UTF-8")+"&units="+params[1]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        if (json == null) {
            Message msg = new Message();
            msg.obj = null;
            handler.sendMessage(msg);
            return;
        }
        try {
            Message msg = new Message();
            msg.obj = WeatherUtil.WeatherJSONParser.parseWeather(json);
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
