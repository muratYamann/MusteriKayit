package com.example.emrah.musterikayit;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by murat on 5.10.2016.
 */
public class MusteriDetail extends AppCompatActivity {
    public static String TAG = "_Main";

    Bundle dataBundle;
    DBHelper dbHelp;

    TextView tvMusteriadi, tvMusteriPhone, tvMusteriHesap, tvMusTC, tvMusMail;
    Button btnGuncelle;
    ListView lstMusteriDate;
    ArrayList<String> arrayList;
    ArrayList<String>arrayListDate;

    ResultReadToFileInternal readToFileInternal;

    String tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musteri_detail);

        dbHelp = new DBHelper(this);

        arrayListDate = new ArrayList();

        readToFileInternal = new ResultReadToFileInternal();


        try {

            Bundle extras = getIntent().getExtras();
            String isim = extras.getString("name");
            final String gelenId = extras.getString("kulID");
            Log.d(TAG, "extra" + isim + gelenId);

            Cursor rs = dbHelp.getData(gelenId);
            rs.moveToFirst();

            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
            final String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
            final String hesapp = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
            tc = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_ID));
            String mail = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
            String date = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUM_DATE));

            if (!rs.isClosed()) { rs.close();}

            arrayList = new ArrayList<>();



            tvMusteriHesap = (TextView) findViewById(R.id.tvMusteriHesap);
            tvMusteriadi = (TextView) findViewById(R.id.tvMuteriAdi);
            tvMusteriPhone = (TextView) findViewById(R.id.tvMusteriTel);
            tvMusMail = (TextView) findViewById(R.id.tvMusteriMail);
            tvMusTC = (TextView) findViewById(R.id.tvMusteriTC);
            btnGuncelle = (Button) findViewById(R.id.btnGuncelle);

            tvMusteriadi.setText( nam);
            tvMusteriPhone.setText(phon);
            tvMusteriHesap.setText( hesapp.toString());
            tvMusTC.setText( tc);
            tvMusMail.setText(mail);


            readToFileInternal.ReadToFile(tc+".csv",MusteriDetail.this);
            arrayListDate =readToFileInternal.liste;


            lstMusteriDate = (ListView)findViewById(R.id.lvMusteriDetailDate);
            ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayListDate);
            lstMusteriDate.setAdapter(veriAdaptoru);



            tvMusteriPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phon));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intentCall);
                }
            });



            btnGuncelle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBundle =new Bundle();
                    Intent intent = new Intent(getApplicationContext(),UpdateClass.class);

                    dataBundle.putString("kulID",gelenId);
                    dataBundle.putString("hesapp",hesapp.toString());
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
            });

        }catch (Exception e){}


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.musteri_detail_delete, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.Delete_Contact:
                Delete();
                 break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void Delete(){

        dbHelp.deleteContact(tc);
        Toast.makeText(getApplicationContext(), "silme işlemi başarılı", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }

}
