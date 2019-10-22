package com.cmduran.bitkiprojewithmenu;

import android.bluetooth.BluetoothManager;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class Fonksiyon extends AppBaseActivity {
    Button btnOn,btnOff;
    Switch aSwitch;
    static String kont="OTOMATİK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonksiyon);
        btnOn = (Button) findViewById(R.id.btnPon);
        btnOff = (Button) findViewById(R.id.btnPOff);
        aSwitch=(Switch)findViewById(R.id.switch1);
        aSwitch.setChecked(true);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try{
                    if(b){
                        MainActivity.bt.send("oto",false);
                        Toast.makeText(getBaseContext(),"PERVANE OTOMATİK MODDA",Toast.LENGTH_LONG).show();
                        btnOn.setVisibility(View.INVISIBLE);
                        btnOff.setVisibility(View.INVISIBLE);
                        kont="OTOMATİK";

                    }
                    else{
                        MainActivity.bt.send("manu",false);
                        Toast.makeText(getBaseContext(),"PERVANE MANUEL MODDA",Toast.LENGTH_LONG).show();
                        btnOn.setVisibility(View.VISIBLE);
                        btnOff.setVisibility(View.VISIBLE);
                        kont="MANUEL";

                    }
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(),"Bluetooth Bağlantısını Kontrol Edin!",Toast.LENGTH_LONG).show();

                }
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.bt.send("fOn", false);
            }
        });
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.bt.send("fOff",false);
            }
        });
    }

}
