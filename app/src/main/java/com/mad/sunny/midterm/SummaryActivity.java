/**
 * Name: Sunny Singh
 */

package com.mad.sunny.midterm;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    // Max number of icons to show
    int ICON_LIMIT = 3;

    TextView textViewTemperature;
    TextView textViewHumidity;
    TextView textViewPressure;
    TextView textViewWeather;
    GridLayout gridLayout;
    GridLayout.LayoutParams imageLayoutParams;

    int iconCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
        textViewHumidity = (TextView) findViewById(R.id.textViewHumidity);
        textViewPressure = (TextView) findViewById(R.id.textViewPressure);
        textViewWeather = (TextView) findViewById(R.id.textViewWeather);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        Weather weather = (Weather) getIntent().getSerializableExtra(MainActivity.KEY_WEATHER);
        String query = getIntent().getStringExtra(MainActivity.KEY_QUERY);
        String units = getIntent().getStringExtra(MainActivity.KEY_UNITS);

        ArrayList<String> descriptions = weather.getDescriptions();
        ArrayList<String> icons = weather.getIcons();
        String textTemperature = (units.equals("metric")) ? weather.getTemperature()+" "+" Celsius" : weather.getTemperature()+" "+" Fahrenheit";
        String textHumidity = weather.getHumidity()+"%";
        String textPressure = weather.getPressure()+" hPa";
        String textWeather = "";

        setTitle(query);

        // Add no more than 3 icons
        for (int i = 0; i < icons.size() && i < ICON_LIMIT; i++) {
            addImage(icons.get(i));
        }

        // Construct weather string
        for (int i = 0; i < descriptions.size(); i++) {
            String comma = i != 0 ? ", " : "";
            textWeather += comma+descriptions.get(i);
        }

        textViewTemperature.setText(textTemperature);
        textViewHumidity.setText(textHumidity);
        textViewPressure.setText(textPressure);
        textViewWeather.setText(textWeather);
    }

    private void addImage(String icon) {
        Handler handler;
        final ImageView image = new ImageView(this);

        imageLayoutParams = new GridLayout.LayoutParams();
        imageLayoutParams.height = 250;
        imageLayoutParams.width = 250;
        imageLayoutParams.rightMargin = 5;
        imageLayoutParams.topMargin = 5;
        imageLayoutParams.setGravity(Gravity.CENTER);
        imageLayoutParams.columnSpec = GridLayout.spec(iconCount, GridLayout.CENTER);
        image.setLayoutParams(imageLayoutParams);

        // Callback
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.obj != null) {
                    // Picasso instance to insert downloaded
                    // image into an ImageView
                    RequestCreator pic = (RequestCreator) msg.obj;
                    pic.into(image);

                    gridLayout.addView(image);

                    iconCount++;
                }
                return false;
            }
        });

        new GetIconAsyncTask(this, handler).execute(icon);
    }
}
