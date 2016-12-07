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

    WriteInternalAppend_date wr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);


        wr = new WriteInternalAppend_date();

        etYeniBakiye =(EditText)findViewById(R.id.etYeniHesap);
        btnYeniHesapGuncelle =(Button)findViewById(R.id.btnYeniBakiyeGuncelle);
        tvEskiBakiye =(TextView)findViewById(R.id.tvEskiHesap) ;


        Calendar c = Calendar.getInstance();

        final String odemeYaptigiTarih = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+" "+c.get((Calendar.HOUR_OF_DAY))+":"+c.get(Calendar.MINUTE);

        final String trh[] =new String[1];
        trh[0]=odemeYaptigiTarih;


        dbGuncelle =new DBHelper(this);

        try {

            Bundle extras = getIntent().getExtras();
            String hesap = extras.getString("hesapp");
            final String gelenId = extras.getString("kulID");

            tvEskiBakiye.setText(hesap);

            Cursor rs = dbGuncelle.getData(gelenId);
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

                        if (etYeniBakiye.getText().toString().trim() != null){

                            String yeniBakiye = etYeniBakiye.getText().toString().trim();
                            int eskiHesap = Integer.valueOf(hesapp);
                            int odenen = Integer.valueOf(yeniBakiye);
                            int kalan = eskiHesap - odenen;

                            dbGuncelle.updateContact(gelenId,String.valueOf(kalan),odemeYaptigiTarih);
                            Toast.makeText(UpdateClass.this, "Guncelleme Gerçeklestirildi", Toast.LENGTH_SHORT).show();

                            wr.WriteFile(gelenId.toString().trim()+".csv",trh,UpdateClass.this);


                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                        }
                        else {

                            Toast.makeText(UpdateClass.this, "Ödeme miktarı giriniz ...", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(UpdateClass.this,"Ödeme miktarı giriniz...", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }catch (Exception e){

        }




    }
}
