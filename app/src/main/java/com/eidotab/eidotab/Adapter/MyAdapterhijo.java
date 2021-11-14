/*
package com.eidotab.eidotab.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.eidotab.eidotab.Model.Cuentadata;
import com.eidotab.eidotab.R;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class MyAdapterhijo extends RecyclerView.Adapter<MyAdapterhijo.VH>
{

    private Context mContext;
    public ArrayList<Cuentadata> rdata;
    private TextView l_toti;

    public MyAdapterhijo(Context context, TextView lb_toti)
    {
        this.mContext = context;
        rdata = new ArrayList<>();
        this.l_toti = lb_toti;
    }

    public int AddNew(Cuentadata cuentadata)
    {
        rdata.add(cuentadata);
        int position = rdata.indexOf(cuentadata);
        notifyItemInserted(position);
        return position;

    }

    @Override
    public MyAdapterhijo.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cuenta, parent, false);
        return new MyAdapterhijo.VH(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapterhijo.VH holder, int position)
    {

        final Cuentadata cuentadata = rdata.get(position);

        holder.list_item_plato_name.setText(cuentadata.getNombre_plato());
        holder.list_item_precio.setText(String.format(Locale.GERMANY, "%,.2f", cuentadata.getPrecio_plato()) + " S/." );

        String prec =  l_toti.getText().toString().replace("S/.", "");
        String tot =  holder.list_item_precio.getText().toString().replace("S/.", "");
        NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMANY);

        try
        {
            double val = nf_in.parse(prec).doubleValue();
            double val2 = nf_in.parse(tot).doubleValue();
            double stot = val2 + val;
            l_toti.setText(String.format(Locale.GERMANY, "%,.2f", stot) + " S/." );
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount()
    {

        return rdata.size();

    }

    public class VH extends RecyclerView.ViewHolder
    {

        TextView list_item_plato_name;
        TextView list_item_precio;

        public VH(View itemView)
        {
            super(itemView);

            list_item_plato_name = itemView.findViewById(R.id.list_item_plato_name);
            list_item_precio = itemView.findViewById(R.id.list_item_precio);

        }

    }
}


*/
