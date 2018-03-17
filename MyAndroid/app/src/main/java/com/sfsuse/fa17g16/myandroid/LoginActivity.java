package com.sfsuse.fa17g16.myandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

//####standard generiert########

  public class LoginActivity extends AppCompatActivity {


    // UI references.
    String email;
    String password;
    View login_progress, login_form;
    ProgressDialog prgDialog;
    Button registerButton, email_register_button, email_sign_in_button, button;
    ViewGroup mycontent;

    //private static String url = "http://192.168.73.168:17016/fa17g16";
    //static String url = "http://192.168.73.168:17016/fa17g16";
    static String url = "http://192.168.137.89:17016/fa17g16";
    //static String url = "http://192.168.43.174:17016/fa17g16";
    //static String baseurl = "http://192.168.73.168:17016/fa17g16";
    //private static String url = "http://192.168.73.168:17016/fa17g16";


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

                    Toast.makeText(getApplicationContext(),
                            "Please wait for login", Toast.LENGTH_LONG)
                            .show();
                    prgDialog = new ProgressDialog(LoginActivity.this);
                    // Set Progress Dialog Text
                    prgDialog.setMessage("Please wait...");
                    // Set Cancelable as False
                    prgDialog.setCancelable(false);
                    prgDialog.show();
                    // creating new user
                    params.put("email", email);
                    params.put("password", password);
                    login(params);
                }//Ende
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


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    //ende

    /**
     * Shows the progress UI and hides the login form.
     */

        public void login(RequestParams params) {
//     FunctionLogin logion= new  FuntionLogin()
//  logion.login(params , url, layout);
// TODO: attempt authentication against a network service.
            // Show Progress Dialog
            prgDialog.show();
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(url + "/user/login", params, new TextHttpResponseHandler() {//url peut devenir parametre
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
                        DuplicateFormatFlagsException error = null;
                        //String err = error.getMessage();
                      //  Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    prgDialog.hide();
                    if (statusCode == 200) {
                        Toast.makeText(getApplicationContext(), "You was successfully logged in", Toast.LENGTH_LONG).show();
                        try {
                            Log.i("LoginActivity", response);
                            JSONObject obj = new JSONObject(response);
                            Boolean login = obj.getBoolean("login");

                            if(login){
                                setContentView(R.layout.activity_main2);//r.layout.. peut etre
                                //mycontent.addView(View.inflate(LoginActivity.this, R.layout.activity_main2, null));
                            }
                            else {
                                setContentView(R.layout.activity_register);
                                /*mycontent.removeAllViews();
                                mycontent.addView(View.inflate(LoginActivity.this, R.layout.activity_register, null));*/

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }


                }
                //ende

            });
            Log.i("LoginActivity", params.toString());
        }

}



