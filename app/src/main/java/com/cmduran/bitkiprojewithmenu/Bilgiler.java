package com.cmduran.bitkiprojewithmenu;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class Bilgiler extends AppBaseActivity {

    TextView textABitki,textSicak,textNem,textHava,textPervane;
    Button btnUpdate;
    int sayac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgiler);
        textABitki=(TextView)findViewById(R.id.textAktifB);
        textSicak=(TextView)findViewById(R.id.textSicak);
        textHava=(TextView)findViewById(R.id.textHava);
        textNem=(TextView)findViewById(R.id.textNem);
        textPervane=(TextView)findViewById(R.id.textPervane);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        sayac=0;
        if(BluetoothAdapter.getDefaultAdapter().isEnabled()){
            try{
                MainActivity.bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
                    public void onDataReceived(byte[] data, String message) {

                        String s1=new String(data);
                        Toast.makeText(getBaseContext(), "Veriler Güncellendi!",Toast.LENGTH_LONG).show();
                        if(sayac==0){
                            textSicak.setText(s1);
                        }
                        else if(sayac==1){
                            textNem.setText(s1);
                            sayac=0;
                        }
                        sayac++;

                    }

                });
            }catch(Exception e){
                Toast.makeText(getBaseContext(), "Blueetooth Bağlantısı Yok!",Toast.LENGTH_LONG).show();
            }

        }



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    MainActivity.bt.send("bAl",false);
                    textABitki.setText(MainActivity.textBitki.getText());
                    textPervane.setText(Fonksiyon.kont);
                    textHava.setText(havaTespit());

                }catch (Exception e){
                    Toast.makeText(getBaseContext(),"Veriler Alınırken Bir Hata Oluştu!",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public String havaTespit(){
        String hava;
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        if(currentHour >=18 || currentHour<=6){
            hava="KARANLIK";
        }
        else{
            hava="AYDINLIK";
        }
        return hava;
    }
}
