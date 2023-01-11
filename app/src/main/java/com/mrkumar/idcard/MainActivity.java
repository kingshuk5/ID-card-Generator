package com.mrkumar.idcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String branchname,gender,name,fname,contact,admdate,year,address,idno;
    Spinner spinner;
    RadioGroup rggender;
    EditText edtname,edtfname,edtcontact,edtadmdate,edtyear,edtaddress,edtidno;
    RadioButton getSelectedItem;
    Button btngenerate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().hide();

        spinner = findViewById(R.id.spinner);
        rggender = findViewById(R.id.rggender);
        edtname = findViewById(R.id.edtname);
        edtfname = findViewById(R.id.edtfname);
        edtcontact = findViewById(R.id.edtcontact);
        edtadmdate = findViewById(R.id.edtadmdate);
        edtyear = findViewById(R.id.edtyear);
        edtaddress = findViewById(R.id.edtaddress);
        btngenerate = findViewById(R.id.btngenerate);
        edtidno = findViewById(R.id.edtidno);

        //Spinner ka code
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinnerlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchname = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(MainActivity.this, branchname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Generate button par click

        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioid = rggender.getCheckedRadioButtonId();
                if(radioid != -1){
                    getSelectedItem = findViewById(radioid);
                    gender = getSelectedItem.getText().toString();

                    name = edtname.getText().toString();
                    fname = edtfname.getText().toString();
                    contact = edtcontact.getText().toString();
                    admdate = edtadmdate.getText().toString();
                    year = edtyear.getText().toString();
                    address = edtaddress.getText().toString();
                    idno = edtidno.getText().toString();

                    Intent intent = new Intent(MainActivity.this,GenerateCard.class);
                    intent.putExtra("name",name);
                    intent.putExtra("fname",fname);
                    intent.putExtra("contact",contact);
                    intent.putExtra("admdate",admdate);
                    intent.putExtra("year",year);
                    intent.putExtra("address",address);
                    intent.putExtra("gender",gender);
                    intent.putExtra("branchname",branchname);
                    intent.putExtra("idno",idno);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(MainActivity.this, "Gender Not Selected", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}