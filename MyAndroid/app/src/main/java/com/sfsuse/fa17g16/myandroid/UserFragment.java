package com.sfsuse.fa17g16.myandroid;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    int user_id;
    SharedPreferences prefs;


    public UserFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TextView test = (TextView)findViewById(R.id.test);
        //test.setText("");
//        prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
//        user_id = prefs.getInt("user_id", 0);
//        // Required empty public constructor
//        RequestParams params = new RequestParams();
//        params.put("user_id", user_id);
//
//
//        Utils req= new Utils();
//        try {
//
//
//        JSONObject resultObjects= req.search(params,Utils.URL+"user/dashboard/android","buyerdata");
//
//        // Inflate the layout for this fragment
//            }
//            catch (Exception e){
//            }
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

}
