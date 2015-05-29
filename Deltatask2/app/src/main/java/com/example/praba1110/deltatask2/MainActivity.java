package com.example.praba1110.deltatask2;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    String[] names;
    ListView contacts;
    int[] images={R.drawable.aa,R.drawable.ar,R.drawable.b,R.drawable.ew,R.drawable.lp,R.drawable.mz,R.drawable.sj,R.drawable.ts};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        names=res.getStringArray(R.array.Names);
        contacts= (ListView) findViewById(R.id.listView);
        MyAdapter adapter=new MyAdapter(this,images,names);
        contacts.setAdapter(adapter);
    }

    public void search(View view)
    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String name = editText.getText().toString();
        int l = names.length, flag=0;
        if(l==0) Toast.makeText(this,"Enter a name",Toast.LENGTH_SHORT);
        else
        {
            for (int i = 0; (i < l) && (flag == 0); i++) {
                if (names[i].equalsIgnoreCase(name)) {
                    Toast.makeText(this, "Contact found: " + names[i], Toast.LENGTH_SHORT).show();
                    flag = 1;
                }
            }
            if (flag == 0) Toast.makeText(this, "Missing. :(", Toast.LENGTH_SHORT).show();
        }
    }
class MyAdapter extends ArrayAdapter<String>
{
    int[] images;
    Context context;
    String[] cnames;
    MyAdapter(Context c,int[] imgs,String[] names)
    {
        super(c,R.layout.singlerow,R.id.textView,names);
        this.context=c;
        this.images=imgs;
        this.cnames=names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.singlerow,parent,false);
        ImageView cpic= (ImageView) row.findViewById(R.id.imageView);
        TextView cname= (TextView) row.findViewById(R.id.textView);
        cpic.setImageResource(images[position]);
        cname.setText(cnames[position]);



        return row;
    }

}
}
