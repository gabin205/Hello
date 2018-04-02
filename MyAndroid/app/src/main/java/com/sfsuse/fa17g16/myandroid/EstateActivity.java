package com.sfsuse.fa17g16.myandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;

public class EstateActivity extends AppCompatActivity {
    View layout;
    Button button;
    Button btn_contact, btn_send;
    SharedPreferences prefs;
    private boolean login;
    private String url = Utils.URL;
    String header;
    String size;
    String cost;
    //Bundle bundle = new Bundle();

    RequestParams params;
    ArrayList<String> equipementListe;
    ArrayList<String> imagesListe;
     String rooms;
     String zipcode;
     String location;
     String description;
     String build_at;
     String first_name;
     String last_name;
     String email;
     String street;
     String seller_id;
     String realestate_id;
     String pw;
     String email_s;
     int user_id;
    private String headerName;
    private String headerValue;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_estate);
         Intent intent = getIntent();
         header = intent.getStringExtra("header");
         size = intent.getStringExtra("size");
         cost = intent.getStringExtra("cost");
         rooms = intent.getStringExtra("rooms");
         zipcode = intent.getStringExtra("zipcode");
         location = intent.getStringExtra("location");
         street = intent.getStringExtra("street");
         description = intent.getStringExtra("description");
         build_at = intent.getStringExtra("build_at");
         first_name = intent.getStringExtra("first_name");
         last_name = intent.getStringExtra("last_name");
         email = intent.getStringExtra("email");
         seller_id = intent.getStringExtra("seller_id");
         realestate_id = intent.getStringExtra("realestate_id");
         params=new RequestParams();

        prefs = PreferenceManager.getDefaultSharedPreferences(EstateActivity.this);
        login = prefs.getBoolean("islogged", false);
        user_id = prefs.getInt("user_id", 0);
        email_s = prefs.getString("email", null);
        pw=prefs.getString("pw", null);
        headerName = prefs.getString(Utils.HEADER_NAME, null);
        headerValue = prefs.getString(Utils.HEADER_VALUE, null);
        button = (Button) findViewById(R.id.button3);

        if (login) {
            button.setText("Logout");
        } else {
            button.setText("MyHomePage");
        }
        equipementListe = intent.getStringArrayListExtra("equipements");
        imagesListe = intent.getStringArrayListExtra("images");


        ListView listView = (ListView) findViewById(R.id.v_equipment);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, equipementListe);
        listView.setAdapter(adapter);

        //
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imageliste);
        for (String imagePath : imagesListe) {
            ImageView imageView = new ImageView(this);
            imageView.setMaxHeight(50);
            Glide.with(this).load(imagePath).into(imageView);
            linearLayout.addView(imageView);
        }

        final TextView v_header = (TextView) findViewById(R.id.header);
        v_header.setText(header);
        TextView v_size = (TextView) findViewById(R.id.size);
        v_size.setText(size);
        TextView v_cost = (TextView) findViewById(R.id.cost);
        v_cost.setText(cost);
        TextView v_rooms = (TextView) findViewById(R.id.rooms);
        v_rooms.setText(rooms);
        TextView v_zipcode = (TextView) findViewById(R.id.zipcode);
        v_zipcode.setText(zipcode);
        TextView v_location = (TextView) findViewById(R.id.location);
        v_location.setText(location);
        TextView v_street = (TextView) findViewById(R.id.street);
        v_street.setText(street);
        TextView v_description = (TextView) findViewById(R.id.description);
        v_description.setText(description);
        /*TextView v_build_at =(TextView)findViewById(R.id.build_at);
        v_build_at.setText(build_at);*/
        TextView v_first_name = (TextView) findViewById(R.id.s_first_name);
        v_first_name.setText(first_name);
        TextView v_last_name = (TextView) findViewById(R.id.s_last_name);
        v_last_name.setText(last_name);
        TextView v_email = (TextView) findViewById(R.id.s_email);
        v_email.setText(email);

        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				
                // initilize
                if (!login) {
                    Toast.makeText(getApplicationContext(), "Please Log in before using this function", Toast.LENGTH_LONG).show();
                } else {

                    //alert Dialog
                    AlertDialog.Builder builder;
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    //View layout = inflater.inflate(R.layout.sendmessage, (ViewGroup) findViewById(R.id.msg_contact));
                     layout = inflater.inflate(R.layout.sendmessage, null);

                    builder = new AlertDialog.Builder(EstateActivity.this);

                    builder.setView(layout);
                    AlertDialog ppEnviarMsg = builder.create();

                    btn_send = (Button)layout.findViewById(R.id.btn_send);


                    ppEnviarMsg.show();

                    ppEnviarMsg.getWindow().getAttributes().gravity = Gravity.CENTER;//ende*/
                    //ppEnviarMsg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                    btn_send.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        EditText v_boddy = (EditText)layout.findViewById(R.id.v_boddy);
                                                        EditText v_header = ((EditText)layout.findViewById(R.id.v_header));
                                                        String g_boddy = v_boddy.getText().toString().trim();
                                                        String g_header = v_header.getText().toString().trim();
                                                        RequestParams params = new RequestParams();
                                                        params.put("real_estate", realestate_id);
                                                        params.put("header", g_header);
                                                        params.put("body", g_boddy);
                                                        params.put("sender_id", user_id);
                                                        contactMsg(params);
                                                    }
                                                });
                }
            }

        });
    }

        public void contactMsg(RequestParams params){

            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader(headerName, headerValue);
            client.post(url + "messaging/contactrequest/android/"+seller_id, params, new TextHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                    Log.i("result","body: "+responseString);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        Toast.makeText(getApplicationContext(), "your message was successfull sended", Toast.LENGTH_LONG).show();

                }
            });
        }


        /** Called when the user taps the Login button */

    public void goToLogin(View view) {
    if(login){
        button.setText("Logout");
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(getApplicationContext(), "You was successfully logged out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent( EstateActivity.this,MainActivity.class);
        startActivity(intent);
    }else
        {
            Bundle bundle= new Bundle();
            Intent intent = new Intent(EstateActivity.this, LoginActivity.class);
            bundle.putString("estate","first");
            bundle.putStringArrayList("equipements", equipementListe);
            bundle.putStringArrayList("images",imagesListe );
            bundle.putString("header", header);
            bundle.putString("cost", cost);
            bundle.putString("street", street);
            bundle.putString("size", size);
            bundle.putString("rooms", rooms);
            bundle.putString("zipcode", zipcode);
            bundle.putString("location", location);
            bundle.putString("description", description);
            bundle.putString("build_at", build_at);
            bundle.putString("first_name", first_name);
            bundle.putString("last_name", last_name);
            bundle.putString("email", email);
            bundle.putString("seller_id", seller_id);
            bundle.putString("realestate_id", realestate_id);
            intent.putExtras(bundle);
        startActivity(intent);
        }
    }

}
