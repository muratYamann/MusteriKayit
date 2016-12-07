package com.example.emrah.musterikayit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DisplayContact extends AppCompatActivity {
int from_Where_I_Am_Coming = 0;
private DBHelper mydb ;
public static String TAG ="_ekleme";
    
EditText name ;
EditText phone;
EditText email;
EditText tc;
EditText hesap;
int id_To_Update = 0;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_contact);

    Calendar c = Calendar.getInstance();
    final String odemeYaptigiTarih = c.get(Calendar.YEAR)+"-"+
            (c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+
            " "+c.get((Calendar.HOUR_OF_DAY))+":"+c.get(Calendar.MINUTE);


    name = (EditText) findViewById(R.id.etAdi);
    phone = (EditText) findViewById(R.id.etTel);
    email = (EditText) findViewById(R.id.etEmail);
    tc = (EditText) findViewById(R.id.etTc);
    hesap = (EditText) findViewById(R.id.etHesap);

    mydb = new DBHelper(this);


Button btnSave = (Button)findViewById(R.id.btnKaydet);
btnSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Log.d(TAG, "onClick: btnSave");

        Bundle extras = getIntent().getExtras();
        Log.d(TAG, "onClick: Extras"+extras);
        
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value == 0){

                String id= tc.getText().toString();

                if(mydb.insertContact(id,name.getText().toString(), phone.getText().toString(), email.getText().toString(), tc.getText().toString(), hesap.getText().toString(),odemeYaptigiTarih)){
                    Toast.makeText(getApplicationContext(), "Kaydedildi", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: id:"+id);
                    Log.d(TAG, "onClick: name:"+name.getText());
                    Log.d(TAG, "onClick: phone:"+phone.getText());
                    Log.d(TAG, "onClick: email:"+email.getText());
                    Log.d(TAG, "onClick: tc:"+tc.getText());
                    Log.d(TAG, "onClick: hesap:"+hesap.getText());
                    Log.d(TAG, "onClick: tarih:"+odemeYaptigiTarih);
                }

                else{
                    Log.d(TAG, "onClick: kayıt Yapılmadi");
                    Toast.makeText(getApplicationContext(), "Öğe kaydedilmedi", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
            else{

                if(mydb.updateContact(id_To_Update,name.getText().toString(), phone.getText().toString(), email.getText().toString(),
                        tc.getText().toString(), hesap.getText().toString(),odemeYaptigiTarih))
                {

                    Toast.makeText(getApplicationContext(), "Güncellendi", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Güncellenmedi", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
});


    Bundle extras = getIntent().getExtras();
    if(extras !=null)
    {
        int Value = extras.getInt("id");

        if(Value==0){

            Button b = (Button)findViewById(R.id.btnKaydet);
            b.setVisibility(View.VISIBLE);
            name.setEnabled(true);
            name.setFocusableInTouchMode(true);
            name.setClickable(true);

            phone.setEnabled(true);
            phone.setFocusableInTouchMode(true);
            phone.setClickable(true);

            email.setEnabled(true);
            email.setFocusableInTouchMode(true);
            email.setClickable(true);

            tc.setEnabled(true);
            tc.setFocusableInTouchMode(true);
            tc.setClickable(true);

            hesap.setEnabled(true);
            hesap.setFocusableInTouchMode(true);
            hesap.setClickable(true);
        }


    }

}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    Bundle extras = getIntent().getExtras();

    if(extras !=null)
    {
        int Value = extras.getInt("id");
        if(Value>0){
            getMenuInflater().inflate(R.menu.display_contact, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.main, menu);
        }
    }
    return true;
}


}

