package com.example.praba1110.contacts20;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class Edit extends ActionBarActivity {

    String image,con_name,del;
    EditText name;
    SQLhandler handler;
    ImageView imageView ;
    Integer REQ_CODE_PICK_IMAGE=1;
    String TEMP_PHOTO_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageView= (ImageView) findViewById(R.id.imageView2);
        name=(EditText)findViewById(R.id.editname);
        Bundle b=getIntent().getExtras();
        if(b!=null){
            con_name=b.getString("Name");
            del=con_name;
            image=b.getString("pic");
        }
        if(image.equals("black")){
            imageView.setImageResource(R.drawable.contact);
        }
        else {
            imageView.setImageBitmap(BitmapFactory.decodeFile(image));
        }
        name.setText(con_name);
    }
    public void newpick(View v){
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        i.setType("image/*");
        i.putExtra("crop", "true");
        i.putExtra("aspectX", 1);
        i.putExtra("aspectY", 1);
        i.putExtra("outputX", 300);
        i.putExtra("outputY", 300);
        i.putExtra("return-data", true);
        i.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        i.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(i, REQ_CODE_PICK_IMAGE);
    }
    public void done(){
        handler=new SQLhandler(this,null,null,1);
        handler.deletecontact(con_name);
        con_name=name.getText().toString();
        if(image==null){
            image="nopic";
        }

        handler.addcontact(con_name, image);
        Toast.makeText(this, "Edited  " + con_name, Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }



    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {

        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        int d=c.get(Calendar.DATE);
        int m=c.get(Calendar.MONTH);
        int y=c.get(Calendar.YEAR);
        TEMP_PHOTO_FILE=Long.toString(System.currentTimeMillis())+"_"+
                Integer.toString(d)+"_"+Integer.toString(m)+"_"+Integer.toString(y)+".jpg";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


            File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {}

            return file;
        } else {

            return null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode==REQ_CODE_PICK_IMAGE) {

            if (resultCode == RESULT_OK) {
                if (imageReturnedIntent!=null) {



                    String filePath= Environment.getExternalStorageDirectory()
                            +"/"+TEMP_PHOTO_FILE;
                    image=filePath;


                    Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(selectedImage);
                }
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Giving save button function
        if (id == R.id.Done) {
            done();
        }

        return super.onOptionsItemSelected(item);
    }
}
