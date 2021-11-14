package com.eidotab.eidotab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.Handler;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.eidotab.Adapter.MyAdapter6;
import com.eidotab.eidotab.Interfaz.IRequesstMensaje;
import com.eidotab.eidotab.Interfaz.ISendPedido;
import com.eidotab.eidotab.Model.DataRoot;
import com.eidotab.eidotab.Model.Itemorder;
import com.eidotab.eidotab.Model.Mensaje;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.Model.Posirator;
import com.eidotab.eidotab.Model.Tablet;
import com.eidotab.eidotab.SQlite.DBHelper;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlatoActivity extends AppCompatActivity
{

    LinearLayout btn_exp;
    LinearLayout btn_expa;

    LinearLayout lyo_boton;
    LinearLayout agregaplato_arriba;
    LinearLayout agregaplato_abajo;

    Integer posiarray;

    //ImageButton btn_add;

    ScrollView scroll_pedido;
    ScrollView scroll_pedidoa;
    //Boolean level;


    LinearLayout mov;


    LinearLayout lyo_transfer;

    Button saltarin;
    Button btn_left;

    TextView lbl_titulo;



    ArrayList<Itemorder> aitem;

    RecyclerView myRecycler;
    MyAdapter6 adp;
    int position;

    int posiorder;


    Boolean entrada;

    Integer posiscroll;

    DataRoot dataRoot;

    ArrayList<String> contorno;
    ArrayList<String> ingrediente;


    ArrayList<Plato> dataplat;

    Tablet tablet;

    DBHelper myDB;

    Button btn_mesero;
    Button btn_order;

    LinearLayout lizq;
    LinearLayout ltex;
    LinearLayout lder;

    //int contar;

    TextView tlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato);

        //btn_add            = (ImageButton)      findViewById(R.id.btn_add);

        lyo_boton          = findViewById(R.id.lyo_boton);
        myRecycler         = findViewById(R.id.myRecyclerView6);
        saltarin           = findViewById(R.id.saltarin);
        btn_left           = findViewById(R.id.btn_left);
        btn_exp            = findViewById(R.id.btn_exp);
        lyo_transfer       = findViewById(R.id.lyo_transfer);
        agregaplato_arriba = findViewById(R.id.agrega_platoarriba);
        agregaplato_abajo  = findViewById(R.id.lyo_platoabajo);
        scroll_pedido      = findViewById(R.id.scrollpedido);
        scroll_pedidoa     = findViewById(R.id.scrollpedidoa);
        lbl_titulo         = findViewById(R.id.lbl_titulo);
        btn_mesero         = findViewById(R.id.btn_mesero);
        btn_order          = findViewById(R.id.btn_order);
        lizq               = findViewById(R.id.lizq);
        lder               = findViewById(R.id.lder);
        ltex               = findViewById(R.id.ltext);
        tlevel             = findViewById(R.id.tlevel);




        mov                = findViewById(R.id.mov);

        btn_expa           = findViewById(R.id.btn_expa);

        posiarray = 0;
        posiorder = 0;

        aitem = new ArrayList<>();

        contorno = new ArrayList<>();
        ingrediente = new ArrayList<>();

        myDB = DBHelper.GetDBHelper(this);

        tablet = myDB.listTablet();

        btn_mesero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showmessage("LLAMASTE AL MESERO");
                veritran();

                Mensaje mensaje = new Mensaje();

                Tablet tablet = myDB.listTablet();
                mensaje.setEstadomensaje("PENDIENTE");
                mensaje.setRemitente("MESA " + tablet.getNro_mesa());
                mensaje.setTexto("LO SOLICITAN EN LA MESA");

                sendmensaje(mensaje);


            }
        });

        btn_exp.setOnClickListener(new View.OnClickListener() //ESTOY ABAJO Y PRESIONO PARA SUBIR
        {
            @Override
            public void onClick(View v)
            {
                tlevel.setText("true");
                //((MyApplication) getApplicationContext()).setLevel(true);
                //level = true;
                dataRoot = myDB.listPedidos();
                if (dataRoot.getEstado_orden() != null )
                {
                    recuorder(dataRoot);
                }
                //btn_add.setEnabled(false);
                lyo_transfer.setVisibility(View.VISIBLE);
                agregaplato_abajo.removeAllViews();
                lyo_transfer.bringToFront();
            }
        });

        btn_expa.setOnClickListener(new View.OnClickListener() //ESTOY ARRIBA Y PRESIONO PARA BAJAR
        {
            @Override
            public void onClick(View v)
            {
                //level = false;// INDICA EN QUE LAYOUT VOY OPERAR
                tlevel.setText("false");
                //((MyApplication) getApplicationContext()).setLevel(false);
                dataRoot = myDB.listPedidos();
                if (dataRoot.getEstado_orden() != null )
                {
                    recuorder(dataRoot);
                }
                //btn_add.setEnabled(true);
                lyo_transfer.setVisibility(View.INVISIBLE);
                agregaplato_arriba.removeAllViews();
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                veritran();
                ArrayList<Itemorder>filtrorder = new ArrayList<>();
                ArrayList<Itemorder>listo = new ArrayList<>();

                DataRoot dataenviar = myDB.listPedidos();

                if (dataenviar.getOrden() != null)
                {

                    for(Itemorder itemorder : dataenviar.getOrden())
                    {
                        if(!itemorder.getEnviado())
                        {
                            filtrorder.add(itemorder);
                            itemorder.setEnviado(true);
                        }
                        else
                        {
                            listo.add(itemorder);
                        }
                    }

                    dataenviar.setOrden(filtrorder);
                    dataenviar.setEstado_orden("pendiente");
                    dataenviar.setEstado_pago("pendiente");
                    dataenviar.setEstado_pcuenta("pendiente");
                    String nmesa = dataenviar.getIdinterno();
                    dataenviar.setMesa(nmesa);

                    if (filtrorder.size() > 0)
                    {
                        sendorder(dataenviar);

                        if(listo.size() > 0)
                        {
                            filtrorder.addAll(listo);
                        }

                        Collections.sort(filtrorder, new Posirator());

                        dataenviar.setOrden(filtrorder);

                        myDB.updatePedido(dataenviar);



                        showmessage("COLOCASTE EL PEDIDO");


                        agregaplato_abajo.removeAllViews();
                        agregaplato_arriba.removeAllViews();
                        lyo_transfer.setVisibility(View.INVISIBLE);

                        //level = false;
                        //((MyApplication) getApplicationContext()).setLevel(false);
                        tlevel.setText("false");



                        recuorder(dataenviar);

                        finish();

                    }
                    else
                    {
                        showmessage("SELECCIONE ALGÚN ARTÍCULO");
                    }

                }
                else
                {
                    showmessage("SELECCIONE ALGÚN ARTÍCULO");



/*                    if(((MyApplication) getApplicationContext()).getSugecheffbool())
                    {
                        Intent intentToA = new Intent(PlatoActivity.this, MainActivity.class);
                        intentToA.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intentToA.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intentToA);
                        finish();
                    }
                    else
                    {
*//*                        Intent intentToA = new Intent(PlatoActivity.this, Main_menu_Activity.class);
                        intentToA.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intentToA.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intentToA);*//*
                        finish();
                    }*/







                }





            }
        });



        entrada = true;


        dataplat = new ArrayList<>();


        lyo_boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });



        getdata();

        setAdapter();

        for (Plato suge : dataplat)
        {

            position = adp.AddNew(suge);

        }

        if(dataplat.size()>1)
        {
            btn_left.setVisibility(View.VISIBLE);
            saltarin.setVisibility(View.VISIBLE);
            saltarin.performClick();
        }
        else
        {
            btn_left.setVisibility(View.INVISIBLE);
            saltarin.setVisibility(View.INVISIBLE);
        }

    }

    public void sendorder(DataRoot dataenviar)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApplicationContext().getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISendPedido request = retrofit.create(ISendPedido.class);

        Call<DataRoot> call = request.dataenviar(dataenviar);

        call.enqueue(new Callback<DataRoot>()
        {
            @Override
            public void onResponse(@NonNull Call<DataRoot> call, @NonNull Response<DataRoot> response)
            {
                Log.i("RETROFIT", "Termino POST");

                setResult(RESULT_OK);
            }

            @Override
            public void onFailure(@NonNull Call<DataRoot> call, @NonNull Throwable t)
            {

                Log.d("Error agregar pedido " , t.getMessage());
            }
        });
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

    private void setsize()
    {

        Paint textPaint  = lbl_titulo.getPaint();
        float widtht  = textPaint.measureText(lbl_titulo.getText().toString());

        if (widtht > 230)
        {
            widtht = widtht + 40;
            LinearLayout.LayoutParams paramte = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramte.weight = widtht;
            ltex.setLayoutParams(paramte);

            float y = (widtht  - 230)/2;
            float a = 461 - y;
            float b = 508 - y;


            LinearLayout.LayoutParams paramiz = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramiz.weight = (a);
            lizq.setLayoutParams(paramiz);

            LinearLayout.LayoutParams paramde = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramde.weight =  (b);
            lder.setLayoutParams(paramde);

        }
        else
        {

            LinearLayout.LayoutParams paramte = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramte.weight = 230;
            ltex.setLayoutParams(paramte);

            LinearLayout.LayoutParams paramiz = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramiz.weight = (461);
            lizq.setLayoutParams(paramiz);

            LinearLayout.LayoutParams paramde = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramde.weight =  (508);
            lder.setLayoutParams(paramde);
        }
    }

    private void getdata()
    {
        Intent intent = getIntent();
        ArrayList<Plato> data = (ArrayList<Plato>) intent.getExtras().getSerializable("data");
        posiscroll = intent.getExtras().getInt("position");
        dataplat = data;
        lbl_titulo.setText(data.get(posiscroll).getNombre_plato().toUpperCase());
        setsize();
    }

    private void veritran()
    {
        if(lyo_transfer.getVisibility() == View.VISIBLE)
        {
            btn_expa.performClick();
        }
    }

    private void showmessage(String mensaje)
    {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
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

    @SuppressLint("ClickableViewAccessibility")
    void setAdapter()
    {
        adp = new MyAdapter6(this, contorno, ingrediente, agregaplato_abajo, agregaplato_arriba, scroll_pedido, scroll_pedidoa, tlevel, btn_expa, lyo_transfer);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecycler.setLayoutManager(linearLayoutManager);

        myRecycler.setAdapter(adp);


        myRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                veritran();

                return false;
            }
        });





        if (entrada)
        {

            myRecycler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //recyclerView.smoothScrollToPosition(posi);
                    myRecycler.scrollToPosition(posiscroll);

                }
            }, 50);


            entrada = false;
        }

        myRecycler.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            boolean top;
            //boolean ch = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);



                if (top)
                {
                    veritran();
                    posiscroll = linearLayoutManager.findFirstVisibleItemPosition();
                    myRecycler.smoothScrollToPosition(posiscroll);
                    //boolean hasEnded = newState == SCROLL_STATE_IDLE;
                    lbl_titulo.setText(adp.data.get(posiscroll).getNombre_plato().toUpperCase());
                    setsize();

                }
                else
                {
                    veritran();
                    posiscroll = linearLayoutManager.findLastVisibleItemPosition();
                    myRecycler.smoothScrollToPosition(posiscroll);
                    lbl_titulo.setText(adp.data.get(posiscroll).getNombre_plato().toUpperCase());
                    setsize();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0) {
                    top = false;
                } else {

                    if (dx < 0)
                    {
                        top = true;
                    }

                }

            }
        });


        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        final Animation animTranslate2 = AnimationUtils.loadAnimation(this, R.anim.anim_translate2);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                veritran();

                if(linearLayoutManager.findFirstVisibleItemPosition() > 0)
                {
                    myRecycler.scrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
                }

            }
        });

        saltarin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                veritran();

                if (!((MyApplication) getApplicationContext()).getAnimateplato())
                {
                    myRecycler.scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                }
                else
                {
                    v.startAnimation(animTranslate);
                    mov.performClick();
                    ((MyApplication) getApplicationContext()).setAnimateplato(false);

                }
            }});

        mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((MyApplication) getApplicationContext()).getAnimateplato())
                {
                    v.startAnimation(animTranslate2);
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

        agregaplato_abajo.removeAllViews();
        agregaplato_arriba.removeAllViews();
        lyo_transfer.setVisibility(View.INVISIBLE);


        entrada = true;
        //level = false;
        //((MyApplication) getApplicationContext()).setLevel(false);
        tlevel.setText("false");

       // contar = 0;

        dataRoot = myDB.listPedidos();


        if (dataRoot.getEstado_orden() != null )
        {
            recuorder(dataRoot);
        }

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





    }


    private void recuorder(DataRoot dataRoot) //METODO PARA VERIFICAR SI UNA ORDEN FUE RECIBIDA POR COCINA SI CRASHEO EL APP.
    {
        aitem = new ArrayList<>();
        aitem = dataRoot.getOrden();
        for (Itemorder order: aitem)
        {
            creaorder(order, dataRoot, order.getPosi());
        }
    }

    public void creaorder(final Itemorder order, final DataRoot dataRoot, final Integer posi)
    {



        if( (tlevel.getText().equals("true")))
        {

            @SuppressLint("RestrictedApi") CheckBox checkbox = new CheckBox(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
            LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(paramch);
            checkbox.setHeight(30);
            checkbox.setText(order.getNombre_platol());
            checkbox.setTextAppearance( R.style.AppTheme);
            checkbox.setTextColor(Color.BLACK);
            checkbox.setChecked(true);
            checkbox.setEnabled(!order.getEnviado());
            checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.check_box));
            if(!checkbox.isEnabled())
            {
                checkbox.setButtonDrawable(null);
                checkbox.setTextColor(getResources().getColor(R.color.desa));
            }
            checkbox.setPadding(5,0,0,0);
            checkbox.setId(posi);

            agregaplato_arriba.addView(checkbox);

            scroll_pedidoa.post(new Runnable() {
                @Override
                public void run() {
                    scroll_pedidoa.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (!isChecked)
                    {
                        aitem = new ArrayList<>();
                        for (int i = 0; i <agregaplato_arriba.getChildCount() ; i++)
                        {
                          CheckBox sacar = (CheckBox) agregaplato_arriba.getChildAt(i);
                            if (sacar.getId() == buttonView.getId())
                            {
                                agregaplato_arriba.removeViewAt(i);

                                break;




                            }
                        }

                        aitem = dataRoot.getOrden();
                        aitem.remove(order);
                        myDB.updatePedido(dataRoot);



                    }
                }
            });


        }
        else
        {
            @SuppressLint("RestrictedApi") CheckBox checkbox = new CheckBox(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
            LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(paramch);
            checkbox.setHeight(30);
            checkbox.setText(order.getNombre_platol());
            checkbox.setTextAppearance(R.style.AppTheme);
            checkbox.setTextColor(Color.BLACK);
            checkbox.setChecked(true);
            checkbox.setId(posi);
            checkbox.setEnabled(!order.getEnviado());
            checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.check_box));
            if(!checkbox.isEnabled())
            {
                checkbox.setButtonDrawable(null);
                checkbox.setTextColor(getResources().getColor(R.color.desa));
            }
            checkbox.setPadding(5,0,0,0);

            agregaplato_abajo.addView(checkbox);

            scroll_pedido.post(new Runnable() {
                @Override
                public void run() {
                    scroll_pedido.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {

                    // int posb;

                    if (!isChecked)
                    {
                        aitem = new ArrayList<>();
                        for (int i = 0; i <agregaplato_abajo.getChildCount() ; i++)
                        {
                            CheckBox sacar = (CheckBox) agregaplato_abajo.getChildAt(i);
                            if (sacar.getId() == buttonView.getId())
                            {
                                agregaplato_abajo.removeViewAt(i);

                                break;
                            }
                        }
                        aitem = dataRoot.getOrden();
                        aitem.remove(order);
                        myDB.updatePedido(dataRoot);
                    }
                }
            });


        }



    }




}
