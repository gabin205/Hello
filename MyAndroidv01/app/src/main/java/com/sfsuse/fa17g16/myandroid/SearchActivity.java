package com.sfsuse.fa17g16.myandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class SearchActivity extends AppCompatActivity {


    Button button;
    ProgressDialog prgDialog;
    //private static String url = "http://192.168.43.174:17016/fa17g16/";
    private static String url = Utils.URL;//"http://192.168.137.89:17016/fa17g16/";
    private String adresse;
    ViewGroup mycontent;
    ListView List;

    SearchItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String maxCost = intent.getStringExtra("maxCost");
        String minCost = intent.getStringExtra("minCost");
        String maxSpace = intent.getStringExtra("maxSpace");
        String minSpace = intent.getStringExtra("minSpace");
        String searchplace = intent.getStringExtra("searchplace");


        adapter = new SearchItemsAdapter(this);

        RequestParams params = new RequestParams();

        Log.i("MainActivity", "searchplace: : " + searchplace);

        prgDialog = new ProgressDialog(SearchActivity.this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        prgDialog.show();
        // build jason
        ////  tu va build le djson differement
        params.put("maxCost", maxCost);
        params.put("minCost", minCost);
        params.put("maxSpace", maxSpace);
        params.put("minSpace", minSpace);
        params.put("searchplace", searchplace);
        //la c est une fontiction qui te  une requete
        search(params);


        ListView listView = (ListView) findViewById(R.id.simpleListView);

    }

    /** Called when the user taps the Login button */
    /*public void goToLogin(View view) {
        Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
        button = (Button) findViewById(R.id.button4);
        startActivity(intent);
    }*/


    //search function
    public void search(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle post = client.post(url + "search/android", params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("MainActivity", "response: " + responseString);

                // Hide Progress Dialog
                prgDialog.hide();
                DuplicateFormatFlagsException error = null;
                String err = error.getMessage();
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                Log.i("MainActivity", "result: " + response);

                try {
                    JSONObject resultObjects = new JSONObject(response);
                    JSONObject realObject = resultObjects.getJSONObject("data");

                    JSONArray equipments = realObject.getJSONArray("equipments");
                    JSONArray result = realObject.getJSONArray("result");
                    //JSONArray formData = realObject.getJSONArray("formdata");
                    //JSONArray data = realObject.getJSONArray("data");


                    //Toast.makeText(getApplicationContext(), equipments.get(0).toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),data.get(0).toString() , Toast.LENGTH_LONG).show();
                    if (resultObjects.length() == 0) {
                        String rps = "Sorry! There are No results for yours query.";
                        Toast.makeText(getApplicationContext(), rps, Toast.LENGTH_LONG).show();
                    } else {
                        //Result
                        for (int i = 0; i < result.length(); i++) {
                            //HashMap<String, String> all = new HashMap<>();
                            JSONObject obj = result.getJSONObject(i);
                            String header = obj.getString("header");
                            String size = obj.getString("size");
                            String cost = obj.getString("cost");
                            String rooms = obj.getString("rooms");

                            //Adresse --> Object
                            JSONObject adresse = obj.getJSONObject("adress");
                            String street = adresse.getString("street");

                            //zipcode --> Object
                            JSONObject zipcodeObject = adresse.getJSONObject("zipcode");
                            String zipcode = zipcodeObject.getString("zipcode");
                            String location = zipcodeObject.getString("location");

                            //media --> array
                            JSONArray medias = obj.getJSONArray("medias");
                            //.mediasObject.getString("path");
                            for (int j = 0; j < medias.length(); j++) {
                                JSONObject mediasObject = medias.getJSONObject(j);
                                String id = mediasObject.getString("id");
                                String type = mediasObject.getString("type");
                                String path = mediasObject.getString("path");
                                String estate_id = mediasObject.getString("estate_id");
                                String created_at = mediasObject.getString("created_at");
                                break;
                            }

                            //Toast.makeText(getApplicationContext(), header, Toast.LENGTH_LONG).show();
                        }
                    }
                    adapter.setResult(result);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

}