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

 public static final String MAX_COST = "maxCost";
    Button button;
    ProgressDialog prgDialog;
    //private static String url = "http://192.168.43.174:17016/fa17g16/";
    private static String url = Utils.URL;//"http://192.168.137.89:17016/fa17g16/";
    private String adresse;
    private ViewGroup mycontent;
    private ListView List;
    private String description, build_at, equipment_type, equipment_name, first_name, last_name, email;

    private SearchItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String maxCost = intent.getStringExtra(MAX_COST);
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

    }

    /** Called when the user taps the Login button */
    /*public void goToLogin(View view) {
        Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
        button = (Button) findViewById(R.id.button4);
        startActivity(intent);
    }*/


    //search function
    private void search(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle post = client.post(url + "search/android", params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("MainActivity", "response: " + responseString);

                // Hide Progress Dialog
                prgDialog.hide();
//                DuplicateFormatFlagsException error = throwable.;
//                String err = error.getMessage();
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                Log.i("MainActivity", "result: " + response);

                try {
                    JSONObject resultObjects = new JSONObject(response);
                    JSONObject realObject = resultObjects.getJSONObject("data");

                 //Toast.makeText(getApplicationContext(), equipments.get(0).toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),data.get(0).toString() , Toast.LENGTH_LONG).show();
                    if (resultObjects.length() == 0 || realObject.length() == 0) {
                        String rps = "Sorry! There are No results for yours query.";
                        Toast.makeText(getApplicationContext(), rps, Toast.LENGTH_LONG).show();
                    } else {
                        JSONArray equipments = realObject.getJSONArray("equipments");
                        JSONArray result = realObject.getJSONArray("result");
                        //JSONArray formData = realObject.getJSONArray("formdata");
                        //JSONArray data = realObject.getJSONArray("data");
                        adapter.setResult(result);
                        ListView listView = (ListView) findViewById(R.id.simpleListView);

                        //Initialisation de la liste avec les données for real_estate
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // intent pour ouvrir les details
                                //Object obj = ((ListView)parent).getItemAtPosition(position);
                                //SearchItem item = (SearchItem)obj;

                                SearchItem item = adapter.getErgebnisListe().get(position);
                                // intent putExtra()
                                Intent intent = new Intent(SearchActivity.this, EstateActivity.class);
                                intent.putExtra("header", item.getHeader());
                                intent.putExtra("cost", item.getCost());
                                intent.putExtra("street", item.getStreet());
                                intent.putExtra("size", item.getSize());
                                intent.putExtra("rooms", item.getRooms());
                                intent.putExtra("zipcode", item.getZipcode());
                                intent.putExtra("location", item.getLocation());
                                intent.putExtra("description", item.getDescription());
                                intent.putExtra("build_at", item.getBuild_at());
                                intent.putExtra("first_name", item.getFirst_name());
                                intent.putExtra("last_name", item.getLast_name());
                                intent.putExtra("email", item.getEmail());
                                intent.putExtra("seller_id",item.getSeller_id());
                                intent.putExtra("realestate_id",item.getRealestate_id());
                                //
                                intent.putExtra("images", item.getMedias());
                                intent.putExtra("equipements", item.getequipmentItem());
                                startActivity(intent);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

}
