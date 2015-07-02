package com.example.praba1110.contacts20;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    SQLhandler handler;
    String[] r,picid;
    List<String> lname,lpicid;



    ListView contacts;
    private boolean doubleBackToExitPressedOnce=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts= (ListView) findViewById(R.id.listView);
        handler=new SQLhandler(this,null,null,1);
        lname=new ArrayList<String>();
        lpicid=new ArrayList<String>();
        lname=handler.getnames();
        lpicid=handler.getpicid();
        picid=new String[lpicid.size()];
        picid=lpicid.toArray(picid);
        r=new String[lname.size()];
        r=lname.toArray(r);
        ListAdapter adapter=new MyAdapter(this,picid,r);
        contacts.setAdapter(adapter);



    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //Giving add button function
        if (id == R.id.Add) {
            Intent intent=new Intent(this,Addnew.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
