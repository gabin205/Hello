package com.sfsuse.fa17g16.myandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
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

    Button bSearch, bReset, button, btn_home;
    SharedPreferences prefs;
    private static String url = Utils.URL;
    String maxSpace, minSpace, minCost, maxCost;
    String searchplace;
    ProgressDialog prgDialog;
    ViewGroup mycontent;
    ListView lv;
    boolean login;
    String name;
    TextView em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button3);
        //em=(TextView)findViewById(R.id.textView5);
        prefs= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        login=prefs.getBoolean("islogged",false);
        /*String emailSa="saaa";

        emailSa= emailSa==null?"fonction":prefs.getString("email",null);
       em.setText(emailSa);*/
        bSearch = (Button) findViewById(R.id.bSearch);
        if(login){ button.setText("Logout");

        }
        else {
            button.setText("MyHomePage");
        }
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initilize
                EditText  vMaxSpace = ((EditText) findViewById(R.id.maxSpace));
                minSpace = ((EditText) findViewById(R.id.minSpace)).getText().toString().trim();
                minCost = ((EditText) findViewById(R.id.minCost)).getText().toString().trim();
                maxCost = ((EditText) findViewById(R.id.maxCost)).getText().toString().trim();
                searchplace = ((EditText) findViewById(R.id.searchplace)).getText().toString().trim();

                maxSpace = vMaxSpace.getText().toString().trim();

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
                        prgDialog.setMessage("Please wait...");
                        // Set Cancelable as False
//                        prgDialog.setCancelable(false);
                        prgDialog.show();

                        // Intent json to build
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("minCost", minCost);
                        intent.putExtra(SearchActivity.MAX_COST, maxCost);
                        intent.putExtra("maxSpace", maxSpace);
                        intent.putExtra("minSpace", minSpace);
                        intent.putExtra("searchplace", searchplace);
                        startActivity(intent);

                    }
                }
                //Ende
        });

        btn_home = (Button) findViewById(R.id.button8);
        if(login){
            btn_home.setText("Dashboard");
        }
        else {
            btn_home.setText("HOME");

        }

}

        /** Called when the user taps the Login button */
        public void goToLogin(View view) {

            if(login) {
                if(login){ button.setText("Logout");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "You was successfully logged out", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent( MainActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            } else{
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("estate","start");
                startActivity(intent);
            }
        }

        public void goToDashboard (View view){
            if(login) {
                if(login){ btn_home.setText("Dashboard");
                    SharedPreferences.Editor editor = prefs.edit();
                    Intent intent = new Intent( MainActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
            } else{
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
    }



        /*TextView v_textView = (TextView) findViewById(R.id.textView);
        if(login){
            if(login){
                v_textView.setText(v_textView +"");
            }
    }*/

}



