package com.sfsuse.fa17g16.myandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button logout;
    //static String url = "http://192.168.73.168:17016/fa17g16";
    Button bSearch, bReset, button;
    private static String url = Utils.URL;
    String maxSpace, minSpace, minCost, maxCost;
    String searchplace;
    ProgressDialog prgDialog;
    ViewGroup mycontent;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        savedInstanceState.getBoolean();*/

        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initilize
                maxSpace = ((EditText) findViewById(R.id.maxSpace)).getText().toString().trim();;
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

                    prgDialog = new ProgressDialog(Main2Activity.this);
                    prgDialog.setMessage("Please wait...");
                    // Set Cancelable as False
//                        prgDialog.setCancelable(false);
                    prgDialog.show();

                    // Intent json to build
                    Intent intent = new Intent(Main2Activity.this, SearchActivity.class);
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

            /*Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            button = (Button) findViewById(R.id.button3);
            Toast.makeText(getApplicationContext(),"you are succefull logout", Toast.LENGTH_LONG).show();
            startActivity(intent);*/


        //logout = (Button) findViewById(R.id.button3);
        /*logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(),
                        "you are succefull logout", Toast.LENGTH_LONG)
                        .show();
                startActivity(intent);

            }
        });*/
    }

    /*public void goToMainActivity(View view) {
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        button = (Button) findViewById(R.id.button3);
        Toast.makeText(getApplicationContext(),"you are succefull logout", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }*/


}
