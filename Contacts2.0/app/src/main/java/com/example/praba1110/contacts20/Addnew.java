package com.example.praba1110.contacts20;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;


public class Addnew extends AppCompatActivity {

    ImageView con_image;
    String image,con_name;
    EditText name;
    SQLhandler handler;
    public  String PHOTO_FILE = ".jpg";
    Integer REQ_CODE_PICK_IMAGE=1;

    public void done(){
        handler=new SQLhandler(this,null,null,1);
        if(image==null){
            image="nopic";
        }
        con_name=name.getText().toString();
        handler.addcontact(con_name, image);
        Toast.makeText(this,"Added "+con_name,Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        name=(EditText)findViewById(R.id.editText);
        con_image= (ImageView) findViewById(R.id.imageView);
    }
    public void uploadimage(View v){
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


    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }


    private File getTempFile() {

        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        int d=c.get(Calendar.DATE);
        int m=c.get(Calendar.MONTH);
        int y=c.get(Calendar.YEAR);
        PHOTO_FILE=Long.toString(System.currentTimeMillis())+"_"+
                Integer.toString(d)+"_"+Integer.toString(m)+"_"+Integer.toString(y)+".jpg";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


            File file = new File(Environment.getExternalStorageDirectory(),PHOTO_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {}

            return file;
        } else {
            Toast.makeText(this,"Not Uploaded",Toast.LENGTH_LONG).show();
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
                            +"/"+PHOTO_FILE;
                    image=filePath;
                    Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);
                    con_image.setImageBitmap(selectedImage);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addnew, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Adding save button function
        if (id == R.id.Done) {
            done();
        }

        return super.onOptionsItemSelected(item);
    }
}
