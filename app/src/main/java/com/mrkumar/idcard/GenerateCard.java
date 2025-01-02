package com.mrkumar.idcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class GenerateCard extends AppCompatActivity {
    String branchname,gender,name,fname,contact,admdate,year,address,idno;
    TextView tvname,tvfname,tvsession,tvcontact,tvidno,tvsig,tvbranch,tvadmdate,tvaddress;
    ImageView ivdp;
    CardView cardView;
    Button btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_card);
        Intent intent = getIntent();
        branchname = intent.getStringExtra("branchname");
        gender = intent.getStringExtra("gender");
        name = intent.getStringExtra("name");
        fname = intent.getStringExtra("fname");
        contact = intent.getStringExtra("contact");
        address = intent.getStringExtra("address");
        admdate = intent.getStringExtra("admdate");
        year = intent.getStringExtra("year");
        idno = intent.getStringExtra("idno");

        btnShare = findViewById(R.id.btnshare);



        tvname = findViewById(R.id.tvname);
        tvfname = findViewById(R.id.tvfname);
        tvsession = findViewById(R.id.tvsession);
        tvcontact = findViewById(R.id.tvcontact);
        tvidno = findViewById(R.id.tvidno);
        tvsig = findViewById(R.id.tvsig);
        tvbranch = findViewById(R.id.tvbranch);
        tvadmdate = findViewById(R.id.tvadmdate);
        tvaddress =findViewById(R.id.tvadddress);
        ivdp = findViewById(R.id.ivdp);
        cardView = findViewById(R.id.cardview);

        tvfname.setText("Father's name:-Mr. "+fname);
        tvcontact.setText("Contact No:-"+contact);
        tvidno.setText("ID No:-"+idno);
        tvbranch.setText("Branch:-"+branchname);
        tvadmdate.setText("Admission date:-"+admdate);
        tvsig.setText(name);
        tvaddress.setText("Address:-"+address);

        if(gender.equalsIgnoreCase("male")){
            tvname.setText("Mr. "+name);

        }
        else{
            tvname.setText("Mrs. "+name);

        }

        int end = Integer.parseInt(year);
        end = end +4;
        String date = year +" - "+ Integer.toString(end);
        tvsession.setText(date);


        //Image set
        ivdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,1);
            }
        });



        //sharing Card

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap screenshot = ViewShot(cardView);


                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                screenshot.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),screenshot, "Title", null);
                Uri imageUri =  Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(intent, "Select"));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filepath = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,filepath,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filepath[0]);

            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ivdp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }



    // ye screenshot le rha cardview ka

    public Bitmap ViewShot(View v) {
        int height = v.getHeight();
        int width = v.getWidth();
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas (b);
        v.draw(c);
        return b;
    }

}