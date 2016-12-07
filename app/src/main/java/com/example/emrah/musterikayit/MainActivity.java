package com.example.emrah.musterikayit;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    public static  String TAG ="_Main";
    private ListView obj;
    DBHelper mydb;
    ArrayList<String> array_list;
    ArrayList<String>array_id;
    Bundle dataBundle;

    final static int PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermissionCallPhone();
        getPermissionSendSms();

        try {

            mydb = new DBHelper(this);
            array_id =new ArrayList<>();
            array_list = new ArrayList();
            array_id =mydb.getPersonId();
            array_list =mydb.getAllCotacts(); //mydb.getAllCotacts();
            Log.d(TAG, "db_person: "+array_list);

            ListView listemiz=(ListView) findViewById(R.id.lvMusteriListesi);
            listemiz.setLongClickable(true);
            ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, array_list);
            listemiz.setAdapter(veriAdaptoru);



            listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String gelenId =String.valueOf(array_id.get(position));
                    String name = String.valueOf(array_list.get(position));
                    Intent i =new Intent(getApplicationContext(),MusteriDetail.class);
                    dataBundle =new Bundle();
                    dataBundle.putString("kulID",gelenId);
                    dataBundle.putString("name",name);
                    i.putExtras(dataBundle);
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            });

        }catch (Exception e){}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.add_contact:
                dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void getPermissionCallPhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CALL_PHONE)) {
            }
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSIONS_REQUEST_CODE);
        }
    }

    public void getPermissionSendSms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.SEND_SMS)) {
            }
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
