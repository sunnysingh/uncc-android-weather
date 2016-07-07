/**
 * Name: Sunny Singh
 */

package com.mad.sunny.midterm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String KEY_WEATHER = "weather";
    static final String KEY_QUERY = "query";
    static final String KEY_UNITS = "units";

    EditText editTextSearch;
    Switch switchMetric;
    Button buttonCheck;
    ProgressDialog progressDialog;

    Handler handler;

    private boolean hasInternet = false;

    String query;
    String units;
    Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        switchMetric = (Switch) findViewById(R.id.switchMetric);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);

        // Setup ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving Data...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        hasInternet = isConnected();

        // Callback
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                progressDialog.dismiss();

                if (msg.obj == null) {
                    Toast.makeText(MainActivity.this, "Error: Invalid Query", Toast.LENGTH_SHORT).show();
                    return false;
                }

                weather = (Weather) msg.obj;

                startSummaryActivity();

                return false;
            }
        });

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasInternet) {
                    query = editTextSearch.getText().toString();
                    units = switchMetric.isChecked() ? "metric" : "imperial";

                    progressDialog.show();

                    new GetDataAsyncTask(handler).execute(query, units);
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void startSummaryActivity() {
        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
        intent.putExtra(KEY_WEATHER, weather);
        intent.putExtra(KEY_QUERY, query);
        intent.putExtra(KEY_UNITS, units);
        startActivity(intent);
    }
}
