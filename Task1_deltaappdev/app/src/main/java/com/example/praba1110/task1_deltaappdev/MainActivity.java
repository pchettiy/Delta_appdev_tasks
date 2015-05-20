package com.example.praba1110.task1_deltaappdev;

import android.content.Context;
import android.content.SharedPreferences;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    int counter=0;
    static String c="counter";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView t=(TextView)findViewById(R.id.text);

        SharedPreferences settings;
        settings=this.getSharedPreferences("save",Context.MODE_APPEND);
        counter=settings.getInt(c,counter);
        t.setText(""+counter);


    }

    public void buttonclick(View view)
    {
        counter++;
        TextView text=(TextView)findViewById(R.id.text);
        text.setText(""+counter);
        save(this,counter);


    }
    public void save(Context context,Integer counter)
    {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings=context.getSharedPreferences("save",Context.MODE_APPEND);
        editor=settings.edit();
        editor.putInt(c,counter);
        editor.commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }



}
