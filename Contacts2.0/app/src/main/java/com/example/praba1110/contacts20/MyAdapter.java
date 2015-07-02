package com.example.praba1110.contacts20;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MyAdapter extends ArrayAdapter<String> {
    public String[] pic,pname;
    private Context con;
    public MyAdapter(Context context,String[] img , String[] objects) {
        super(context, R.layout.singlerow, objects);
        pic=img;
        pname=objects;
        con=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(getContext());
        View v = null;

            v=inflater.inflate(R.layout.singlerow,parent,false);
        final String images;
            images = pic[position];
            final String name=pname[position];
            final Button delete=(Button)v.findViewById(R.id.delete);
            TextView t=(TextView)v.findViewById(R.id.name_row);
            ImageView i=(ImageView)v.findViewById(R.id.img_row);
            Button edit=(Button)v.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Edit.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("pic", images);
                    con.startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLhandler handle = new SQLhandler(getContext(), null, null, 1);
                    handle.deletecontact(name);
                    Toast.makeText(getContext(), "Deleted " + name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    con.startActivity(intent);
                }
            });
            t.setText(name);
            if(images.equals("nopic")){
                i.setImageResource(R.drawable.contact);
            }
            else {
                i.setImageBitmap(BitmapFactory.decodeFile(images));
            }

        return v;
    }
}
