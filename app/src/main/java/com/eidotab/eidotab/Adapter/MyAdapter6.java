package com.eidotab.eidotab.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eidotab.eidotab.Model.Cuentadata;
import com.eidotab.eidotab.Model.DataRoot;
import com.eidotab.eidotab.Model.Itemorder;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.Model.Tablet;
import com.eidotab.eidotab.R;
import com.eidotab.eidotab.SQlite.DBHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter6 extends RecyclerView.Adapter<MyAdapter6.VH>
{

    private Context mContext;
    public ArrayList<Plato> data;

    private ArrayList<String> contornox;
    private ArrayList<String> ingredientex;
    //private AutofitTextView lb_nombreplato;
    private DBHelper myDB;
    private Tablet tablet;
    private Itemorder itemorder;
    private LinearLayout agregaplat_abajo;
    private LinearLayout agregaplat_arriba;
    private ScrollView scrol_pedido;
    private ScrollView scrol_pedidoa;
    private ArrayList<Itemorder> aitem;
    private TextView tleve;
    private LinearLayout bt_expa;
    private LinearLayout ly_transfer;




    public MyAdapter6(Context context, ArrayList<String> contorno, ArrayList<String> ingrediente, LinearLayout agregaplato_abajo, LinearLayout agregaplato_arriba, ScrollView scroll_pedido, ScrollView scroll_pedidoa, TextView tlevel, LinearLayout btn_expa, LinearLayout lyo_transfer)
    {
        this.contornox = contorno;
        this.ingredientex = ingrediente;
        this.mContext = context;
        //this.lb_nombreplato = lbl_nombreplato;
        data = new ArrayList<>();
        myDB = DBHelper.GetDBHelper(mContext);
        tablet = myDB.listTablet();
        this.agregaplat_abajo = agregaplato_abajo;
        this.agregaplat_arriba = agregaplato_arriba;
        this.scrol_pedido = scroll_pedido;
        this.scrol_pedidoa = scroll_pedidoa;
        this.tleve = tlevel;
        this.bt_expa = btn_expa;
        this.ly_transfer = lyo_transfer;
    }

    public int AddNew(Plato plato)
    {
        data.add(plato);
        int position = data.indexOf(plato);
        notifyItemInserted(position);
        return position;
    }

    @Override
    public MyAdapter6.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row5, parent, false);
        return new MyAdapter6.VH(v);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final MyAdapter6.VH holder, final int position)
    {

        final Plato plato = data.get(position);

        int cantin = plato.getIngrediente().size();

        if(cantin == 0)
        {
            holder.lbl_ingre.setVisibility(View.INVISIBLE);
        }


        int cancon = plato.getContorno().size();

        if(cancon == 0)
        {
            holder.lbl_adic.setVisibility(View.INVISIBLE);
        }


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(sdcard, plato.getFoto_movil());
            Uri uri = Uri.fromFile(file);
            Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().dontTransform().into(holder.imageSuge);
        }

        holder.lbl_descriplato.setText(plato.getDescripcion());

        holder.llena_conto(plato);

        holder.lbl_precioplato.setText(" " + String.format(Locale.GERMANY, "%,.2f", plato.getPrecio_plato()) + " S/.");

        //<editor-fold desc="btn_add">
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showmessage(plato.getNombre_plato());

                veritran();


                Cuentadata cuentadata = new Cuentadata();

                //Integer recyvent = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                DataRoot comanda = myDB.getPedido(tablet.getNro_mesa());
                itemorder = new Itemorder();
                final int posi = agregaplat_abajo.getChildCount();

                itemorder.setPosi(posi);

                //String cate = dataplat.get(recyvent).getCategoria_plato().toLowerCase();
                String cate = plato.getCategoria_plato().toLowerCase();
                itemorder.setCategoria(cate);
                itemorder.setEstadoitem("pendiente");
                itemorder.setEnviado(false);
                //String nplato = dataplat.get(recyvent).getNombre_plato();
                String nplato = plato.getNombre_plato();

                itemorder.setNombre_plato(nplato);

                cuentadata.setNombre_plato(nplato);



                if (contornox.size() > 0) {
                    int tope = contornox.size();

                    ArrayList<String> clleno = new ArrayList<>();

                    for (String propio : contornox) {
                        clleno.add(propio);

                        int inde = contornox.indexOf(propio);

                        if (inde == 0) {
                            nplato = nplato + " (Sin.";
                        }

                        nplato = nplato + propio;

                        if (inde >= 0 && inde < tope - 1) {
                            nplato = nplato + ", ";
                        }

                        if (tope - 1 == inde) {
                            nplato = nplato + ")";
                        }
                    }
                    itemorder.setContornos(clleno);
                }
                else
                {
                    ArrayList<String> cvacio = new ArrayList<>();
                    itemorder.setContornos(cvacio);
                }

                if (ingredientex.size() > 0)
                {
                    int tope2 = ingredientex.size();

                    ArrayList<String> illeno = new ArrayList<>();

                    for (String adic : ingredientex)
                    {
                        illeno.add(adic);

                        int inde = ingredientex.indexOf(adic);

                        if (inde == 0)
                        {
                            nplato = nplato + " (Adc.";
                        }

                        nplato = nplato +  adic;

                        if (inde >= 0 && inde < tope2 - 1)
                        {
                            nplato = nplato + ", ";
                        }

                        if (tope2 - 1 == inde)
                        {
                            nplato = nplato + ")";
                        }
                    }

                    itemorder.setIngredientes(illeno);

                }
                else
                {
                    ArrayList<String> ivacio = new ArrayList<>();
                    itemorder.setIngredientes(ivacio);
                }

                itemorder.setNombre_platol(nplato);

                contornox.clear();
                ingredientex.clear();

                itemorder.setSub_categoria(plato.getSub_categoria_plato().toLowerCase());
                itemorder.setPrecio_plato(plato.getPrecio_plato());
                cuentadata.setPrecio_plato(plato.getPrecio_plato());
                itemorder.setFoto_movil(plato.getFoto_movil());


                creaorder(itemorder, comanda, itemorder.getPosi());

                aitem = new ArrayList<>();

                myDB.addCuenta(cuentadata);

                if(comanda.getEstado_orden() == null)
                {
                    comanda.setEstado_orden("pendiente");
                    comanda.setIdinterno(tablet.getNro_mesa());
                    aitem.add(itemorder);
                    comanda.setOrden(aitem);
                    myDB.addPedido(comanda);

                    showmessage("Agregaste " + itemorder.getNombre_plato());

                }
                else
                {
                    aitem = comanda.getOrden();
                    aitem.add(itemorder);
                    myDB.updatePedido(comanda);

                    showmessage("Agregaste " + itemorder.getNombre_plato());

                }

                notifyDataSetChanged(); //REFRESCAR EL ADAPTER



            }
        });
        //</editor-fold>


    }

    private void showmessage(String mensaje)
    {
        final Toast toast = Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 800);

    }

    @Override
    public int getItemCount()
    {

        return data.size();

    }

    private void veritran()
    {
        if(ly_transfer.getVisibility() == View.VISIBLE)
        {
            bt_expa.performClick();
        }
    }

    public void creaorder(final Itemorder order, final DataRoot dataRoot, final Integer posi)
    {

        if(tleve.getText().equals("true"))
        {

            @SuppressLint("RestrictedApi") CheckBox checkbox = new CheckBox(new ContextThemeWrapper(mContext, R.style.AppTheme));
            LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(paramch);
            checkbox.setHeight(30);
            checkbox.setText(order.getNombre_platol());
            checkbox.setTextAppearance( R.style.AppTheme);
            checkbox.setTextColor(Color.BLACK);
            checkbox.setChecked(true);
            checkbox.setEnabled(!order.getEnviado());
            checkbox.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.check_box));
            if(!checkbox.isEnabled())
            {
                checkbox.setButtonDrawable(null);
                checkbox.setTextColor(mContext.getResources().getColor(R.color.desa));
            }
            checkbox.setPadding(5,0,0,0);
            checkbox.setId(posi);

            agregaplat_arriba.addView(checkbox);

            scrol_pedidoa.post(new Runnable() {
                @Override
                public void run() {
                    scrol_pedidoa.fullScroll(ScrollView.FOCUS_DOWN);
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
                        for (int i = 0; i <agregaplat_arriba.getChildCount() ; i++)
                        {
                            CheckBox sacar = (CheckBox) agregaplat_arriba.getChildAt(i);
                            if (sacar.getId() == buttonView.getId())
                            {
                                agregaplat_arriba.removeViewAt(i);

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
            @SuppressLint("RestrictedApi") CheckBox checkbox = new CheckBox(new ContextThemeWrapper(mContext, R.style.AppTheme));
            LinearLayout.LayoutParams paramch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(paramch);
            checkbox.setHeight(30);
            checkbox.setText(order.getNombre_platol());
            checkbox.setTextAppearance(R.style.AppTheme);
            checkbox.setTextColor(Color.BLACK);
            checkbox.setChecked(true);
            checkbox.setId(posi);
            checkbox.setEnabled(!order.getEnviado());
            checkbox.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.check_box));
            if(!checkbox.isEnabled())
            {
                checkbox.setButtonDrawable(null);
                checkbox.setTextColor(mContext.getResources().getColor(R.color.desa));
            }
            checkbox.setPadding(5,0,0,0);

            agregaplat_abajo.addView(checkbox);

            scrol_pedido.post(new Runnable() {
                @Override
                public void run() {
                    scrol_pedido.fullScroll(ScrollView.FOCUS_DOWN);
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
                        for (int i = 0; i <agregaplat_abajo.getChildCount() ; i++)
                        {
                            CheckBox sacar = (CheckBox) agregaplat_abajo.getChildAt(i);
                            if (sacar.getId() == buttonView.getId())
                            {
                                agregaplat_abajo.removeViewAt(i);

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

    public class VH extends RecyclerView.ViewHolder
    {
        public ImageView imageSuge;

        TextView lbl_descriplato;
        TextView lbl_precioplato;

        LinearLayout scroll_ii;
        LinearLayout scroll_id;

        ScrollView scroll_iil;
        ScrollView scroll_idl;

        LinearLayout scroll_ai;
        LinearLayout scroll_ad;

        ScrollView scroll_ail;
        ScrollView scroll_adl;

        RelativeLayout btn_add;
        LinearLayout btafather;

        TextView lbl_ingre;
        TextView lbl_adic;

        private CheckBox check;

        public VH(View itemView)
        {

            super(itemView);

            imageSuge       = itemView.findViewById(R.id.imageSuge);
            lbl_descriplato = itemView.findViewById(R.id.lbl_descriplato);
            lbl_precioplato = itemView.findViewById(R.id.lbl_precio);
            scroll_ii       = itemView.findViewById(R.id.scroll_ii);
            scroll_id       = itemView.findViewById(R.id.scroll_id);
            scroll_ai       = itemView.findViewById(R.id.scroll_ai);
            scroll_ad       = itemView.findViewById(R.id.scroll_ad);

            scroll_iil      = itemView.findViewById(R.id.scroll_iil);
            scroll_idl      = itemView.findViewById(R.id.scroll_idl);
            scroll_ail      = itemView.findViewById(R.id.scroll_ail);
            scroll_adl      = itemView.findViewById(R.id.scroll_adl);

            btn_add         = itemView.findViewById(R.id.btn_add);
            btafather       = itemView.findViewById(R.id.btafather);

            lbl_adic        = itemView.findViewById(R.id.lbl_adic);
            lbl_ingre       = itemView.findViewById(R.id.lbl_ingre);


        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void llena_conto(final Plato item)
        {
            scroll_ii.removeAllViewsInLayout();
            scroll_id.removeAllViewsInLayout();
            scroll_ai.removeAllViewsInLayout();
            scroll_ad.removeAllViewsInLayout();

            scroll_iil.setVisibility(View.INVISIBLE);
            scroll_idl.setVisibility(View.INVISIBLE);
            scroll_ail.setVisibility(View.INVISIBLE);
            scroll_adl.setVisibility(View.INVISIBLE);


            if (item.getEstado_contorno().equals("True"))
            {
                scroll_iil.setVisibility(View.VISIBLE);
                scroll_idl.setVisibility(View.VISIBLE);
                scroll_ail.setVisibility(View.VISIBLE);
                scroll_adl.setVisibility(View.VISIBLE);


                for (final String ingrediente : item.getIngrediente())
                {
                    check = new CheckBox(mContext);
                    check.setTextColor(mContext.getColorStateList(R.color.letras));
                    check.setText(ingrediente);
                    LinearLayout.LayoutParams paramc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    check.setSingleLine(true);
                    paramc.setMargins(0,0,0,0);
                    check.setLayoutParams(paramc);
                    check.setHeight((int) (23* mContext.getResources().getDisplayMetrics().density));
                    check.setChecked(true);


                    Float pedazo = Float.parseFloat(item.getIngrediente().size() + "") / 2;

                    if(item.getIngrediente().indexOf(ingrediente) < pedazo)
                    {
                       scroll_ii.addView(check);

                        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked)
                                {
                                    contornox.add(ingrediente);
                                    showmessage("SE ELIMINÓ INGREDIENTE O ACOMPAÑANTE " + ingrediente.toUpperCase());
                                }
                                else
                                {
                                    contornox.remove(ingrediente);
                                    showmessage("SE AGREGÓ INGREDIENTE O ACOMPAÑANTE " + ingrediente.toUpperCase());
                                }
                            }
                        });

                    }
                    else
                    {
                        scroll_id.addView(check);

                        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked)
                                {
                                    veritran();
                                    contornox.add(ingrediente);
                                    showmessage("SE ELIMINÓ INGREDIENTE O ACOMPAÑANTE " + ingrediente.toUpperCase());
                                }
                                else
                                {
                                    veritran();
                                    contornox.remove(ingrediente);
                                    showmessage("SE AGREGÓ INGREDIENTE O ACOMPAÑANTE " + ingrediente.toUpperCase());
                                }
                            }
                        });

                    }

                }

                for (final String contorno : item.getContorno()) {
                    check = new CheckBox(mContext);
                    check.setTextColor(mContext.getColorStateList(R.color.letras));
                    check.setText(contorno);
                    LinearLayout.LayoutParams paramc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    check.setSingleLine(true);
                    paramc.setMargins(0,0,0,0);
                    check.setLayoutParams(paramc);
                    check.setHeight((int) (23 * mContext.getResources().getDisplayMetrics().density));
                    check.setChecked(false);

                    Float pedazo = Float.parseFloat(item.getContorno().size() + "") / 2;

                    if(item.getContorno().indexOf(contorno) < pedazo)
                    {
                        scroll_ai.addView(check);

                        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked)
                                {
                                    veritran();
                                    ingredientex.add(contorno);
                                    showmessage("SE AGREGÓ EL ADICIONAL " + contorno.toUpperCase());
                                }
                                else
                                {
                                    veritran();
                                    ingredientex.remove(contorno);
                                    showmessage("SE ELIMINÓ EL ADICIONAL " + contorno.toUpperCase());
                                }
                            }
                        });

                    }
                    else
                    {
                        scroll_ad.addView(check);

                        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked)
                                {
                                    veritran();
                                    ingredientex.add(contorno);
                                    showmessage("SE AGREGÓ EL ADICIONAL " + contorno.toUpperCase());
                                }
                                else
                                {
                                    veritran();
                                    ingredientex.remove(contorno);
                                    showmessage("SE ELIMINÓ EL ADICIONAL " + contorno.toUpperCase());
                                }
                            }
                        });

                    }
                }

            }
        }
    }
}
