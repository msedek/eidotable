package com.eidotab.eidotab;

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
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.eidotab.eidotab.Adapter.MyAdapter5;
import com.eidotab.eidotab.Interfaz.IRequesstMensaje;
import com.eidotab.eidotab.Interfaz.ISendPedido;
import com.eidotab.eidotab.Model.Cuentadata;
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

public class SubMainMenuActivity extends AppCompatActivity
{
    Button btn_express;
    Button btn_mesero;
    Button btn_order;
    Button btn_check;
    Button btn_back;


    Button btn_exp;
    Button btn_expa;
    Boolean level;
    LinearLayout lyo_transfer;
    LinearLayout agregaplato_arriba;
    LinearLayout agregaplato_abajo;
    ScrollView scroll_pedidoa;
    ScrollView scroll_pedido;


    Button saltarin;
    Button btn_left;

    TextView lbl_titulo;

    DBHelper myDB;

    DataRoot list_order;

    ArrayList<Itemorder> aitem;



    String cate;

    LinearLayout lyo_boton;


    LinearLayout mov;

    ArrayList<Plato> categories;
    int position;
    MyAdapter5 adp;
    RecyclerView myRecyclerView;

    LinearLayout lizq;
    LinearLayout ltex;
    LinearLayout lder;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main_menu);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        lyo_boton            = (LinearLayout) findViewById(R.id.lyo_boton);
        btn_back             = (Button) findViewById(R.id.btn_back);
        myRecyclerView       = (RecyclerView) findViewById(R.id.myRecyclerView5);
        btn_mesero           = (Button) findViewById(R.id.btn_mesero);
        btn_express          = (Button) findViewById(R.id.btn_express);
        btn_order            = (Button) findViewById(R.id.btn_order);
        btn_check            = (Button) findViewById(R.id.btn_check);
        lbl_titulo           = (TextView) findViewById(R.id.lbl_titulo);
        saltarin             = (Button) findViewById(R.id.saltarin);
        btn_left             = (Button) findViewById(R.id.btn_left);
        scroll_pedido              = (ScrollView) findViewById(R.id.scrollpedido);
        agregaplato_abajo       = (LinearLayout) findViewById(R.id.lyo_platoabajo);

        btn_exp              = (Button) findViewById(R.id.btn_exp);
        btn_expa              = (Button) findViewById(R.id.btn_expa);
        lyo_transfer         = (LinearLayout) findViewById(R.id.lyo_transfer);
        agregaplato_arriba   = (LinearLayout) findViewById(R.id.agrega_platoarriba);
        scroll_pedidoa       = (ScrollView) findViewById(R.id.scrollpedidoa);

        mov                  = (LinearLayout) findViewById(R.id.mov);

        lizq               = (LinearLayout) findViewById(R.id.lizq);
        lder               = (LinearLayout) findViewById(R.id.lder);
        ltex               = (LinearLayout) findViewById(R.id.ltext);

        //animate =  ((MyApplication) this.getApplication()).getAnimatesubmainmenu();


        myDB = DBHelper.GetDBHelper(this);



        aitem = new ArrayList<>();

        btn_exp.setOnClickListener(new View.OnClickListener() //ESTOY ABAJO Y PRESIONO PARA SUBIR
        {
            @Override
            public void onClick(View v)
            {
                level = true;
                list_order = myDB.listPedidos();
                if (list_order.getEstado_orden() != null )
                {
                    recuorder(list_order);
                }
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
                level = false;// INDICA EN QUE LAYOUT VOY OPERAR
                list_order = myDB.listPedidos();
                if (list_order.getEstado_orden() != null )
                {
                    recuorder(list_order);
                }
                lyo_transfer.setVisibility(View.INVISIBLE);
                agregaplato_arriba.removeAllViews();
            }
        });



        lyo_boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btn_back.performClick();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_mesero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmessage2("LLAMASTE AL MESERO");
                veritran();

                Mensaje mensaje = new Mensaje();

                Tablet tablet = myDB.listTablet();
                mensaje.setEstadomensaje("PENDIENTE");
                mensaje.setRemitente("MESA " + tablet.getNro_mesa());
                mensaje.setTexto("LO SOLICITAN EN LA MESA");

                sendmensaje(mensaje);


            }
        });

        btn_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmessage2("FUISTE AL MENU EXPRESS");
                veritran();
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                veritran();
                ArrayList<Itemorder>filtrorder = new ArrayList<>();
                ArrayList<Itemorder>listo = new ArrayList<>();

                DataRoot dataenviar = myDB.listPedidos();

                if(dataenviar.getOrden() != null)
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

                        btn_check.setEnabled(true);

                        showmessage2("COLOCASTE EL PEDIDO");


                        agregaplato_abajo.removeAllViews();
                        agregaplato_arriba.removeAllViews();
                        lyo_transfer.setVisibility(View.INVISIBLE);

                        level = false;



                        recuorder(dataenviar);

                    }
                    else
                    {
                        showmessage2("SELECCIONE ALGÚN ARTÍCULO");
                    }

                }
                else
                {
                    showmessage2("SELECCIONE ALGÚN ARTÍCULO");
                }




            }
        });

        btn_check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // showmessage2("PEDISTE LA CUENTA");
                veritran();
                Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                startActivity(intent);
            }
        });

        btn_check.setEnabled(false);

        categories = new ArrayList<>();

        inimain();
        setAdapter();

        ArrayList<String> catego = new ArrayList<>();

        Collections.sort(categories);

        for (int i = 0; i <categories.size() ; i++)
        {
            if (i == 0)
            {
                catego.add(categories.get(i).getSub_categoria_plato());
            }
            if (i > 0)
            {
                if (!(categories.get(i).getSub_categoria_plato().equals(categories.get(i-1).getSub_categoria_plato())))
                {
                    catego.add(categories.get(i).getSub_categoria_plato());
                }
            }
        }

        for (String elem : catego)
        {
            position = adp.AddNew(elem);
        }

       // saltarin.performClick();

        if(catego.size()>4)
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





    private void veritran()
    {
        if(lyo_transfer.getVisibility() == View.VISIBLE)
        {
            btn_expa.performClick();
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


        ArrayList<Cuentadata> test = myDB.listCuenta();

        btn_check.setEnabled(test.size() > 0);


        list_order = myDB.listPedidos();

        level = false;


        //dataRoot = myDB.listPedidos();


        if (list_order.getEstado_orden() != null )
        {
            recuorder(list_order);
        }


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

    private void creaorder(final Itemorder order, final DataRoot dataRoot, final Integer posi)
    {



        if(level)
        {

            CheckBox checkbox = new CheckBox(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
            LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(paramch);
            checkbox.setHeight(30);
            checkbox.setText(order.getNombre_platol());
            checkbox.setTextAppearance(getApplicationContext(), R.style.AppTheme);
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
            CheckBox checkbox = new CheckBox(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
            LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(paramch);
            checkbox.setHeight(30);
            checkbox.setText(order.getNombre_platol());
            checkbox.setTextAppearance( R.style.AppTheme);
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

    private void inimain()
    {
        Intent intent = getIntent();
        ArrayList<Plato> data = (ArrayList<Plato>) intent.getExtras().getSerializable("data");
        cate = (String) intent.getExtras().get("cate");
        assert cate != null;
        lbl_titulo.setText(cate.toUpperCase());
        setsize();
        categories = data;
    }

    void setAdapter()
    {
        adp = new MyAdapter5(this, categories);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(adp);

        myRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                veritran();
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
                    myRecyclerView.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
                }

            }
        });

        saltarin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                veritran();
                if (!((MyApplication) getApplicationContext()).getAnimatesubmainmenu())
                {
                    myRecyclerView.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                }
                else
                {
                    v.startAnimation(animTranslate);
                    mov.performClick();
                    ((MyApplication) getApplicationContext()).setAnimatesubmainmenu(false);
                }
            }});

        mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MyApplication) getApplicationContext()).getAnimatesubmainmenu())
                {
                    v.startAnimation(animTranslate2);
                }

            }
        });


    }



}
