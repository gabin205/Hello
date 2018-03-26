package com.sfsuse.fa17g16.myandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

import static com.sfsuse.fa17g16.myandroid.R.layout.login;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    Button bSearch, bReset, button;
    //private static String url = "http://192.168.137.190:17016/fa17g16";
    //private static String url = "http://192.168.43.174:17016/fa17g16/";
    //private static String baseurl = "http://192.168.43.174:17016/fa17g16/";
    private static String url = "http://192.168.137.89:17016/fa17g16/";
    private static String baseurl = "http://192.168.137.89:17016/fa17g16/";
    String maxSpace, minSpace, minCost, maxCost;
    String searchplace;
    //String adress, zipCode, rooms, street, cost, header, location, floor, kitchen, smoking;
    ProgressDialog prgDialog;
    ViewGroup mycontent;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initilize
                maxSpace = ((EditText) findViewById(R.id.maxSpace)).getText().toString().trim();
                minSpace = ((EditText) findViewById(R.id.minSpace)).getText().toString().trim();
                minCost = ((EditText) findViewById(R.id.minCost)).getText().toString().trim();
                maxCost = ((EditText) findViewById(R.id.maxCost)).getText().toString().trim();
                searchplace = ((EditText) findViewById(R.id.searchplace)).getText().toString().trim();

                //scheck parameter
                if(searchplace.isEmpty()){
                        Toast.makeText(getApplicationContext(),
                                "Please enter your ZipCode", Toast.LENGTH_LONG)
                                .show();
                    }
                    else if ( Double.parseDouble(maxCost) < Double.parseDouble(minCost) ){
                        Toast.makeText(getApplicationContext(),
                                "max Price cannot be smaller than min price", Toast.LENGTH_LONG)
                                .show();
                    }

                    else if ( Double.parseDouble(maxCost) < Double.parseDouble(minCost) ){
                        Toast.makeText(getApplicationContext(),
                                "max price cannot be smaller than min price", Toast.LENGTH_LONG)
                                .show();
                    }

                    else if ( Double.parseDouble(maxSpace) < Double.parseDouble(minSpace) ){
                        Toast.makeText(getApplicationContext(),
                                "max space cannot be smaller than min space", Toast.LENGTH_LONG)
                                .show();
                    }
                    else {

                        prgDialog = new ProgressDialog(MainActivity.this);
                        // Set Progress Dialog Text
                        prgDialog.setMessage("Please wait...");
                        // Set Cancelable as False
                        prgDialog.setCancelable(false);
                        prgDialog.show();
                        // build jason
                        // Intent json to build
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("minCost", minCost);
                        intent.putExtra("maxCost", maxCost);
                        intent.putExtra("maxSpace", maxSpace);
                        intent.putExtra("minSpace", minSpace);
                        intent.putExtra("searchplace", searchplace);
                        startActivity(intent);

                    }
                }
                //Ende
        });
}

        /** Called when the user taps the Login button */
        public void goToLogin(View view) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            button = (Button) findViewById(R.id.button3);
            startActivity(intent);
        }

    //goToSearch
    /*public void goToSearch() {

        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("minCost", minCost);
        intent.putExtra("maxCost", maxCost);
        intent.putExtra("maxSpace", maxSpace);
        intent.putExtra("minSpace", minSpace);
        intent.putExtra("searchplace", searchplace);
        startActivity(intent);
    }*/

    //search function
    /*public void search(RequestParams params){
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

                //Toast.makeText(getApplicationContext(),response , Toast.LENGTH_LONG).show();
                //if (statusCode == 200) {
                //final ArrayList<HashMap<String,String>> offersList=new ArrayList<>();
                //setContentView(R.layout.activity_search);
                //mycontent.removeAllViews();
                //mycontent.addView(View.inflate(MainActivity.this, R.layout.activity_search, null));
                //lv = (ListView)findViewById(R.id.simpleListView);

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
                        String rps = "Sorry! There are no results for yours query.";
                        Toast.makeText(getApplicationContext(), rps, Toast.LENGTH_LONG).show();
                    } else {
                        //Result
                        for (int i = 0; i < result.length(); i++) {
                            HashMap<String, String> all = new HashMap<>();
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
                            for (int j = 0; j < medias.length(); j++) {
                                JSONObject mediasObject = medias.getJSONObject(j);
                                String id = mediasObject.getString("id");
                                String type = mediasObject.getString("type");
                                String path = mediasObject.getString("path");
                            }

                            System.out.print(adresse);
                            Toast.makeText(getApplicationContext(), header, Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), size, Toast.LENGTH_LONG).show();
                        }
                    }

                        /*final String[] from = { "header", "size", "cost", "rooms", "street","zipcode","location", "id"};
                        int[] to = { R.id.header, R.id.size, R.id.rooms, R.id.street, R.id.zipcode, R.id.location };
                        SimpleAdapter adapter = new SimpleAdapter( MainActivity.this, offersList, R.layout.activity_search, from,to);
                        // updating listview
                        lv.setAdapter(adapter);
                        //perform listView item click event
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // getting values from selected ListItem
                                String offer_id = ((TextView) view.findViewById(R.id.id)).getText()
                                        .toString();
                                RequestParams oid = new RequestParams();
                                oid.put("offer_id",offer_id);
                                showoffer(oid);
                            }
                        });*/

                /*} catch (JSONException e) {
                    e.printStackTrace();
                }
                //}

            }

        });
    }*///end function


}



