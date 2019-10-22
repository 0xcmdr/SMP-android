package com.cmduran.bitkiprojewithmenu;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class MainActivity extends AppBaseActivity implements AdapterView.OnItemSelectedListener {
    Button btnON,btnYolla;
    static BluetoothSPP bt;
    boolean kontrol=true;
    TextView textBaglan;
    Spinner spinner;
    static EditText textBitki,textSıcak,textNem;
    ImageView resim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnON=(Button)findViewById(R.id.btnBaglan);
        btnYolla=(Button)findViewById(R.id.btnYolla);
        textBaglan=(TextView)findViewById(R.id.textBaglan);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        textBitki=(EditText)findViewById(R.id.editBitki);
        textSıcak=(EditText)findViewById(R.id.editSicak);
        textNem=(EditText)findViewById(R.id.editNem);
        resim=(ImageView)findViewById(R.id.imageView);
        btnON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(kontrol==true && BluetoothAdapter.getDefaultAdapter().isEnabled()){
                        bt= new BluetoothSPP(getApplicationContext());
                        if(!bt.isServiceAvailable()){
                            bt.setupService();
                            bt.startService(BluetoothState.DEVICE_OTHER);
                        }
                        if(!bt.isAutoConnecting()) {
                            bt.autoConnect("BitkiProjex");
                            Toast.makeText(getBaseContext(),"Bağlantı Başarılı!",Toast.LENGTH_LONG).show();
                            kontrol=false;
                        }
                        //SAYAC İLE BEKLETME

                        }
                    if(!kontrol){
                        new CountDownTimer(6000, 1000) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                saatGonder();
                            }

                        }.start();
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Lütfen Bluetooth'u Etkinleştirin!",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });
        btnYolla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bt.send("bOl"+"*"+textBitki.getText().toString()+"-"+textSıcak.getText().toString()+","+textNem.getText().toString(),true);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
     int id=(int)parent.getSelectedItemId();
     String bitki=parent.getSelectedItem().toString();
     textBitki.setText(bitki);
     switch (id){
         case 0:textSıcak.setText("30");textNem.setText("18");resim.setImageResource(R.drawable.aralya);
         break;
         case 1:textSıcak.setText("20");textNem.setText("28");resim.setImageResource(R.drawable.gladya);
         break;
         case 2 :textSıcak.setText("10");textNem.setText("8");resim.setImageResource(R.drawable.kroton);
         break;
     }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public boolean aktifMi(){
        boolean kont=false;

            if(!bt.isBluetoothAvailable()){
                Toast.makeText(this," Bluetoothu, BitkiProjex Modülüyle Eşleştirin",Toast.LENGTH_LONG).show();
                kont=false;
            }
            else {
                Toast.makeText(this,"Bağlanılıyor",Toast.LENGTH_LONG).show();
                kont=true;
            }
        return kont;
    }

    public void saatGonder(){
        Calendar rightNow = Calendar.getInstance();
        int ay=5;
        int gun=rightNow.get(Calendar.DAY_OF_MONTH);
        int hgun=rightNow.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int saat= rightNow.get(Calendar.HOUR_OF_DAY);
        int dakika=rightNow.get(Calendar.MINUTE);
        int saniye=rightNow.get(Calendar.SECOND);
        bt.send("rtc"+"*"+saniye+"-"+dakika+","+saat+"."+hgun+"+"+gun+"/"+ay,false);
    }
}

