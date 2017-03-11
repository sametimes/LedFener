package com.samet.ledfener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    private Camera kamera;
    private boolean flashOn=false;
    private boolean flashdurum;
    Parameters param;

    Button buton;

    //hobaaaaaaa

    //Tamam kanks

    //adamsın pampa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buton = (Button) findViewById(R.id.button);

        flashdurum = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!flashdurum)
        {
            AlertDialog uyari = new AlertDialog.Builder(MainActivity.this).create();
            uyari.setTitle("Hata Mesajı");
            uyari.setMessage("Üzgünüz, telefonunuz flash özelliğine sahip değiildir!!!");

            uyari.setButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                }
            });

            uyari.show();
            return;
        }

        buton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(flashOn)
                {
                    flashkapat();
                }
                else
                {
                    flashac();
                }
            }
        });
    }
    private void kamerafonk(){

        if(kamera==null)
        {
            try
            {
                kamera = Camera.open();
                param = kamera.getParameters();
            }
            catch (RuntimeException e)
            {

            }
        }
    }

    private void kamerakapatfonk()
    {
        kamera.release();
        kamera=null;
    }

    private void flashac(){
        kamerafonk();
        if(!flashOn){
            if(kamera==null||param==null){
                return;
            }

            param=kamera.getParameters();
            param.setFlashMode(Parameters.FLASH_MODE_TORCH);
            kamera.setParameters(param);
            kamera.startPreview();
            flashOn=true;
        }
    }

    private void flashkapat(){
        if(flashOn) {
            if(kamera == null || param==null){
                return;
            }
            param=kamera.getParameters();
            param.setFlashMode(Parameters.FLASH_MODE_OFF);
            kamera.setParameters(param);
            kamera.stopPreview();
            kamerakapatfonk();
            flashOn=false;
        }
    }
    protected void onPause()
    {
        super.onPause();
        flashkapat();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(kamera!=null)
        {
            kamera.release();
            kamera=null;
        }
    }

}
