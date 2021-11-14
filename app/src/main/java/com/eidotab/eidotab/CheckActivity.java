package com.eidotab.eidotab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.eidotab.eidotab.Interfaz.IRequesstMensaje;
import com.eidotab.eidotab.Model.Cuentadata;
import com.eidotab.eidotab.Model.Mensaje;
import com.eidotab.eidotab.Model.Tablet;
import com.eidotab.eidotab.SQlite.DBHelper;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckActivity extends AppCompatActivity
{
  Button btn_mesero;
  LinearLayout sharedparent;
  ArrayList<Cuentadata> data;
  LinearLayout listaplato;
  ScrollView scrolllistplato;
  TextView lbl_descritotal;
  TextView lbl_total;
  DBHelper myDB;
  Button btn_variac;
  Button btn_unac;
  Button btn_back;
  LinearLayout lyo_boton;
  LinearLayout lyo_parentsubcuenta;
  TextView lbl_toti;
  TextView lbl_toti2;
  EditText txt_ruc;
  RadioButton radiofa;
  RadioButton radiobo;
  Button btn_sigo;
  Button btn_fin;
  TextView razonsoc;
  Boolean modobolunac;
  Integer cuentas;
  Boolean multia;
  Integer cantdata;
  TextView txt_cuenta;
  TextView lbruc;
  ScrollView scrollrazon;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check);
    // getSupportFragmentManager().beginTransaction().replace(R.id.frmContainer, new ConsumoFragment()).addToBackStack(null).commit();

    //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);

    btn_mesero          = (Button)       findViewById(R.id.btn_meserocu);
    sharedparent        = (LinearLayout) findViewById(R.id.sharedparent);
    listaplato          = (LinearLayout) findViewById(R.id.listaplato);
    scrolllistplato     = (ScrollView)   findViewById(R.id.scrolllistplato);
    lbl_descritotal     = (TextView)     findViewById(R.id.lbl_descritotal);
    lbl_total           = (TextView)     findViewById(R.id.lbl_total);
    lyo_boton           = (LinearLayout) findViewById(R.id.lyo_boton);
    btn_back            = (Button)  findViewById(R.id.btn_back);
    txt_cuenta          = (TextView)     findViewById(R.id.txt_cuenta);
    btn_unac            = (Button)       findViewById(R.id.btn_unac);
    btn_variac          = (Button)       findViewById(R.id.btn_variac);
    radiofa             = (RadioButton)  findViewById(R.id.radiofa);
    radiobo             = (RadioButton)  findViewById(R.id.radiobo);
    lbl_toti            = (TextView)     findViewById(R.id.lbl_toti);
    lbl_toti2           = (TextView)     findViewById(R.id.lbl_toti2);
    txt_ruc             = (EditText)     findViewById(R.id.txt_ruc);
    lbruc               = (TextView)     findViewById(R.id.lbruc);
    razonsoc            = (TextView)     findViewById(R.id.razonsoc);
    btn_sigo            = (Button)       findViewById(R.id.btn_sigo);
    btn_fin             = (Button)       findViewById(R.id.btn_fin);
    lyo_parentsubcuenta = (LinearLayout) findViewById(R.id.lyo_parentcsubcuenta);
    scrollrazon         = (ScrollView)   findViewById(R.id.scrollrazon);


    myDB = DBHelper.GetDBHelper(this);
    data = myDB.listCuenta();
    multia = data.size() > 1;

    btn_variac.setEnabled(multia);

    cuentas = 0;

    modobolunac = true;

    Double total = 0.0;

    for(Cuentadata cdata : data)
    {
      total = total + cdata.getPrecio_plato();

    }

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

    cantdata = data.size();
    createitem(data);

    lbl_total.setText(String.format(Locale.GERMANY, "%,.2f", total) + " S/.");
    btn_unac.setText(String.format(Locale.GERMANY, "%,.2f", total) + " S/.");

    btn_unac.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        btn_variac.setVisibility(View.INVISIBLE);
        btn_unac.setEnabled(false);
        lbl_toti2.setText(lbl_total.getText());
        sharedparent.setVisibility(View.VISIBLE);
      }
    });

    btn_variac.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        btn_variac.setVisibility(View.INVISIBLE);
        btn_unac.setVisibility(View.INVISIBLE);
        radiofa.setEnabled(false);
        radiobo.setEnabled(false);
        modobolunac = false;
        listaplato.removeAllViews();
        createitem(data);
        lbl_toti2.setText("0 S/.");
        sharedparent.setVisibility(View.VISIBLE);

      }
    });

    radiobo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(modobolunac)
        {
          if(b)
          {
            btn_fin.post(new Runnable() {
              @Override
              public void run() {
                btn_fin.setVisibility(View.VISIBLE);
              }
            });
          }
          else
          {
            btn_fin.setVisibility(View.INVISIBLE);
            scrollrazon.setVisibility(View.INVISIBLE);
          }
        }
        else
        {
          if(cantdata == 0)
          {
            if(b)
            {
              btn_fin.post(new Runnable() {
                @Override
                public void run() {
                  btn_fin.setVisibility(View.VISIBLE);
                }
              });
            }
            else
            {
              btn_fin.setVisibility(View.INVISIBLE);
              scrollrazon.setVisibility(View.INVISIBLE);
            }
          }
          else
          {
            if(b)
            {

              btn_sigo.post(new Runnable() {
                @Override
                public void run() {
                  btn_sigo.setVisibility(View.VISIBLE);
                }
              });

            }
            else
            {
              btn_sigo.setVisibility(View.INVISIBLE);
              scrollrazon.setVisibility(View.INVISIBLE);
            }
          }
        }

      }
    });

    radiofa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(b)
        {
          txt_ruc.setText("");
          razonsoc.setText("");
          scrollrazon.setVisibility(View.INVISIBLE);

          lbruc.post(new Runnable() {
            @Override
            public void run() {
              lbruc.setVisibility(View.VISIBLE);
            }
          });

          txt_ruc.post(new Runnable() {
            @Override
            public void run() {
              txt_ruc.setVisibility(View.VISIBLE);
            }
          });

          razonsoc.post(new Runnable() {
            @Override
            public void run() {
              razonsoc.setVisibility(View.VISIBLE);
            }
          });
        }
        else
        {
          lbruc.setVisibility(View.INVISIBLE);
          txt_ruc.setVisibility(View.INVISIBLE);
          razonsoc.setVisibility(View.INVISIBLE);
        }
      }
    });

    txt_ruc.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if(modobolunac)
        {
          if(charSequence.length()>0)
          {
            btn_fin.setVisibility(View.VISIBLE);
            scrollrazon.setVisibility(View.VISIBLE);
          }
          else
          {
            btn_fin.setVisibility(View.INVISIBLE);
            scrollrazon.setVisibility(View.INVISIBLE);
          }
        }
        else
        {
          if(cantdata == 0)
          {
            if(charSequence.length()>0)
            {
              btn_fin.post(new Runnable() {
                @Override
                public void run() {
                  btn_fin.setVisibility(View.VISIBLE);
                }
              });

              scrollrazon.post(new Runnable() {
                @Override
                public void run() {
                  scrollrazon.setVisibility(View.VISIBLE);
                }
              });
            }
            else
            {
              btn_fin.setVisibility(View.INVISIBLE);
              scrollrazon.setVisibility(View.INVISIBLE);
            }
          }
          else
          {
            if(charSequence.length()>0)
            {

              btn_sigo.post(new Runnable() {
                @Override
                public void run() {
                  btn_sigo.setVisibility(View.VISIBLE);
                }
              });

              scrollrazon.post(new Runnable() {
                @Override
                public void run() {
                  scrollrazon.setVisibility(View.VISIBLE);
                }
              });

            }
            else
            {
              btn_sigo.setVisibility(View.INVISIBLE);
              scrollrazon.setVisibility(View.INVISIBLE);
            }
          }
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    btn_fin.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {

        //TODO PROCESO DE ENVIO A CAJA LA DATA DE COBRO
        Intent i = new Intent(CheckActivity.this, CloseActivity.class);
        startActivity(i);

      }
    });

    btn_sigo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        //TODO
        limpodata();
        lyo_parentsubcuenta.removeAllViews();
        lbl_toti2.setText("0 S/.");

        Integer ncu = Integer.parseInt(txt_cuenta.getText().toString().replace("CUENTA ", "").trim());
        ncu++;
        txt_cuenta.setText("CUENTA " + ncu);

      }
    });


    btn_back.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {

        finish();

      }
    });

    lyo_boton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {


        finish();

      }
    });

    setupMainWindowDisplayMode();
  }

  private void createitem(ArrayList<Cuentadata> lista)
  {
    for(Cuentadata item : lista)
    {
      LinearLayout linearlayout = new LinearLayout(getApplicationContext());
      LinearLayout.LayoutParams paramln = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
      linearlayout.setLayoutParams(paramln);
      linearlayout.setWeightSum(100);
      linearlayout.setOrientation(LinearLayout.HORIZONTAL);

      if(!modobolunac)
      {
        CheckBox checkbox = new CheckBox(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramch.weight = 70;
        checkbox.setLayoutParams(paramch);
        checkbox.setHeight(30);
        checkbox.setText(item.getNombre_plato());
        checkbox.setTextAppearance(getApplicationContext(), R.style.AppTheme);
        checkbox.setTextColor(Color.BLACK);
        checkbox.setChecked(true);
        checkbox.setButtonDrawable(getApplicationContext().getResources().getDrawable(R.drawable.check_boxplus));
        checkbox.setPadding(5,0,0,0);

        linearlayout.addView(checkbox);
      }
      else
      {

        TextView textview = new TextView(getApplicationContext());
        LinearLayout.LayoutParams paramtx = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramtx.weight = 70;
        textview.setLayoutParams(paramtx);
        textview.setText(item.getNombre_plato());
        textview.setTextColor(Color.BLACK);

        linearlayout.addView(textview);
      }

      final TextView textview = new TextView(getApplicationContext());
      LinearLayout.LayoutParams paramtx = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
      paramtx.weight = 30;
      textview.setLayoutParams(paramtx);
      textview.setGravity(Gravity.RIGHT);
      textview.setText(String.format(Locale.GERMANY, "%,.2f", item.getPrecio_plato()) + " S/." );
      textview.setTextColor(Color.BLACK);

      linearlayout.addView(textview);

      listaplato.addView(linearlayout);

      if(!modobolunac)
      {
        for (int i = 0; i < listaplato.getChildCount(); i++) {

          final LinearLayout lchild = (LinearLayout) listaplato.getChildAt(i);

          for (int j = 0; j <lchild.getChildCount() ; j++)
          {

            View view = lchild.getChildAt(j);

            final Integer ind = j + 1;
            final Integer ind2 = j;

            if(view instanceof CheckBox)
            {
              final CheckBox check = (CheckBox) lchild.getChildAt(j);

              check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                  Cuentadata cuent = new Cuentadata();

                  Integer ncu = Integer.parseInt(txt_cuenta.getText().toString().replace("CUENTA ", "").trim());

                  if(ncu == 0)
                  {
                    ncu++;
                  }

                  txt_cuenta.setText("CUENTA " + ncu);
                  txt_cuenta.setVisibility(View.VISIBLE);

                  radiobo.setEnabled(true);
                  radiofa.setEnabled(true);

                  cantdata--;

                  if(cantdata == 0)
                  {
                    if(btn_sigo.getVisibility() == View.VISIBLE)
                    {
                      btn_sigo.setVisibility(View.INVISIBLE);
                      btn_fin.setVisibility(View.VISIBLE);
                    }
                  }

                  cuent.setNombre_plato(check.getText().toString());
                  TextView textview = (TextView) lchild.getChildAt(ind);
                  String prec =  textview.getText().toString().replace("S/.", "");
                  NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMANY);
                  lchild.removeViewAt(ind);
                  lchild.removeViewAt(ind2);
                  String tot = lbl_total.getText().toString().replace("S/.", "");
                  lbl_descritotal.setText("Total restante (incluido IGV)");
                  try
                  {
                    double val = nf_in.parse(prec).doubleValue();
                    double val2 = nf_in.parse(tot).doubleValue();
                    double stot = val2 - val;
                    lbl_total.setText(String.format(Locale.GERMANY, "%,.2f", stot) + " S/." );
                    cuent.setPrecio_plato(val);
                  } catch (ParseException e)
                  {
                    e.printStackTrace();
                  }

                  LinearLayout linearlayouts = new LinearLayout(getApplicationContext());
                  LinearLayout.LayoutParams paramln = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                  linearlayouts.setLayoutParams(paramln);
                  linearlayouts.setWeightSum(100);
                  linearlayouts.setOrientation(LinearLayout.HORIZONTAL);

                  CheckBox checkboxs = new CheckBox(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
                  LinearLayout.LayoutParams paramchs = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                  paramchs.weight = 70;
                  checkboxs.setLayoutParams(paramchs);
                  checkboxs.setHeight(30);
                  checkboxs.setText(cuent.getNombre_plato());
                  checkboxs.setTextAppearance(getApplicationContext(), R.style.AppTheme);
                  checkboxs.setTextColor(Color.BLACK);
                  checkboxs.setChecked(true);
                  checkboxs.setButtonDrawable(getApplicationContext().getResources().getDrawable(R.drawable.minus));
                  checkboxs.setPadding(5,0,0,0);

                  linearlayouts.addView(checkboxs);

                  final TextView textviews = new TextView(getApplicationContext());
                  LinearLayout.LayoutParams paramtxs = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                  paramtxs.weight = 30;
                  textviews.setLayoutParams(paramtxs);
                  textviews.setGravity(Gravity.RIGHT);
                  textviews.setText(String.format(Locale.GERMANY, "%,.2f", cuent.getPrecio_plato()) + " S/." );
                  textviews.setTextColor(Color.BLACK);

                  linearlayouts.addView(textview);

                  lyo_parentsubcuenta.addView(linearlayouts);

                  String preci =  lbl_toti2.getText().toString().replace("S/.", "");

                  try
                  {
                    double val = nf_in.parse(preci).doubleValue();
                    double val2 = cuent.getPrecio_plato();
                    double stot = val2 + val;
                    lbl_toti2.setText(String.format(Locale.GERMANY, "%,.2f", stot) + " S/." );
                  }
                  catch (ParseException e)
                  {
                    e.printStackTrace();
                  }

                  listensubparent();
                }
              });

              if(lchild.getChildCount() == 0)
              {
                listaplato.removeViewAt(i);
              }

            }
          }
        }
      }
    }
  }

  private void listensubparent()
  {
    for (int w = 0; w < lyo_parentsubcuenta.getChildCount(); w++)
    {
      final LinearLayout lchilds = (LinearLayout) lyo_parentsubcuenta.getChildAt(w);

      for (int y = 0; y < lchilds.getChildCount();y++) {

        View views = lchilds.getChildAt(y);

        final Integer ind = y + 1;
        final Integer ind2 = y;

        if (views instanceof CheckBox)
        {
          final CheckBox checks = (CheckBox) lchilds.getChildAt(y);

          checks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Cuentadata cuent = new Cuentadata();

              cantdata++;

              if(cantdata > 0)
              {
                if(btn_fin.getVisibility() == View.VISIBLE)
                {
                  btn_sigo.setVisibility(View.VISIBLE);
                  btn_fin.setVisibility(View.INVISIBLE);
                }
              }

              cuent.setNombre_plato(checks.getText().toString());
              TextView textview = (TextView) lchilds.getChildAt(ind);
              String prec =  textview.getText().toString().replace("S/.", "");
              NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMANY);
              lchilds.removeViewAt(ind);
              lchilds.removeViewAt(ind2);


              String tot = lbl_toti2.getText().toString().replace("S/.", "");

              try
              {
                double val = nf_in.parse(prec).doubleValue();
                double val2 = nf_in.parse(tot).doubleValue();
                double stot = val2 - val;
                lbl_toti2.setText(String.format(Locale.GERMANY, "%,.2f", stot) + " S/." );
                cuent.setPrecio_plato(val);
              } catch (ParseException e)
              {
                e.printStackTrace();
              }

              String ld =  lbl_toti2.getText().toString().replace("S/.", "");

              try {
                double dld = nf_in.parse(ld).doubleValue();
                if(dld == 0.00)
                {
                  Integer ncu = Integer.parseInt(txt_cuenta.getText().toString().replace("CUENTA ", "").trim());

                  if(ncu == 1)
                  {
                    txt_cuenta.setText("CUENTA 0");
                    txt_cuenta.setVisibility(View.INVISIBLE);

                  }

                  lbl_descritotal.setText("Total (incluido IGV)");
                  limpodata();
/*                                    radiobo.setChecked(false);
                                    radiofa.setChecked(false);
                                    radiofa.setEnabled(false);
                                    radiobo.setEnabled(false);
                                    btn_fin.setVisibility(View.INVISIBLE);
                                    btn_sigo.setVisibility(View.INVISIBLE);
                                    txt_ruc.setText("");
                                    scrollrazon.setVisibility(View.INVISIBLE);
                                    razonsoc.setText("");*/

                }
              }
              catch (ParseException e)
              {
                e.printStackTrace();
              }

              ArrayList<Cuentadata> retro = new ArrayList<>();

              retro.add(cuent);

              String preci =  lbl_total.getText().toString().replace("S/.", "");

              try
              {
                double val = nf_in.parse(preci).doubleValue();
                double val2 = cuent.getPrecio_plato();
                double stot = val2 + val;
                lbl_total.setText(String.format(Locale.GERMANY, "%,.2f", stot) + " S/." );
              }
              catch (ParseException e)
              {
                e.printStackTrace();
              }


              createitem(retro);
            }
          });

          if(lchilds.getChildCount() == 0)
          {
            lyo_parentsubcuenta.removeViewAt(w);
          }
        }
      }
    }
  }

  private  void limpodata()
  {
    radiobo.setChecked(false);
    radiofa.setChecked(false);
    radiofa.setEnabled(false);
    radiobo.setEnabled(false);
    btn_fin.setVisibility(View.INVISIBLE);
    btn_sigo.setVisibility(View.INVISIBLE);
    txt_ruc.setText("");
    scrollrazon.setVisibility(View.INVISIBLE);
    razonsoc.setText("");
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
        Log.i("RETROFIT", "Llamo al mesero");

        setResult(RESULT_OK);
      }

      @Override
      public void onFailure(@NonNull Call<Mensaje> call, @NonNull Throwable t)
      {

        Log.d("Error agregar pedido " , t.getMessage());
      }
    });
  }

  private void setupMainWindowDisplayMode()
  {
    View decorView = setSystemUiVisibilityMode();
    decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
      @Override
      public void onSystemUiVisibilityChange(int visibility) {
        setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
      }
    });
  }

  private View setSystemUiVisibilityMode()
  {
    View decorView = getWindow().getDecorView();
    int options;
    options =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    decorView.setSystemUiVisibility(options);
    return decorView;
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



    setupMainWindowDisplayMode();
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus)
  {
    super.onWindowFocusChanged(hasFocus);

    if(hasFocus)
    {
      setupMainWindowDisplayMode();
    }

  }

}

     /*   scrolllistplato.post(new Runnable() {
            @Override
            public void run() {
                scrolllistplato.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
*/
