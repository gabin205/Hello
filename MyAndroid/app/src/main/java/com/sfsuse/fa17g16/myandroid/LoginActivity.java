package com.sfsuse.fa17g16.myandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.READ_CONTACTS;




/**
 * A login screen that offers login via email/password.
 */


public class LoginActivity extends AppCompatActivity {

    String email;
    String password;
    View login_progress, login_form;
    ProgressDialog prgDialog;
    Button registerButton, email_register_button, email_sign_in_button, button;
    ViewGroup mycontent;

    private static String url = Utils.URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LoginActivity", "Beginn LoginActivity: ");

        setContentView(R.layout.activity_login);
        // Set up the login form.
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email_sign_in_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LoginActivity", "store userdata: ");
                // Store values at the time of the login attempt.
                email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
                password = ((EditText) findViewById(R.id.password)).getText().toString().trim();

                RequestParams params = new RequestParams();
                Log.i("LoginActivity", "email: "+email);
                Log.i("LoginActivity", "password: "+password);

                if (email.length() == 0 || password.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Please enter all required fields", Toast.LENGTH_LONG)
                            .show();
                } else {

                    prgDialog = new ProgressDialog(LoginActivity.this);
                    // Set Progress Dialog Text
                    prgDialog.setMessage("Please wait for login...");
                    // Set Cancelable as False
                    prgDialog.setCancelable(false);
                    prgDialog.show();
                    // creating new user
                    params.put("email", email);
                    params.put("password", password);
                    login(params);
                }
            }
        });

        login_form = findViewById(R.id.login_form);
        login_progress = findViewById(R.id.login_progress);
        registerButton = (Button) findViewById(R.id.email_register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Shows the progress UI and hides the login form.
     */

    public void login(RequestParams params) {
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url + "user/login", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                // Hide Progress Dialog
                prgDialog.hide();
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
                     Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                if (statusCode == 200) {
                    try {
                        Log.i("LoginActivity", response);
                        JSONObject obj = new JSONObject(response);
                        Boolean login = obj.getBoolean("login");
                        String message = obj.getString("message");
                        int user_id = obj.getInt("user_id");
                        // new IntenT

                        if(login) {
                            //login by real_estate activity
                            Intent intent;
                            intent = getIntent();
                            Bundle bundle = intent.getExtras();
                            String activity = bundle.getString("estate");
                            ArrayList<String> equipementListe= bundle.getStringArrayList("equipements");
                            ArrayList<String> imagesListe=bundle.getStringArrayList("images");
                            String header = bundle.getString("header");
                            String size = bundle.getString("size");
                            String cost = bundle.getString("cost");
                            String rooms = bundle.getString("rooms");
                            String zipcode = bundle.getString("zipcode");
                            String location = bundle.getString("location");
                            String street = bundle.getString("street");
                            String description = bundle.getString("description");
                            String build_at = bundle.getString("build_at");
                            String first_name = bundle.getString("first_name");
                            String last_name = bundle.getString("last_name");
                            String emails = bundle.getString("email");
                            String seller_id = bundle.getString("seller_id");
                            String realestate_id = bundle.getString("realestate_id");
                            Toast.makeText(getApplicationContext(), "You was successfully logged in", Toast.LENGTH_LONG).show();

                          if(activity.matches("first")) {

                              intent = new Intent(LoginActivity.this, EstateActivity.class);

                              intent.putExtra("estate","first");
                              intent.putExtra("equipements", equipementListe);
                              intent.putExtra("images",imagesListe );
                              intent.putExtra("header", header);
                              intent.putExtra("cost", cost);
                              intent.putExtra("street", street);
                              intent.putExtra("size", size);
                              intent.putExtra("rooms", rooms);
                              intent.putExtra("zipcode", zipcode);
                              intent.putExtra("location", location);
                              intent.putExtra("description", description);
                              intent.putExtra("build_at", build_at);
                              intent.putExtra("first_name", first_name);
                              intent.putExtra("last_name", last_name);
                              intent.putExtra("email", emails);
                              intent.putExtra("seller_id", seller_id);
                              intent.putExtra("realestate_id", realestate_id);

                              SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                              SharedPreferences.Editor editor = preferences.edit();
                              editor.putString("email",email);
                              editor.putBoolean("islogged",login);
                              editor.putString("pw", password);
                              editor.putInt("user_id", user_id);
                              editor.putString(Utils.HEADER_NAME, headers[9].getName());
                              editor.putString(Utils.HEADER_VALUE, headers[9].getValue());
                              editor.commit();
                              startActivity(intent);
                          }
                          else{

                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", email);
                            editor.putBoolean("islogged", login);
                            editor.putString("pw", password);
                            editor.putInt("user_id", user_id);
                            editor.apply();

                            startActivity(intent);}

                        }else {
                            Toast.makeText(getApplicationContext(), "bad credentiels, try against or first register", Toast.LENGTH_LONG).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }

            }//fin

        });
        Log.i("LoginActivity", params.toString());
    }

}



