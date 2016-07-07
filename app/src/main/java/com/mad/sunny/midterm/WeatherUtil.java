/**
 * Name: Sunny Singh
 */

package com.mad.sunny.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sunny on 6/9/16.
 */
public class WeatherUtil {

    static public class WeatherJSONParser {

        static Weather parseWeather(String in) throws JSONException {
            ArrayList<String> descriptions = new ArrayList<String>();
            ArrayList<String> icons = new ArrayList<String>();

            JSONObject root = new JSONObject(in);
            JSONArray weatherJSONArray = root.optJSONArray("list");

            // API will still return a 200 status code when a city
            // isn't found so make sure that the list array exists
            if (weatherJSONArray == null) {
                return null;
            }

            JSONObject jsonObj = weatherJSONArray.getJSONObject(0);
            JSONObject mainObj = jsonObj.getJSONObject("main");
            JSONArray weatherArray = jsonObj.getJSONArray("weather");

            Weather weather = new Weather();

            // Add main data
            weather.setTemperature(Integer.toString(mainObj.getInt("temp")));
            weather.setPressure(Integer.toString(mainObj.getInt("pressure")));
            weather.setHumidity(Integer.toString(mainObj.getInt("humidity")));

            // weather can have multiple items
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherObj = weatherArray.getJSONObject(i);

                descriptions.add(weatherObj.getString("main"));
                icons.add(weatherObj.getString("icon"));
            }

            // Add weather data
            weather.setDescriptions(descriptions);
            weather.setIcons(icons);

            return weather;
        }

    }

}
