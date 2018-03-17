package com.sfsuse.fa17g16.myandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button logout, button;
    static String url = "http://192.168.137.89:17016/fa17g16";
    //static String url = "http://192.168.73.168:17016/fa17g16";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        button = (Button) findViewById(R.id.button3);
        Toast.makeText(getApplicationContext(),
                "you are succefull logout", Toast.LENGTH_LONG)
                .show();
        startActivity(intent);
    }
}
