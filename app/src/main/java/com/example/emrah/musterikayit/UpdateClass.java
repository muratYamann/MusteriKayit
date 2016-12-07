package com.example.emrah.musterikayit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by murat on 7.10.2016.
 */
public class UpdateClass extends Activity {

    public static String TAG ="_update";
    TextView tvEskiBakiye;
    EditText etYeniBakiye,etEskiBakiye ;
    Button btnYeniHesapGuncelle;
    DBHelper dbGuncelle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        etYeniBakiye =(EditText)findViewById(R.id.etYeniHesap);
        btnYeniHesapGuncelle =(Button)findViewById(R.id.btnYeniBakiyeGuncelle);

        Calendar c = Calendar.getInstance();

        final String odemeYaptigiTarih = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+" "+c.get((Calendar.HOUR_OF_DAY))+":"+c.get(Calendar.MINUTE);



        dbGuncelle =new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        String hesap = extras.getString("hesap");
        final String gelenId = extras.getString("kulID");
        Log.d(TAG, "onCreate: "+hesap);


        Cursor rs = dbGuncelle.getData(Integer.valueOf(gelenId));
        rs.moveToFirst();
       final String hesapp = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));


        if (!rs.isClosed())
        {
            rs.close();
        }


        btnYeniHesapGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String yeniBakiye = etYeniBakiye.getText().toString().trim();
                    int eskiHesap = Integer.valueOf(hesapp);
                    int odenen = Integer.valueOf(yeniBakiye);
                    int kalan = eskiHesap - odenen;


                    dbGuncelle.updateContact(Integer.valueOf(gelenId),String.valueOf(kalan),odemeYaptigiTarih);
                    Toast.makeText(UpdateClass.this, "Guncelleme Gerçeklestirildi", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                }catch (Exception e){
                    Toast.makeText(UpdateClass.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
