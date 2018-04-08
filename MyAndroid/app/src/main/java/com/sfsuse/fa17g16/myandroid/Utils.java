package com.sfsuse.fa17g16.myandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Gabin on 18.03.2018.
 */

public class Utils {
    public static final String HOST = "192.168.137.66";
    public static final String URL = "http://"+HOST+":17016/fa17g16/";


    // cookies
    public static final String HEADER_NAME = "set-cookie-name";
    public static final String HEADER_VALUE = "set-cookie-value";
    ProgressDialog prgDialog;
    JSONObject realObject;
    public JSONObject search(RequestParams params, String url, final String table){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle post = client.post(url , params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("MainActivity", "response: " + responseString);

                // Hide Progress Dialog
                prgDialog.hide();
//                DuplicateFormatFlagsException error = throwable.;
//                String err = error.getMessage();
               // Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }



            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                //prgDialog.hide();
                Log.i("MainActivity", "result: " + response);

                try {


                    JSONObject resultObjects = new JSONObject(response);
                    JSONObject realObjectr = resultObjects.getJSONObject("data");
                    realObject=realObjectr.getJSONObject(table);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        });
        return realObject;  }

}
