package com.eidotab.eidotab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.eidotab.Adapter.MyAdapter3;
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

public class SugeCheff extends AppCompatActivity
{

    ArrayList<Plato> lista_platos;

    int position;
    MyAdapter3 adp;
    RecyclerView myRecyclerView;


    DBHelper myDB;

    DataRoot list_order;

    ArrayList<Itemorder> aitem;

    Button btn_exp;
    Button btn_expa;
    Boolean level;
    LinearLayout lyo_transfer;
    LinearLayout agregaplato_arriba;
    LinearLayout agregaplato_abajo;
    ScrollView scroll_pedidoa;
    ScrollView scroll_pedido;


    LinearLayout lyo_boton;

    Button btn_back;
    Button saltarin;
    Button btn_left;
    Button btn_mesero;
    Button btn_order;
    Button btn_check;
    Button btn_express;


    Boolean sugecheff;


    TextView lbl_tituloch;

    ArrayList<Plato> odd;
    ArrayList<Plato> nood;

    Boolean swmodo;

    LinearLayout lizq;
    LinearLayout ltex;
    LinearLayout lder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suge_cheff);


        lyo_boton            = (LinearLayout) findViewById(R.id.lyo_boton);
        btn_mesero           = (Button) findViewById(R.id.btn_mesero);
        btn_order            = (Button) findViewById(R.id.btn_order);
        btn_check            = (Button) findViewById(R.id.btn_check);
        lbl_tituloch         = (TextView) findViewById(R.id.lbl_tituloch);
        myRecyclerView       = (RecyclerView) findViewById(R.id.myRecyclerView3);
        btn_back             = (Button) findViewById(R.id.btn_back);
        saltarin             = (Button) findViewById(R.id.saltarin);
        btn_left             = (Button) findViewById(R.id.btn_left);
        scroll_pedido             = (ScrollView) findViewById(R.id.scrollpedido);
        agregaplato_abajo       = (LinearLayout) findViewById(R.id.lyo_platoabajo);
        btn_express          = (Button) findViewById(R.id.btn_express);

        btn_exp              = (Button) findViewById(R.id.btn_exp);
        btn_expa              = (Button) findViewById(R.id.btn_expa);
        lyo_transfer         = (LinearLayout) findViewById(R.id.lyo_transfer);
        agregaplato_arriba   = (LinearLayout) findViewById(R.id.agrega_platoarriba);
        scroll_pedidoa       = (ScrollView) findViewById(R.id.scrollpedidoa);


        lizq               = (LinearLayout) findViewById(R.id.lizq);
        lder               = (LinearLayout) findViewById(R.id.lder);
        ltex               = (LinearLayout) findViewById(R.id.ltext);

        myDB = DBHelper.GetDBHelper(this);

        sugecheff = false;

        aitem = new ArrayList<>();

        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);


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


        saltarin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                veritran();
                arg0.startAnimation(animTranslate);
            }});


        swmodo = true;

        lyo_boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btn_back.performClick();
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

/*        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMainActivity= new Intent(SubMenuActivity.this, Main_menu_Activity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity,0);
                finish();
            }
        });*/

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

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showmessage2("PEDISTE LA CUENTA");
                veritran();
                Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                startActivity(intent);
            }
        });

        btn_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmessage2("FUISTE AL MENU EXPRESS");
                veritran();
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lista_platos = new ArrayList<>();
        odd = new ArrayList<>();
        nood = new ArrayList<>();


        inimain();
        setAdapter();

        Collections.sort(lista_platos);

        if(!sugecheff)
        {
            for (int i = 0; i < lista_platos.size(); i++)
            {

                String modo = "";

                if (swmodo)    //IF DISCRIMINA QUE CATEGORIAS POSEEN SUBCATE Y CUALES NO
                {
                    modo = lista_platos.get(i).getCategoria_plato();
                }
                else
                {
                    if(!swmodo)
                    {
                        modo = lista_platos.get(i).getSub_categoria_plato();
                    }
                }

                if (!(modo.equals(lbl_tituloch.getText())))
                {
                    lista_platos.remove(i);
                    Collections.sort(lista_platos);
                    i = -1;
                }
            }
        }
        else
        {
            Collections.sort(lista_platos);
        }


        for (int i = 0; i < lista_platos.size(); i++)
        {
            if(!isOdd(i))
            {
                nood.add(lista_platos.get(i));
            }

            if(isOdd(i))
            {
                odd.add(lista_platos.get(i));
            }
        }

        for (Plato plato : nood)
        {
            position = adp.AddNew(plato);
        }

        //saltarin.performClick();

        if(lista_platos.size() > 10)
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

        Paint textPaint  = lbl_tituloch.getPaint();
        float widtht  = textPaint.measureText(lbl_tituloch.getText().toString());

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

    private void veritran()
    {
        if(lyo_transfer.getVisibility() == View.VISIBLE)
        {
            btn_expa.performClick();
        }
    }

    boolean isOdd( int val )
    {
        return (val & 0x01) != 0;
    }

    void setAdapter()
    {
        adp = new MyAdapter3(this, odd);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(adp);

        myRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                veritran();
            }
        });

    }

/*    private void showmessage(String mensaje)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }*/

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
        lbl_tituloch.setText((String) intent.getExtras().get("cate"));
        setsize();
        Boolean bsuge =(Boolean) intent.getExtras().get("bsuge");
        Boolean swaux = (Boolean) intent.getExtras().get("swmodo");
        if(bsuge)
        {
            sugecheff = true;
        }
        if (!swaux)
        {
            swmodo = false;
        }
        lista_platos = data;
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

        //((MyApplication) getApplicationContext()).setSugecheffbool(true);

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
            checkbox.setTextAppearance(getApplicationContext(), R.style.AppTheme);
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
