package com.sfsuse.fa17g16.myandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import static java.lang.String.valueOf;

public class RegisterActivity extends AppCompatActivity {
    public static String TAG = "RegisterActivity";

    private static String url = Utils.URL;

    Button email_register_button;
    String password, email, last_name, first_name;
    ProgressDialog prgDialog;
    ViewGroup mycontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //for register action
        email_register_button = (Button) findViewById(R.id.email_register_button);
        email_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initilize
                first_name = ((EditText) findViewById(R.id.first_name)).getText().toString().trim();
                last_name = ((EditText) findViewById(R.id.last_name)).getText().toString().trim();
                email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
                password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
                // password wird gehasht
                //String hspwd = rg.generateHash(password);
                Log.i(TAG, "first_name: : "+first_name);
                Log.i(TAG, "last_name: : "+last_name);
                Log.i(TAG, "email: : "+email);
                Log.i(TAG, "password: : "+password);
                RequestParams params = new RequestParams();
                //check the input of form

                if (email.length() == 0 || password.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Please enter all required fields", Toast.LENGTH_LONG)
                            .show();
                } else {

                        Toast.makeText(getApplicationContext(),
                                "Please wait for register", Toast.LENGTH_LONG)
                                .show();
                        prgDialog = new ProgressDialog(RegisterActivity.this);
                        // Set Progress Dialog Text
                        prgDialog.setMessage("Please wait...");
                        // Set Cancelable as False
                        prgDialog.setCancelable(false);
                        prgDialog.show();
                        // creating new user
                        params.put("first_name", first_name);
                        params.put("last_name", last_name);
                        params.put("email", email);
                        params.put("password", password);
                        createUser(params);
                    }//Ende
                }
            });

        }

    public void createUser(RequestParams params) {
        // Show Progress Dialog

        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        //String url = "http://192.168.137.190:17016/fa17g16";
        client.post(url + "user/reg", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();

                if (statusCode == 302) {

                    Toast.makeText(getApplicationContext(), "Your registration was successful", Toast.LENGTH_LONG).show();
                    //setContentView(R.layout.activity_main2);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                    startActivity(intent);}


                // Else display error message
                else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable error) {
                // Hide Progress Dialog
                prgDialog.hide();
                //Log.i(TAG, client.toString());
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    String err = error.getMessage();
                    Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.i(TAG, client.toString());
        Log.i("RegisterActivity", params.toString());
    }

    }
