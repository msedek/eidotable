package com.eidotab.eidotab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.eidotab.eidotab.Interfaz.IRequesstMensaje;
import com.eidotab.eidotab.Model.Mensaje;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.Model.Tablet;
import com.eidotab.eidotab.SQlite.DBHelper;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity
{
    Button btn_menu;
    Button btn_mesero;

    LinearLayout btn_mnos;

    ArrayList<Plato> lista_platos;
    ArrayList<Plato> list_filter;
    ArrayList<Plato> list_sugecheff;

    LinearLayout lyo_sugecheff;

    Integer borrado;



    Boolean mainsw;

    String language;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        lyo_sugecheff   = (LinearLayout) findViewById(R.id.lyo_sugecheff);
        btn_menu        = (Button) findViewById(R.id.btn_menu);
        btn_mesero      = (Button) findViewById(R.id.btn_mesero);
        btn_mnos        = (LinearLayout) findViewById(R.id.btn_mnos);

        myDB = DBHelper.GetDBHelper(this);

        borrado = 0;



        //mScroll = true;
        lista_platos = new ArrayList<>();
        list_filter = new ArrayList<>();
        list_sugecheff = new ArrayList<>();

        getdata();

        lista_platos = myDB.listPlatos();


        if (language.equals("ES"))
        {
            for (Plato plato : lista_platos)
            {
                if (plato.getIdioma().equals("español"))
                {
                    list_filter.add(plato);
                }
            }
        }

        if (language.equals("EN"))
        {
            for (Plato plato : lista_platos)
            {
                if (plato.getIdioma().equals("english"))
                {
                    list_filter.add(plato);
                }
            }
        }

        mainsw = true;

        for (Plato suge : list_filter)
        {
            if (suge.isSugerencia())
            {
                list_sugecheff.add(suge);
            }
        }


        lyo_sugecheff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SugeCheff.class);
                intent.putExtra("data", list_sugecheff);
                intent.putExtra("cate", "Sugerencias");
                intent.putExtra("swmodo", false);
                intent.putExtra("bsuge", true);
                startActivity(intent);
            }
        });


        // setAdapter();




        //myRecycler.scrollToPosition(repeatCounter);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_menu_Activity.class);
                intent.putExtra("data", list_filter);
                startActivity(intent);
            }
        });

        btn_mesero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showmessage2("LLAMASTE AL MESERO");

                Mensaje mensaje = new Mensaje();

                Tablet tablet = myDB.listTablet();
                mensaje.setEstadomensaje("PENDIENTE");
                mensaje.setRemitente("MESA " + tablet.getNro_mesa());
                mensaje.setTexto("LO SOLICITAN EN LA MESA");

                sendmensaje(mensaje);


            }
        });

        btn_mnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NosotrosActivity.class);
                startActivity(intent);

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

        mainsw = true;

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mainsw = false;
    }

    private void getdata()
    {
        Intent intent = getIntent();
        language = intent.getExtras().getString("lang");

    }

    private void showmessage2(String mensaje)
    {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, Integer.parseInt(getString(R.string.duratoast)));

    }


    public void sendmensaje(Mensaje mensaje)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApplicationContext().getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequesstMensaje request = retrofit.create(IRequesstMensaje.class);

        Call<Mensaje> call = request.addMensaje(mensaje);

        call.enqueue(new Callback<Mensaje>()
        {
            @Override
            public void onResponse(@NonNull Call<Mensaje> call, @NonNull Response<Mensaje> response)
            {
                Log.i("RETROFIT", "Envió comanda a cocina");

                setResult(RESULT_OK);
            }

            @Override
            public void onFailure(@NonNull Call<Mensaje> call, @NonNull Throwable t)
            {

                Log.d("Error agregar pedido " , t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        // Do Here what ever you want do on back press;
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

/*        borrado++;

        if(borrado == 3)
        {
            mostrarDialogo("Alerta", "Deseas reiniciar la base de datos del DEMO?");
            borrado = 0;
        }*/

    }

    private void mostrarDialogo(String titulo, String mensaje)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(mensaje)
                .setTitle(titulo)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmación Aceptada.");
                        baseborrado();
                        dialog.cancel();
                        finish();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmación Cancelada.");
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void baseborrado()
    {

        myDB.deleteAllPedido();
        myDB.deleteAllCuenta();

    }


}
