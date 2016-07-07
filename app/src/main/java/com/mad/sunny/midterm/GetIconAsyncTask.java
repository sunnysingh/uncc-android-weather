/**
 * Name: Sunny Singh
 */

package com.mad.sunny.midterm;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;

/**
 * Created by sunny on 6/9/16.
 */
public class GetIconAsyncTask extends AsyncTask<String, Void, RequestCreator> {

    private Context context;
    private Handler handler;

    private String baseURL = "http://openweathermap.org/img/w/";

    public GetIconAsyncTask (Context c, Handler h) {
        context = c;
        handler = h;
    }

    @Override
    protected RequestCreator doInBackground(String... params) {
        String icon = params[0];
        return Picasso.with(context).load(baseURL+icon+".png");
    }

    @Override
    protected void onPostExecute(RequestCreator pic) {
        super.onPostExecute(pic);
        Message msg = new Message();
        msg.obj = pic;
        handler.sendMessage(msg);
    }
}
