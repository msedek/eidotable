package com.eidotab.eidotab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.eidotab.eidotab.SQlite.DBHelper;


public class ScreenOne extends AppCompatActivity
{
    ImageButton btn_esp;
    ImageButton btn_eng;
    ImageButton btn_ordenar;
    String language;
    Boolean slan = false;
    Boolean slan2 = false;
    DBHelper myDB;

    LinearLayout itouch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_one);

        myDB = DBHelper.GetDBHelper(this);

        btn_esp     = (ImageButton)  findViewById(R.id.btn_esp);
        btn_eng     = (ImageButton)  findViewById(R.id.btn_eng);
        btn_ordenar = (ImageButton)  findViewById(R.id.btn_ordenar);
        itouch      = (LinearLayout) findViewById(R.id.itouch);
        slan        = true;
        slan2       = true;


        itouch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btn_ordenar.performClick();
            }
        });

        btn_esp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btn_esp.setVisibility(View.INVISIBLE);
                btn_eng.setVisibility(View.VISIBLE);
                mostrarMensaje("Español");
                language = "ES";
                slan = false;
                slan2 = true;
            }
        });

        btn_eng.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btn_eng.setVisibility(View.INVISIBLE);
                btn_esp.setVisibility(View.VISIBLE);
                mostrarMensaje("English");
                language = "EN";
                slan = false;
                slan2 = false;
            }
        });

        btn_ordenar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (slan)
                {
                    language = "ES";
                    slan = false;
                }

                if (slan2)
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("lang", language);
                    startActivity(intent);
                }
                else
                {
                    mostrarMensaje("Idioma Seleccionado no activo en Demo");
                }

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


    }

    private void mostrarMensaje(String mensaje)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

}
