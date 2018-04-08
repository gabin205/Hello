package com.sfsuse.fa17g16.myandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    int user_id;
    SharedPreferences prefs;
    private boolean login;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();*/

            switch (item.getItemId()) {
                case R.id.navigation_person:
                    UserFragment fragment = new UserFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment, "FragmentName");
                    fragmentTransaction1.commit();
                    //transaction.replace(R.id.content, new UserFragment());
                    return true;
                case R.id.navigation_message:
                    MessageFragment fragment2 = new MessageFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.user, fragment2, "FragmentName");
                    fragmentTransaction2.commit();
                    //transaction.replace(R.id.user, new MessageFragment());
                    return true;
                case R.id.navigation_realEstate:

                    //transaction.replace(R.id.content, new EstateFragment());
                    return true;
                case R.id.navigation_sell:
                    //transaction.replace(R.id.content, new SellFragment());
                    return true;
                case R.id.navigation_favorite:
                    //transaction.replace(R.id.content, new FavoriteFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        prefs = PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this);
        login = prefs.getBoolean("islogged", false);
        user_id = prefs.getInt("user_id", 0);
        // Required empty public constructor
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);


        Utils req= new Utils();
        try {


            JSONObject resultObjects= req.search(params,Utils.URL+"user/dashboard/android","buyerdata");

            // Inflate the layout for this fragment
        }
        catch (Exception e){
        }

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new UserFragment());*/
        UserFragment fragment = new UserFragment();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content, fragment, "FragmentName");
        fragmentTransaction1.commit();


    }

}
